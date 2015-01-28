package org.procrastinationpatients.tts.entities;

import javafx.scene.canvas.GraphicsContext;

/**
 * 南北走向
 * Created by decker on 15-1-28.
 */
public class Road extends Link {

    private Dot northDot;
    private Dot southDot;

    public Road(Integer id) {
        super(id);
    }

    @Override
    public void drawStaticGraphic(GraphicsContext gc) {

    }

    @Override
    public void drawDynamicGraphic(GraphicsContext gc) {

    }

    public Dot getNorthDot() {
        return northDot;
    }

    public void setNorthDot(Dot northDot) {
        this.northDot = northDot;
    }

    public Dot getSouthDot() {
        return southDot;
    }

    public void setSouthDot(Dot southDot) {
        this.southDot = southDot;
    }
}
