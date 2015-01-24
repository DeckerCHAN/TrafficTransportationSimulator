package org.procrastinationpatients.tts.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import org.procrastinationpatients.tts.core.Drawable;

import java.util.LinkedList;

/**
 * @Author Decker
 */
public class MainWindow extends Application {

    LinkedList<Drawable> drawables;
    Canvas backgroundCanvas;
    Group rootGroup;

    public MainWindow() {
        super();
        this.backgroundCanvas=new Canvas(2014, 768);
        this.rootGroup=new Group();

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Traffic Transportation Simulator");
        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
        for (Drawable drawable : this.drawables) {
            drawable.draw(gc);
        }
        rootGroup.getChildren().add(backgroundCanvas);
        stage.setScene(new Scene(rootGroup));
        stage.show();
    }
}
