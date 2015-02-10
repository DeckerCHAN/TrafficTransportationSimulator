package org.procrastinationpatients.tts.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.procrastinationpatients.tts.source.StaticConfig;
import org.procrastinationpatients.tts.utils.DrawUtils;


public class Margin extends IdentifiableObject implements Visible, Dot {

    private Point2D position;

    private Link connectedLink;

    private Integer firstInputLaneIndex =-1;

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
        if (StaticConfig.DEBUG_MODE) {
            DrawUtils.drawHorizontalText(gc, this.getPosition().getX(), this.getPosition().getY() - 15D, Color.GREEN, "C:" + this.getId(), 15D);

        }
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

    public Integer getFirstInputLaneIndex() {
        return firstInputLaneIndex;
    }

    public void setFirstInputLaneIndex(Integer firstInputLaneIndex) {
        this.firstInputLaneIndex = firstInputLaneIndex;
    }
}
