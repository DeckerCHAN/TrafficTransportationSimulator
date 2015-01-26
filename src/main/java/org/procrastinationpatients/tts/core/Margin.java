package org.procrastinationpatients.tts.core;

import javafx.scene.canvas.GraphicsContext;

import java.util.Collection;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class Margin extends VisualEntity implements Container {

	@Override
	public Collection<Vehicle> getVehicles() {
		return null;
	}

	@Override
	public void addVehicles(Vehicle vehicle) {

	}

	@Override
	public int getSafetyDistanceByID(int whichLine, int index) {
		return 0;
	}

	@Override
	public void drawStaticGraphic(GraphicsContext gc) {

	}

	@Override
	public void drawDynamicGraphic(GraphicsContext gc) {

	}

	@Override
	public int getLineLength() {
		return 0;
	}

	@Override
	public Container changeToNextContainer(Vehicle vehicle) {
		return null;
	}

	@Override
	public int changeLine(Vehicle vehicle) {
		return 0;
	}

	@Override
	public Vehicle getNextVehicle(Vehicle vehicle) {
		return null;
	}

	@Override
	public boolean canChangeLine(Vehicle vehicle) {
		return false;
	}
}
