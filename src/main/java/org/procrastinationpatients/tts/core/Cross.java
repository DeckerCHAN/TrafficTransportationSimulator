package org.procrastinationpatients.tts.core;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class Cross implements Container, Dot, VisualEntity, Connectible {

	private LinkedList<Vehicle> vehicles;

    private Point2D location;
    private final Integer id;

    private Connectible[] connections;

    public Cross(Integer crossID, Point2D location) {
        this.connections = new Connectible[4];
        this.id = crossID;
        this.location = location;
    }

    public Cross(Integer crossID, Point2D location, Collection<Connectible> connections) {
        this(crossID, location);
        this.vehicles = new LinkedList<>();
    }

	@Override
	public Collection<Vehicle> getVehicles() {
		return this.vehicles;
	}

	@Override
	public void addVehicles(Vehicle vehicle) {
		this.vehicles.add(vehicle);
	}

	@Override
	public int getLineLength() {
		return 0;
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
	public Container changeToNextContainer(Vehicle vehicle) {
		return null;
	}

	@Override
	public Vehicle getNextVehicle(Vehicle vehicle) {
		return null;
	}

	@Override
	public void drawStaticGraphic(GraphicsContext gc) {

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
    public Collection<Connectible> getConnections() {
        return new ArrayList<>(Arrays.asList(this.connections));
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
