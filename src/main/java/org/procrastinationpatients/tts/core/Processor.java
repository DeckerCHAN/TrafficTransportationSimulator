package org.procrastinationpatients.tts.core;

import org.procrastinationpatients.tts.entities.Movement;
import org.procrastinationpatients.tts.entities.Produce;

/**
 * Created by decker on 15-2-1.
 */
public class Processor {
    Produce produce;
    Movement movement;
    Thread produceThread;
    Thread moveThread;


    public Processor() {
        this.produce = new Produce();
        this.produceThread = new Thread(produce);
        this.movement = new Movement(produce.getAllVehicles());
        this.moveThread = new Thread(new Movement(produce.getAllVehicles()));
    }

    public void start() {
        this.produceThread.start();
        this.moveThread.start();
    }

    @Deprecated
    public void pause() {
        Engine.getInstance().setIsPaused(true);
    }

    @Deprecated
    public void resume() {
        Engine.getInstance().setIsPaused(false);
    }

    @Deprecated
    public void stop() {
        Engine.getInstance().setIsStopped(true);
    }


}
