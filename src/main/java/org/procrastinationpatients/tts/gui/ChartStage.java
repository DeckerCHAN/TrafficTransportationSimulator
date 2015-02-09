package org.procrastinationpatients.tts.gui;


import javafx.stage.Stage;
import org.procrastinationpatients.tts.entities.Lane;

public class ChartStage extends TickStage {

    private Lane[] targetLanes;

    public ChartStage() {
        super();
        this.targetLanes = new Lane[3];
    }

    @Override
    protected void drawAllDynamic() {

    }

    @Override
    protected void drawAllStatic() {

    }
}
