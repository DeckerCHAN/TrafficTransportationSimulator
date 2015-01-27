package org.procrastinationpatients.tts.core;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.procrastinationpatients.tts.utils.DrawUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @Author Decker & his father -- Jeffrey
 */

public class Margin implements Container, Dot, VisualEntity, Connectible {

	private final Integer id;
	private Point2D position;
	private Link connectionLink;

	private int line_Length;

	//每条线路用一个数组表示
	private Vehicle[][] line;
	//所有Margin内的车的集合
	private LinkedList<Vehicle> vehicles;

	public Margin(Integer marginID, Point2D position) {
		this.position = position;
		this.id = marginID;
	}

	public Margin(Integer marginID, Point2D position, int length) {
		this.position = position;
		this.id = marginID;
		this.line = new Vehicle[6][length];
	}

	@Override
	public LinkedList<Vehicle> getVehicles() {
		return this.vehicles;
	}

	//将汽车加入到Margin对象中
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
	public int getSafetyDistanceByID(int whichLine, int index) {
		return 0;
	}


	@Override
	public void drawStaticGraphic(GraphicsContext gc) {
		DrawUtils.drawBallAtCoordinate(gc, this.getPosition(), 30, Color.BLUE);

	}

	@Override
	public void drawDynamicGraphic(GraphicsContext gc) {

	}

	@Override
	public int getLineLength() {
		return this.line_Length;
	}

	@Override
	public void setLineLength(int length) {
		this.line_Length = length;
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

	@Override
	public Container changeToNextContainer(Vehicle vehicle) {
		return null;
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

	@Override
	public Point2D getPosition() {
		return this.position;
	}

	public Integer getId() {
		return id;
	}

	public Link getConnectionLink() {
		return connectionLink;
	}

	public void setConnectionLink(Link connectionLink) {
		this.connectionLink = connectionLink;
	}

	@Override
	public Collection<Connectible> getConnections() {
		ArrayList<Connectible> arrayList = new ArrayList<>();
		arrayList.add(this.connectionLink);
		return arrayList;
	}

	@Override
	public void addConnection(Connectible connection) {
		this.connectionLink = (Link) connection;
	}

	public void setLine(Vehicle[][] line) {
		this.line = line;
	}

	public Vehicle[][] getLine() {
		return this.line;
	}
}
