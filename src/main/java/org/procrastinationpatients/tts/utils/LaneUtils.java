package org.procrastinationpatients.tts.utils;

import org.procrastinationpatients.tts.entities.Lane;


public class LaneUtils {
    public static void connectLane(Lane... laneSequence) {

        for (int i = 0; i < laneSequence.length - 1; i++) {
            laneSequence[i].getOutputs().add(laneSequence[i + 1]);
            laneSequence[i + 1].getInputs().add(laneSequence[i]);
        }
    }
}
