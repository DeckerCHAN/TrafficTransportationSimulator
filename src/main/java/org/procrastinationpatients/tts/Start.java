package org.procrastinationpatients.tts;

import javafx.application.Application;
import org.procrastinationpatients.tts.gui.MainWindow;

/**
 * @Author Decker
 */
public class Start {
    public static void main(String[] args) {
        try {
            Application.launch(MainWindow.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
