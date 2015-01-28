package org.procrastinationpatients.tts;

import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.entities.Visible;

import java.util.LinkedList;

/**
 * @Author Decker
 */
public class Test {
    public static void main(String[] args) {
        try {
            Engine engine = Engine.getInstance();
            LinkedList<Visible> visualEntities = new LinkedList<>();

            engine.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
