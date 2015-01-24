package org.procrastinationpatients.tts.core;

import javafx.scene.canvas.GraphicsContext;

import java.util.Collection;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class Margin implements Container,Drawable{

    @Override
    public Collection<Vehicle> getVehicles() {
        return null;
    }

    @Override
    public void setVehicles() {

    }

	@Override
	public int getSafetyDistanceByID(int index) {
		return 0;
	}

	@Override
    public void draw( GraphicsContext gc) {

    }
}
