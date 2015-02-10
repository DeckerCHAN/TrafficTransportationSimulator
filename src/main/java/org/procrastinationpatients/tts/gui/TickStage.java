package org.procrastinationpatients.tts.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.procrastinationpatients.tts.source.StaticConfig;

public abstract class TickStage extends Stage {

    private Timeline timeline;

    protected TickStage() {
        this.setTimeline(new Timeline(new KeyFrame(Duration.millis(StaticConfig.TICK_INTERVAL), this.getTickHandler())));
        this.getTimeline().setCycleCount(Animation.INDEFINITE);
    }

    protected abstract void drawAllDynamic();

    protected abstract void drawAllStatic();

    protected EventHandler<ActionEvent> getTickHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Long start = System.currentTimeMillis();
                drawAllDynamic();
                Long end = System.currentTimeMillis();
                if (StaticConfig.DEBUG_MODE || StaticConfig.OUTPUT_DRAW_TIME) {
                    System.out.println(String.format("Draw cost %s ms.", end - start));
                }


            }
        };
    }

    protected Timeline getTimeline() {
        return timeline;
    }

    protected void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }
}
