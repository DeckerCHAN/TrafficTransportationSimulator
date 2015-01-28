package org.procrastinationpatients.tts.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by decker on 15-1-28.
 */
public class Cross extends IdentifiableObject implements Visible, Dot {

    private  Point2D position;
    private Lane[] lanes;

    private Road northRoad;
    private Road southRoad;
    private Street eastStreet;
    private Street westStreet;

    public Cross(Integer id, Point2D position) {
        super(id);
        this.position = position;
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
}
