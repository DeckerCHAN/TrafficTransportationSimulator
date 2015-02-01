package org.procrastinationpatients.tts;

import org.procrastinationpatients.tts.core.Engine;

/**
 * @Author Decker
 */
public class Test {
    public static void main(String[] args) {
        try {
            Engine engine = Engine.getInstance();
            engine.launch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
