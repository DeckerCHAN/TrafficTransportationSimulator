package org.procrastinationpatients.tts;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.core.VisualEntity;

import java.util.LinkedList;

/**
 * @Author Decker
 */
public class Test {
    public static void main(String[] args) {
        try {
            Engine engine = Engine.getInstance();
            LinkedList<VisualEntity> visualEntities = new LinkedList<>();

            engine.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
