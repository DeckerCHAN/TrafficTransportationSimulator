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

	private LinkType type;

	private final Integer id;
	private LinkedList<Vehicle> vehicles;
	private int line_Length;
	private Connectible[] connections;
	private Lane[] lanes;

	public Link(Integer linkID) {
		this.id = linkID;
		//DONE:树组实例化之后再赋值嘛
		this.lanes = new Lane[6];
		this.setType(LinkType.UNKNOW);
	}

	public Link(Integer linkID, Connectible[] connections) {
		this(linkID);
		if (connections.length != 2) {
			throw new ArrayIndexOutOfBoundsException("Link能且只能连接两个Dot点");
		}
		this.connections = connections;
		//TODO:通过两个Connection计算数组拥有的AvailablePoint数量
	}

	@Override
	public LinkedList<Vehicle> getVehicles() {
		return this.vehicles;
	}

	@Override
	public boolean addVehicle(Vehicle vehicle) {
		int cur_line = vehicle.getCur_line();
		for (int j = 0; j < line_Length; j++) {
			if (lanes[cur_line].vehicles[j] == null) {
				vehicle.setCur_Loc(j);
				this.vehicles.add(vehicle);
				this.lanes[cur_line].vehicles[j] = vehicle;
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeVehicle(Vehicle vehicle) {
		int v_line = vehicle.getCur_line();
		int v_location = vehicle.getCur_Loc();

		if(lanes[v_line].vehicles[v_location] != null){
			this.vehicles.remove(vehicle) ;
			this.lanes[v_line].vehicles[v_location] = null ;
		}
	}


	//通过索引位置,得到与下一辆车之间的安全距离
	@Override
	public int getSafetyDistanceByID(int whichLine, int index) {
		if (index < 0 || index >= this.line_Length)
			return -1;
		if (lanes[whichLine].vehicles[index] == null)
			return 0;

		for (int i = index + 1; i < this.line_Length; i++) {
			if (lanes[whichLine].vehicles[index] != null) {
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
			if (lanes[v_line].vehicles[i] != null) {
				flag = false;
				break;
			}
		}

		return flag;
	}

	@Override
	public void changeToNextContainer(Vehicle vehicle) {
		this.removeVehicle(vehicle);

		Container nextCon = lanes[vehicle.getCur_line()].getOutput()[0].getContainer() ;
		nextCon.addVehicle(vehicle) ;

	}

	public boolean hasVehicle(int line , int loc){
		if(this.lanes[line].vehicles[loc] != null)
			return true ;
		else
			return false ;
	}

	@Override
	public void toGoalLine(Vehicle vehicle) {
		int v_line = vehicle.getGoal_line();
		int v_location = vehicle.getCur_Loc();

		if(!hasVehicle(v_line,v_location)){
			lanes[vehicle.getCur_line()].vehicles[v_location] = null ;
			lanes[v_line].vehicles[v_location] = vehicle ;
		}
	}

	@Override
	public Vehicle getNextVehicle(Vehicle vehicle) {

		int v_line = vehicle.getCur_line();
		int v_location = vehicle.getCur_Loc();

		for (int i = v_location + 1; i < this.line_Length; i++) {
			if (lanes[v_line].vehicles[i] != null)
				return lanes[v_line].vehicles[i];
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

	public LinkType getType() {
		return type;
	}

	public void setType(LinkType type) {
		this.type = type;
	}
}
