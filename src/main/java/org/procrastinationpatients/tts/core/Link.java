package org.procrastinationpatients.tts.core;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
	private int line_Length;
	private LinkType type;
	private Lane[] lanes;
	private LinkedList<Vehicle> vehicles;
	private Connectible[] connections;


	public Link(Integer linkID) {
		this.id = linkID;
		this.lanes = new Lane[6];
		this.setType(LinkType.UNKNOWN);
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
		Point2D positionA = dotA.getPosition();
		Point2D positionB = dotB.getPosition();


		DrawUtils.drawLine(gc, dotA.getPosition(), dotB.getPosition(), Color.BLACK, 3);
		if (this.type == LinkType.STREET) {//若南北走向

			Line portLineA = new Line(positionA.getX() - 30D, positionA.getY() + 60D, positionA.getX() + 30D, positionA.getY() + 60D);
			Line portLineB = new Line(positionB.getX() - 30D, positionB.getY() - 60D, positionB.getX() + 30D, positionB.getY() - 60D);
			Line portCenterLink = new Line(positionA.getX(), positionA.getY() + 60D, positionB.getX(), positionB.getY() - 60D);
			Line portCenterLinkP = new Line(positionA.getX() + 10D, positionA.getY() + 60D, positionB.getX() + 10D, positionB.getY() - 60D);
			Line portCenterLinkPP = new Line(positionA.getX() + 20D, positionA.getY() + 60D, positionB.getX() + 20D, positionB.getY() - 60D);
			Line portCenterLinkPPP = new Line(positionA.getX() + 30D, positionA.getY() + 60D, positionB.getX() + 30D, positionB.getY() - 60D);
			Line portCenterLinkM = new Line(positionA.getX() - 10D, positionA.getY() + 60D, positionB.getX() - 10D, positionB.getY() - 60D);
			Line portCenterLinkMM = new Line(positionA.getX() - 20D, positionA.getY() + 60D, positionB.getX() - 20D, positionB.getY() - 60D);
			Line portCenterLinkMMM = new Line(positionA.getX() - 30D, positionA.getY() + 60D, positionB.getX() - 30D, positionB.getY() - 60D);


			//画出A的port线
			DrawUtils.drawLine(gc, portLineA, Color.BROWN, 8);
			//画出B的port线
			DrawUtils.drawLine(gc, portLineB, Color.BROWN, 8);
			//画出中线
			DrawUtils.drawLine(gc, portCenterLink, Color.BROWN, 8);
			DrawUtils.drawLine(gc, portCenterLinkP, Color.BROWN, 8);
			DrawUtils.drawLine(gc, portCenterLinkPP, Color.BROWN, 8);
			DrawUtils.drawLine(gc, portCenterLinkPPP, Color.BROWN, 8);
			DrawUtils.drawLine(gc, portCenterLinkM, Color.BROWN, 8);
			DrawUtils.drawLine(gc, portCenterLinkMM, Color.BROWN, 8);
			DrawUtils.drawLine(gc, portCenterLinkMMM, Color.BROWN, 8);


		}
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

		Container nextCon = lanes[vehicle.getCur_line()].getOutput().getContainer() ;
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
	public void addLanes(Lane[] lanes) {
		this.lanes = lanes ;
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
	public Connectible[] getConnections() {
		return this.connections;
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
