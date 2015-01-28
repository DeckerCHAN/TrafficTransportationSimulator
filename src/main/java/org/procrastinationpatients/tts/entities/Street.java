package org.procrastinationpatients.tts.entities;

import javafx.scene.canvas.GraphicsContext;

/**
 * 东西走向
 * Created by decker on 15-1-28.
 */
public class Street extends Link {



    public Street(Integer id) {
        super(id);
    }

    @Override
    public void drawStaticGraphic(GraphicsContext gc) {

    }

    @Override
    public void drawDynamicGraphic(GraphicsContext gc) {

    }

    public Dot getEastDot() {
        return this.getB();
    }

    public void setEastDot(Dot eastDot) {
        this.setB(eastDot);
    }

    public Dot getWestDot() {
        return this.getA();
    }

    public void setWestDot(Dot westDot) {
        this.setA(westDot);
    }
}
