package org.procrastinationpatients.tts.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    MainStage main;
    ChartStage chart;

    public MainApp() {
        super();
    }

    @Override
    public void start(Stage s) throws Exception {
        main = new MainStage();
        main.show();
    }
}
