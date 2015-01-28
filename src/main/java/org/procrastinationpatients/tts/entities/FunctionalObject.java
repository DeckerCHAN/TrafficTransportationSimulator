package org.procrastinationpatients.tts.entities;

/**
 * Created by jeffrey on 2015/1/29.
 */
public interface FunctionalObject {
	public int getSafetyDistanceByID(int Cur_line, int Cur_Loc);

	public Vehicle getNextVehicle(Vehicle vehicle);

	public abstract boolean canChangeLine(Vehicle vehicle);

	public abstract boolean changeLine(Vehicle vehicle);

	public abstract boolean changeToNextContainer(Vehicle vehicle);

	public abstract boolean toGoalLine(Vehicle vehicle);

	public abstract int getLineLength();
}
