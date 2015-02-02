package org.procrastinationpatients.tts.entities;


public interface FunctionalObject {

	public abstract boolean canChangeLine(Vehicle vehicle);

	public abstract void changeToNextContainer(Vehicle vehicle);

	public abstract void toGoalLine(Vehicle vehicle);

	public abstract int changeLine(Vehicle vehicle);

	public abstract int getLane_Length();

	public boolean addVehicle(Vehicle vehicle);

	public void removeVehicle(Vehicle vehicle);

}
