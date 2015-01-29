package org.procrastinationpatients.tts.entities;

import javafx.scene.canvas.GraphicsContext;

/**
 * 南北走向
 * Created by decker on 15-1-28.
 */
public class Road extends Link {


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
        return getA();
    }

    public void setNorthDot(Dot northDot) {
        this.setA(northDot);
    }

    public Dot getSouthDot() {
        return getB();
    }

    public void setSouthDot(Dot southDot) {
        this.setB(southDot);
    }

}