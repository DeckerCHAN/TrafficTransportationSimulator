package org.procrastinationpatients.tts.core;

import java.util.Collection;

/**
 * @Author Decker
 */
public interface Container {
    public Collection<Vehicle> getVehicles();
    public void setVehicles();
	public int getSafetyDistanceByID(int index);
}
