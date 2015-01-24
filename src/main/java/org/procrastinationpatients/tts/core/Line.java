package org.procrastinationpatients.tts.core;

import javafx.scene.canvas.GraphicsContext;

import java.util.Collection;

/**
 * @Author Decker
 */
public class Line extends VisualEntity implements Container {

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
    public void drawStaticGraphic(GraphicsContext gc) {

    }

    @Override
    public void drawDynamicGraphic(GraphicsContext gc) {

    }
}
