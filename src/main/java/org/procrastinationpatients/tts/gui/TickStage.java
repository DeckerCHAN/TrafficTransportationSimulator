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
    private Long tickCounts;

    protected TickStage() {
        this.setTickCounts(Long.valueOf(0));
        this.setTimeline(new Timeline(new KeyFrame(Duration.millis(StaticConfig.TICK_INTERVAL), this.getTickHandler())));
        this.getTimeline().setCycleCount(Animation.INDEFINITE);
    }

    protected abstract void drawAllDynamic();

    protected abstract void drawAllStatic();

    protected EventHandler<ActionEvent> getTickHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tick();
            }
        };
    }

    protected void tick() {
        Long start = System.currentTimeMillis();
        drawAllDynamic();
        Long end = System.currentTimeMillis();
        if (StaticConfig.DEBUG_MODE || StaticConfig.OUTPUT_DRAW_TIME) {
            System.out.println(String.format("Draw cost %s ms.", end - start));
        }
        setTickCounts(getTickCounts() + 1);
    }

    public void pause() {
        this.getTimeline().stop();
    }

    public void resume() {
        this.getTimeline().play();
    }

    public void start() {
        this.getTimeline().play();
    }

    protected Timeline getTimeline() {
        return timeline;
    }

    protected void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Long getTickCounts() {
        return tickCounts;
    }

    public void setTickCounts(Long tickCounts) {
        this.tickCounts = tickCounts;
    }
}
