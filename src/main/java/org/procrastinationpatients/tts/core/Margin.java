package org.procrastinationpatients.tts.core;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author Decker & his father -- Jeffrey
 */

public class Margin implements Container, Dot, VisualEntity, Connectible {

    private final Integer id;
    private Point2D position;
    private Link connectionLink;

    public Margin(Integer marginID, Point2D position) {
        this.position = position;
        this.id = marginID;
    }

	@Override
	public Collection<Vehicle> getVehicles() {
		return null;
	}

	@Override
	public void addVehicles(Vehicle vehicle) {

	}

	@Override
	public int getSafetyDistanceByID(int whichLine, int index) {
		return 0;
	}

	@Override
	public void drawStaticGraphic(GraphicsContext gc) {

    }

	@Override
	public void drawDynamicGraphic(GraphicsContext gc) {

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
    public boolean canChangeLine(Vehicle vehicle) {
        return false;
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
}
