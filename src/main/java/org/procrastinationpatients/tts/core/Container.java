package org.procrastinationpatients.tts.core;

import java.util.Collection;

/**
 * @Author Decker & his father -- Jeffrey
 */
public interface Container {
    public Collection<Vehicle> getVehicles();
    public void setVehicles();
    public int getSafetyDistanceByID(int whichLine , int index);
	public Vehicle[][] getAllLocation();
	public int getLineLength();
	public boolean changeToNextContainer() ;
	public Vehicle getNextVehichle() ;

	public int changeLine(Vehicle vehicle) ;
	public boolean canChangeLine(Vehicle vehicle);
}
