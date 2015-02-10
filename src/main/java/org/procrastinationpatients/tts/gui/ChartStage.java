package org.procrastinationpatients.tts.gui;


import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.entities.Lane;
import org.procrastinationpatients.tts.source.StaticConfig;
import org.procrastinationpatients.tts.utils.DrawUtils;

public class ChartStage extends TickStage {

    private Lane[] targetLanes;
    private Canvas[] canvases;
    private ScrollPane root;
    private VBox box;
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
            this.canvases[i] = new Canvas(StaticConfig.INSPECT_TIME * StaticConfig.CHART_X_SKIP_MULTIPLE + StaticConfig.DRAW_BIAS_X, this.targetLanes[i].getLength() * StaticConfig.CHART_Y_SKIP_MULTIPLE + StaticConfig.DRAW_BIAS_Y);

        }
        this.root = new ScrollPane();
        this.root.setPrefSize(canvases[0].getWidth(),canvases[0].getHeight());
        this.box = new VBox();
        this.box.getChildren().addAll(this.canvases);
        this.root.setContent(this.box);
        this.setScene(new Scene(this.root));
        this.drawAllStatic();
        this.getTimeline().play();
    }

    @Override
    protected void drawAllDynamic() {
        for (int i = 0; i < 3; i++) {
            Double x = (System.currentTimeMillis() - this.startTime)*StaticConfig.CHART_X_SKIP_MULTIPLE / 1000D;
            for (int j = 0; j < targetLanes[i].getVehicles().length; j++) {
                if (targetLanes[i].getVehicles()[j] != null && targetLanes[i].getVehicles()[j].isStop() == false) {
                    Double y = (double) j*StaticConfig.CHART_Y_SKIP_MULTIPLE;
                    DrawUtils.drawBallAtCoordinate(canvases[i].getGraphicsContext2D(), x + StaticConfig.DRAW_BIAS_X, y + StaticConfig.DRAW_BIAS_Y, 3, Color.BLACK);
                }

            }
        }
    }

    @Override
    protected void drawAllStatic() {
        for (int i = 0; i < 3; i++) {
            GraphicsContext gc = canvases[i].getGraphicsContext2D();
            DrawUtils.drawHorizontalText(gc, StaticConfig.DRAW_BIAS_X + 120D, 0 + StaticConfig.DRAW_BIAS_Y - 30D, Color.RED, "Longitudinal position(m)/Time(sec)", 15D);
            DrawUtils.drawBallAtCoordinate(gc, 0 + StaticConfig.DRAW_BIAS_X, 0 + StaticConfig.DRAW_BIAS_Y, 10, Color.RED);
            DrawUtils.drawLine(gc, new Point2D(0 + StaticConfig.DRAW_BIAS_X, 0 + StaticConfig.DRAW_BIAS_Y), new Point2D(0 + StaticConfig.DRAW_BIAS_X + this.canvases[i].getWidth(), 0 + StaticConfig.DRAW_BIAS_Y), Color.RED, 5D);
            DrawUtils.drawLine(gc, new Point2D(0 + StaticConfig.DRAW_BIAS_X, 0 + StaticConfig.DRAW_BIAS_Y), new Point2D(0 + StaticConfig.DRAW_BIAS_X, 0 + StaticConfig.DRAW_BIAS_Y + this.canvases[i].getHeight()), Color.RED, 5D);
            for (int j = 0; j < this.targetLanes[i].getLength(); j++) {
                if (j % 50 == 0) {
                    DrawUtils.drawHorizontalText(gc, 0 + StaticConfig.DRAW_BIAS_X - 20D, 0 + StaticConfig.DRAW_BIAS_Y + StaticConfig.CHART_Y_SKIP_MULTIPLE * j, Color.RED, String.valueOf(j), 8D);
                    DrawUtils.drawBallAtCoordinate(gc, 0 + StaticConfig.DRAW_BIAS_X - 3D, StaticConfig.DRAW_BIAS_Y + StaticConfig.CHART_Y_SKIP_MULTIPLE * j, 4, Color.RED);
                }

            }
            for (int j = 0; j < StaticConfig.INSPECT_TIME; j++) {
                DrawUtils.drawHorizontalText(gc, 0 + StaticConfig.DRAW_BIAS_X + StaticConfig.CHART_X_SKIP_MULTIPLE * j, 0 + StaticConfig.DRAW_BIAS_Y - 10D, Color.RED, String.valueOf(j), 8D);
                DrawUtils.drawBallAtCoordinate(gc,0 + StaticConfig.DRAW_BIAS_X + StaticConfig.CHART_X_SKIP_MULTIPLE * j,0 + StaticConfig.DRAW_BIAS_Y - 3D,4,Color.RED);
            }
        }
    }
}
