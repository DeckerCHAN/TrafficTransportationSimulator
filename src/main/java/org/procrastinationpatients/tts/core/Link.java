package org.procrastinationpatients.tts.core;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @Author Decker & his father -- Jeffrey
 */

public class Link implements Container, Dot, VisualEntity, Connectible {

	public static final Link EMPTY;

	static {
		EMPTY = new Link(-1);
	}

	private Vehicle[][] line;
	private int line_Length;

	private Connectible[] connections;

	private final Integer id;

	public Link(Integer linkID) {
		this.id = linkID;
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
	public Collection<Vehicle> getVehicles() {
		return null;
	}

	@Override
	public void addVehicles(Vehicle vehicle) {

	}

	//通过索引位置,得到与下一辆车之间的安全距离
	@Override
	public int getSafetyDistanceByID(int whichLine, int index) {
		if (index < 0 || index >= this.line_Length)
			return -1;
		if (line[whichLine][index] == null)
			return 0;

		int distance = index;
		for (index++; index < this.line_Length; index++) {
			if (line[whichLine][index] != null) {
				break;
			}
		}
		if (index == this.line_Length) {
			index--;
		}
		return index - distance;
	}

	@Override
	public void drawStaticGraphic(GraphicsContext gc) {

	}

	@Override
	public void drawDynamicGraphic(GraphicsContext gc) {

	}

	@Override
	public int getLineLength() {
		return line_Length;
	}

	public boolean changeToNextContainer() {
		return false;
	}

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
	public Container changeToNextContainer(Vehicle vehicle) {
		return null;
	}

	@Override
	public Vehicle getNextVehicle(Vehicle vehicle) {
		return null;
	}

	public int change_Line_NUMBER(int v_line) {
		switch (v_line) {
			case 1:
				v_line = 2;
				break;
			case 2:
				v_line = 1;
				break;
			case 3:
				v_line = 4;
				break;
			case 4:
				v_line = 3;
				break;
		}
		return v_line;
	}

	@Override
	public Point2D getPosition() {
		return null;
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