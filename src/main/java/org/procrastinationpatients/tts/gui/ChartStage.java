package org.procrastinationpatients.tts.gui;


import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.entities.Lane;
import org.procrastinationpatients.tts.source.StaticConfig;
import org.procrastinationpatients.tts.utils.DrawUtils;

public class ChartStage extends TickStage {

    private Lane[] targetLanes;
    private Canvas[] canvases;
    private VBox root;
    private Long startTime;


    public ChartStage() {
        super();
        this.startTime = System.currentTimeMillis();
        this.targetLanes = new Lane[3];
        this.canvases = new Canvas[3];

        if (StaticConfig.INSPECT_LANE_GROUP == 0) {
            for (int i = 0; i < 3; i++) {
                this.targetLanes[i] = Engine.getInstance().getLinks()[StaticConfig.INSPECT_LINK].getLanes()[i];
            }
        } else if (StaticConfig.INSPECT_LANE_GROUP == 3) {
            for (int i = 0; i < 3; i++) {
                this.targetLanes[i] = Engine.getInstance().getLinks()[StaticConfig.INSPECT_LINK].getLanes()[i + 3];
            }
        } else {
            throw new RuntimeException("Inspect lane invalid");
        }


        for (int i = 0; i < this.canvases.length; i++) {
           this.canvases[i] = new Canvas(45D*StaticConfig.CHART_X_SKIP_MULTIPLE, this.targetLanes[i].getLength()*StaticConfig.CHART_Y_SKIP_MULTIPLE);

        }
        this.root = new VBox();
       this.root.getChildren().addAll(this.canvases);
        this.setScene(new Scene(this.root));
        this.getTimeline().play();
    }

    @Override
    protected void drawAllDynamic() {
        for (int i = 0; i < 3; i++) {
            Double x = (System.currentTimeMillis() - this.startTime)*StaticConfig.CHART_X_SKIP_MULTIPLE / 1000D;
            for (int j = 0; j < targetLanes[i].getVehicles().length; j++) {
                if (targetLanes[i].getVehicles()[j] != null) {
                    Double y = (double) j*StaticConfig.CHART_Y_SKIP_MULTIPLE;
                    DrawUtils.drawBallAtCoordinate(canvases[i].getGraphicsContext2D(), x, y, 3, Color.BLACK);
                }

            }


        }
    }

    @Override
    protected void drawAllStatic() {

    }
}
