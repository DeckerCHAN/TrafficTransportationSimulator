package org.procrastinationpatients.tts.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by decker on 15-1-28.
 */
public class Cross extends IdentifiableObject implements Visible, Dot, FunctionalObject {

    private  Point2D position;
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
        for(Lane [] lanes:this.getRowLanes())
        {
            lanes=new Lane[7];
            for (Lane lane:lanes)
            {
                lane=new Lane();
            }
        }

    }

    @Override
    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Point2D position) {
        this.position=position;
    }

    @Override
    public void drawStaticGraphic(GraphicsContext gc) {

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
	public int getSafetyDistanceByID(int Cur_line, int Cur_Loc) {
		//TODO
		return 0;
	}

	@Override
	public Vehicle getNextVehicle(Vehicle vehicle) {
		//TODO
		return null;
	}

	@Override
	public boolean canChangeLine(Vehicle vehicle) {
		//TODO
		return false;
	}

	@Override
	public int changeLine(Vehicle vehicle) {
		//TODO
		return 0;
	}

	@Override
	public boolean changeToNextContainer(Vehicle vehicle) {
		//TODO
		return false;
	}

	@Override
	public void toGoalLine(Vehicle vehicle) {
		//TODO
	}

	@Override
	public int getLane_Length() {
		//TODO
		return 0;
	}

	@Override
	public boolean addVehicle(Vehicle vehicle) {
		return false;
	}

	@Override
	public void removeVehicle(Vehicle vehicle) {

	}
}
