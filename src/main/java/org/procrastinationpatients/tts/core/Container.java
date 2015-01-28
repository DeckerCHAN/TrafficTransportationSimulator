package org.procrastinationpatients.tts.core;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @Author Decker & his father -- Jeffrey
 */
public interface Container {
	public LinkedList<Vehicle> getVehicles();

	public boolean addVehicle(Vehicle vehicle);

	public void removeVehicle(Vehicle vehicle) ;

	public int getLineLength();

	public void setLineLength(int length);

	public int changeLine(Vehicle vehicle);

	public int getSafetyDistanceByID(int whichLine, int index);

	public boolean canChangeLine(Vehicle vehicle);

	public void changeToNextContainer(Vehicle vehicle);

	public Vehicle getNextVehicle(Vehicle vehicle);

	public boolean hasVehicle(int line, int loc) ;

	public void toGoalLine(Vehicle vehicle) ;
}
