package org.procrastinationpatients.tts.core;

import javafx.scene.canvas.GraphicsContext;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @Author Decker
 */
public class Cross implements Container,Drawable {

    private LinkedList<Vehicle> vehicles;

    @Override
    public void draw(GraphicsContext gc) {

    }

    @Override
    public Collection<Vehicle> getVehicles() {
        return this.vehicles;
    }

    @Override
    public void setVehicles() {
        this.vehicles=vehicles;
    }
}
