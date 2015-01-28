package org.procrastinationpatients.tts.entities;

/**
 * Created by jeffrey on 2015/1/29.
 */
public interface FunctionalObject {
	public int getSafetyDistanceByID(int whichLine, int index);

	public Vehicle getNextVehicle(Vehicle vehicle);

	public abstract boolean canChangeLine(Vehicle vehicle);

	public abstract void changeToNextContainer(Vehicle vehicle);

	public abstract void toGoalLine(Vehicle vehicle);

	public abstract int changeLine(Vehicle vehicle);

	public abstract int getLane_Length();

	public boolean addVehicle(Vehicle vehicle);

	public void removeVehicle(Vehicle vehicle);

}
