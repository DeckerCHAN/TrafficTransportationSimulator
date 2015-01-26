package org.procrastinationpatients.tts.core;

import java.util.Collection;

/**
 * @Author Decker & his father -- Jeffrey
 */
public interface Container {
	public Collection<Vehicle> getVehicles();

	public void addVehicles(Vehicle vehicle) ;

	public int getLineLength();

	public int changeLine(Vehicle vehicle);

	public int getSafetyDistanceByID(int whichLine, int index);

	public boolean canChangeLine(Vehicle vehicle);

	public Container changeToNextContainer(Vehicle vehicle);

	public Vehicle getNextVehicle(Vehicle vehicle);
}
