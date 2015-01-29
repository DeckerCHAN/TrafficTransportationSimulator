package org.procrastinationpatients.tts.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.procrastinationpatients.tts.utils.DrawUtils;

/**
 * Created by decker on 15-1-28.
 */
public class Cross extends IdentifiableObject implements Visible, Dot, FunctionalObject {

    private Point2D position;
    private Lane[] northLanes;
    private Lane[] southLanes;
    private Lane[] eastLanes;
    private Lane[] westLanes;


    private Road northRoad;
    private Road southRoad;
    private Street eastStreet;
    private Street westStreet;

    public Cross(Integer id, Point2D position) {
        super(id);
        this.position = position;
        this.northLanes = new Lane[]{new Lane(this), new Lane(this), new Lane(this), new Lane(this), new Lane(this), new Lane(this), new Lane(this)};
        this.southLanes = new Lane[]{new Lane(this), new Lane(this), new Lane(this), new Lane(this), new Lane(this), new Lane(this), new Lane(this)};
        this.eastLanes = new Lane[]{new Lane(this), new Lane(this), new Lane(this), new Lane(this), new Lane(this), new Lane(this), new Lane(this)};
        this.westLanes = new Lane[]{new Lane(this), new Lane(this), new Lane(this), new Lane(this), new Lane(this), new Lane(this), new Lane(this)};


    }

    @Override
    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public void drawStaticGraphic(GraphicsContext gc) {
        DrawUtils.drawBallAtCoordinate(gc, this.getPosition(), 6, Color.RED);
        Point2D a = new Point2D(this.getPosition().getX() - 30D, this.getPosition().getY() - 60D);
        Point2D b = new Point2D(this.getPosition().getX() + 30D, this.getPosition().getY() - 60D);

        Point2D c = new Point2D(this.getPosition().getX() + 60D, this.getPosition().getY() - 30D);
        Point2D d = new Point2D(this.getPosition().getX() + 60D, this.getPosition().getY() + 30D);

        Point2D e = new Point2D(this.getPosition().getX() + 30D, this.getPosition().getY() + 60D);
        Point2D f = new Point2D(this.getPosition().getX() - 30D, this.getPosition().getY() + 60D);

        Point2D g = new Point2D(this.getPosition().getX() - 60D, this.getPosition().getY() + 30D);
        Point2D h = new Point2D(this.getPosition().getX() - 60D, this.getPosition().getY() - 30D);

        DrawUtils.drawPolygon(gc, Color.AQUA, 3, a, b, c, d, e, f, g, h);
    }

    @Override
    public void drawDynamicGraphic(GraphicsContext gc) {

    }

    public Road getNorthRoad() {
        return northRoad;
    }

    public void setNorthRoad(Road northRoad) {
        this.northRoad = northRoad;
    }

    public Road getSouthRoad() {
        return southRoad;
    }

    public void setSouthRoad(Road southRoad) {
        this.southRoad = southRoad;
    }

    public Street getEastStreet() {
        return eastStreet;
    }

    public void setEastStreet(Street eastStreet) {
        this.eastStreet = eastStreet;
    }

    public Street getWestStreet() {
        return westStreet;
    }

    public void setWestStreet(Street westStreet) {
        this.westStreet = westStreet;
    }

    public Lane[] getNorthLanes() {
        return northLanes;
    }

    public void setNorthLanes(Lane[] northLanes) {
        this.northLanes = northLanes;
    }

    public Lane[] getSouthLanes() {
        return southLanes;
    }

    public void setSouthLanes(Lane[] southLanes) {
        this.southLanes = southLanes;
    }

    public Lane[] getEastLanes() {
        return eastLanes;
    }

    public void setEastLanes(Lane[] eastLanes) {
        this.eastLanes = eastLanes;
    }

    public Lane[] getWestLanes() {
        return westLanes;
    }

    public void setWestLanes(Lane[] westLanes) {
        this.westLanes = westLanes;
    }

    public Lane[][] getRowLanes() {
        return new Lane[][]{this.getNorthLanes(), this.getEastLanes(), this.getSouthLanes(), this.getWestLanes()};
    }
    
    public Link [] getRowLink()
    {
        return new Link[]{this.getNorthRoad(),this.getEastStreet(),this.getSouthRoad(),this.getWestStreet()};
    }


	@Override
	public boolean canChangeLine(Vehicle vehicle) {
		return false;
	}

	@Override
	public int changeLine(Vehicle vehicle) {
		return 0;
	}

	@Override
	public void toGoalLine(Vehicle vehicle) {}

	@Override
	public int getLane_Length() {return 0;}

	@Override
	public boolean addVehicle(Vehicle vehicle) {
		return false;
	}

	@Override
	public void removeVehicle(Vehicle vehicle) {}

	@Override
	public void changeToNextContainer(Vehicle vehicle) {
		//TODO
	}

}
