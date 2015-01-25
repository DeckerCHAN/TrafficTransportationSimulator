package org.procrastinationpatients.tts.core;

import javafx.scene.canvas.GraphicsContext;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class Cross extends VisualEntity implements Container {

    private LinkedList<Vehicle> vehicles;

    private Container connectionN;
    private Container connectionS;
    private Container connectionW;
    private Container connectionE;


    public Cross(Container connectionN, Container connectionS, Container connectionW, Container connectionE) {
        this.connectionN = connectionN;
        this.connectionS = connectionS;
        this.connectionW = connectionW;
        this.connectionE = connectionE;
        this.vehicles=new LinkedList<>();
    }

    @Override
    public Collection<Vehicle> getVehicles() {
        return this.vehicles;
    }

    @Override
    public void setVehicles() {
        this.vehicles = vehicles;
    }

    @Override
    public void drawStaticGraphic(GraphicsContext gc) {

    }

    @Override
    public void drawDynamicGraphic(GraphicsContext gc) {

    }

    @Override
    public int getSafetyDistanceByID(int whichLine, int index) {
        return 0;
    }

    @Override
    public Vehicle[][] getAllLocation() {
        return new Vehicle[0][];
    }

    @Override
    public int getLineLength() {
        return 0;
    }

    @Override
    public boolean changeToNextContainer() {
        return false;
    }

    @Override
    public int changeLine(Vehicle vehicle) {
        return 0;
    }

    @Override
    public Vehicle getNextVehichle() {
        return null;
    }

    @Override
    public boolean canChangeLine(Vehicle vehicle) {
        return false;
    }
}
