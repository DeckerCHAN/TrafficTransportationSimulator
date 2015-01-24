package org.procrastinationpatients.tts.core;

import javafx.scene.canvas.GraphicsContext;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class Cross extends VisualEntity implements Container {

    private LinkedList<Vehicle> vehicles;


    @Override
    public Collection<Vehicle> getVehicles() {
        return this.vehicles;
    }

    @Override
    public void setVehicles() {
        this.vehicles = vehicles;
    }

    @Override
    public int getSafetyDistanceByID(int index) {
        return 0;
    }

    @Override
    public void drawStaticGraphic(GraphicsContext gc) {

    }

    @Override
    public void drawDynamicGraphic(GraphicsContext gc) {

    }
}
