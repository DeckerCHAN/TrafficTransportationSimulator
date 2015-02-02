package org.procrastinationpatients.tts.tests;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class TestBorderPane extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane=new BorderPane();
        HBox top=new HBox();
        top.getChildren().add(new Button("Hello"));
        top.getChildren().add(new Button("World"));
        borderPane.setTop(top);

        ScrollPane scrollPane=new ScrollPane();
        scrollPane.setPrefSize(400D,400D);
        scrollPane.setContent(new Canvas(2000D,2000D));

        borderPane.setCenter(scrollPane);

        stage.setScene(new Scene(borderPane));
        stage.show();

    }

    public static void main(String [] args)
    {
        launch(args);
    }
}
