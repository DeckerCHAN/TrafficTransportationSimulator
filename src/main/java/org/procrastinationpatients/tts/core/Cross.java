package org.procrastinationpatients.tts.core;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.procrastinationpatients.tts.utils.DrawUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class Cross implements Container, Dot, VisualEntity, Connectible {

	private final Integer id;
	private LinkedList<Vehicle> vehicles;
	private Point2D location;
	private Connectible[] connections;

	private Lane[][] lanes;

	public Cross(Integer crossID, Point2D location) {
		this.connections = new Connectible[4];
		this.id = crossID;
		this.location = location;
		this.lanes = new Lane[9][4];
	}

	@Override
	public LinkedList<Vehicle> getVehicles() {
		return this.vehicles;
	}

	@Override
	public boolean addVehicle(Vehicle vehicle) {
		this.vehicles.add(vehicle);
		return false;
	}

	@Override
	public void removeVehicle(Vehicle vehicle) {
		this.vehicles.remove(vehicle) ;
	}

	@Override
	public int getLineLength() {
		return 0;
	}

	@Override
	public void setLineLength(int length) {

	}


	@Override
	public void drawStaticGraphic(GraphicsContext gc) {
		DrawUtils.drawBallAtCoordinate(gc, this.getPosition(), 30, Color.RED);
	}

	@Override
	public int changeLine(Vehicle vehicle) {
		return 0;
	}

	@Override
	public int getSafetyDistanceByID(int whichLine, int index) {
		return 0;
	}

	@Override
	public void changeToNextContainer(Vehicle vehicle) {

	}

	@Override
	public Vehicle getNextVehicle(Vehicle vehicle) {
		return null;
	}

	@Override
	public boolean hasVehicle(int line, int loc) {
		return false;
	}

	@Override
	public void toGoalLine(Vehicle vehicle) {

	}

	@Override
	public void addLanes(Lane[] lanes) {

	}

	@Override
	public void drawDynamicGraphic(GraphicsContext gc) {

	}

	@Override
	public boolean canChangeLine(Vehicle vehicle) {
		return false;
	}

	@Override
	public Point2D getPosition() {
		return this.location;
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
		ArrayList<Connectible> arrayList = new ArrayList<>(Arrays.asList(this.connections));
		if (arrayList.size() > 4) {
			throw new ArrayIndexOutOfBoundsException("Cross只能连接四个点");
		}
		arrayList.add(connection);
		arrayList.toArray(this.connections);
	}

}
