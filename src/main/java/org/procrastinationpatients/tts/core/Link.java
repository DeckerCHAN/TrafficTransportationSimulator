package org.procrastinationpatients.tts.core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.procrastinationpatients.tts.utils.DrawUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author Decker & his father -- Jeffrey
 */

public class Link implements Container, VisualEntity, Connectible {

	public static final Link EMPTY;

	static {
		EMPTY = new Link(-1);
	}

	private final Integer id;
	private Vehicle[][] line;
	private LinkedList<Vehicle> vehicles;
	private int line_Length;
	private Connectible[] connections;
	private Lane[] lanes;

	public Link(Integer linkID) {
		this.id = linkID;
		this.lanes = new Lane[6];
	}

	public Link(Integer linkID, Connectible[] connections) {
		this(linkID);
		if (connections.length != 2) {
			throw new ArrayIndexOutOfBoundsException("Link能且只能连接两个Dot点");
		}
		this.connections = connections;
		//TODO:通过两个Connection计算数组拥有的AvailablePoint数量
//		int num=new Integer("");
//		line = new Vehicle[4][num];
//		this.line_Length = num;

	}

	@Override
	public LinkedList<Vehicle> getVehicles() {
		return this.vehicles;
	}

	@Override
	public boolean addVehicle(Vehicle vehicle) {
		int cur_line = vehicle.getCur_line();
		for (int j = 0; j < line_Length; j++) {
			if (line[cur_line][j] == null) {
				vehicle.setCur_Loc(j);
				this.vehicles.add(vehicle);
				this.line[cur_line][j] = vehicle;
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeVehicle(Vehicle vehicle) {
		int v_line = vehicle.getCur_line();
		int v_location = vehicle.getCur_Loc();

		if(line[v_line][v_location] != null){
			this.vehicles.remove(vehicle) ;
			this.line[v_line][v_location] = null ;
		}
	}


	//通过索引位置,得到与下一辆车之间的安全距离
	@Override
	public int getSafetyDistanceByID(int whichLine, int index) {
		if (index < 0 || index >= this.line_Length)
			return -1;
		if (line[whichLine][index] == null)
			return 0;

		for (int i = index + 1; i < this.line_Length; i++) {
			if (line[whichLine][index] != null) {
				return i - index ;
			}
		}

		return line_Length - index ;
	}

	@Override
	public void drawStaticGraphic(GraphicsContext gc) {

		Dot dotA = (Dot) this.connections[0];
		Dot dotB = (Dot) this.connections[1];
		DrawUtils.drawLineThrowTwoPoint(gc, dotA.getPosition(), dotB.getPosition(), Color.BLACK, 3);
	}

	@Override
	public void drawDynamicGraphic(GraphicsContext gc) {

	}

	@Override
	public int getLineLength() {
		return line_Length;
	}

	@Override
	public void setLineLength(int length) { this.line_Length = length ; }

	@Override
	public int changeLine(Vehicle vehicle) {
		int v_line = vehicle.getCur_line();
		int v_location = vehicle.getCur_Loc();
		int v_speed = vehicle.getCur_Spd();

		v_line = change_Line_NUMBER(v_line);
		vehicle.setCur_Loc(v_location + v_speed);
		vehicle.setCur_Line(v_line);

		return v_line;
	}

	@Override
	public boolean canChangeLine(Vehicle vehicle) {
		int v_line = vehicle.getCur_line();
		int v_location = vehicle.getCur_Loc();
		int v_speed = vehicle.getCur_Spd();
		boolean flag = true;

		v_line = change_Line_NUMBER(v_line);
		for (int i = v_location + 1; i <= v_location + v_speed; i++) {
			if (line[v_line][i] != null) {
				flag = false;
				break;
			}
		}

		return flag;
	}

	@Override
	public void changeToNextContainer(Vehicle vehicle) {
		this.removeVehicle(vehicle);
		((Cross)this.connections[0]).addVehicle(vehicle) ;
	}

	@Override
	public Vehicle getNextVehicle(Vehicle vehicle) {

		int v_line = vehicle.getCur_line();
		int v_location = vehicle.getCur_Loc();

		for (int i = v_location + 1; i < this.line_Length; i++) {
			if (line[v_line][i] != null)
				return line[v_line][i];
		}
		return null;
	}

	public int change_Line_NUMBER(int v_line) {
		switch (v_line) {
			case 1:
				v_line = 2;
				break;
			case 2:
				v_line = 3;
				break;
			case 3:
				v_line = 3;
				break;
			case 4:
				v_line = 4;
				break;
			case 5:
				v_line = 4;
				break;
			case 6:
				v_line = 5;
		}
		return v_line;
	}


	public Integer getId() {
		return id;
	}

	@Override
	public Collection<Connectible> getConnections() {
		return Arrays.asList(this.connections);
	}

	@Override
	public void addConnection(Connectible connection) {
		List<Connectible> arrayList = Arrays.asList(this.connections);
		arrayList.add(connection);

		this.connections = connections;
	}
}
