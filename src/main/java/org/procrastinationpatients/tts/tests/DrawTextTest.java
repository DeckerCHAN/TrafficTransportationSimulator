package org.procrastinationpatients.tts.tests;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.procrastinationpatients.tts.utils.DrawUtils;

/**
 * Created by Decker on 2015/2/10.
 */
public class DrawTextTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas=new Canvas(500,500);
        GraphicsContext gc= canvas.getGraphicsContext2D();
        DrawUtils.drawVerticalText(gc,400D,200D, Color.RED,"HAHAHA",20D);



        StackPane root=new StackPane();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
