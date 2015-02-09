package org.procrastinationpatients.tts.gui;


import javafx.stage.Stage;
import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.entities.Lane;
import org.procrastinationpatients.tts.source.StaticConfig;

public class ChartWindow extends TickWindow {

    private Lane[] targetLanes;

    public ChartWindow() {
        super();
        this.targetLanes = new Lane[3];
    }

    @Override
    protected void drawAllDynamic() {

    }

    @Override
    protected void drawAllStatic() {

    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
