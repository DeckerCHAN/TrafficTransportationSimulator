package org.procrastinationpatients.tts.entities;

import javafx.scene.canvas.GraphicsContext;

/**
 * 东西走向
 * Created by decker on 15-1-28.
 */
public class Street extends Link {

    private Dot eastDot;
    private Dot westDot;


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
        return eastDot;
    }

    public void setEastDot(Dot eastDot) {
        this.eastDot = eastDot;
    }

    public Dot getWestDot() {
        return westDot;
    }

    public void setWestDot(Dot westDot) {
        this.westDot = westDot;
    }
}
