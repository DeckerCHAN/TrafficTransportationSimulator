package org.procrastinationpatients.tts.core;

import javafx.scene.canvas.GraphicsContext;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @Author Decker & his father -- Jeffrey
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

	@Override
	public int getSafetyDistanceByID(int index) {
		return 0;
	}
}
