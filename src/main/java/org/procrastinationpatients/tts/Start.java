package org.procrastinationpatients.tts;

import org.procrastinationpatients.tts.core.Engine;

/**
 * @Author Decker
 */
public class Start {
    public static void main(String[] args) {
        try {
            Engine engine = Engine.getInstance();
            engine.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
