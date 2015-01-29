package org.procrastinationpatients.tts.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by decker on 15-1-28.
 */
public class Margin extends IdentifiableObject implements Visible, Dot {

    private Point2D position;

    private Link connectedLink;

    public Margin(Integer id, Point2D position) {
        super(id);
        this.position = position;
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

    }

    @Override
    public void drawDynamicGraphic(GraphicsContext gc) {
		
    }

    public Link getConnectedLink() {
        return connectedLink;
    }

    public void setConnectedLink(Link connectedLink) {
        this.connectedLink = connectedLink;
    }
}
