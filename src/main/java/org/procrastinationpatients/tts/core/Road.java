package org.procrastinationpatients.tts.core;

/**
 * 南北走向的路
 *
 * @Author Decker
 */
public class Road extends Link {

    public Road(Cross connectionN, Cross connectionS) {
        super(connectionN, connectionS);
    }


    public Cross getConnectionN()
    {
        return super.getContainerA();
    }

    public Cross getConnectionS()
    {
        return super.getContainerB();
    }

	@Override
	public void addVehicles(Vehicle vehicle) {

	}

	@Override
	public Container changeToNextContainer(Vehicle vehicle) {
		return null;
	}

	@Override
	public Vehicle getNextVehicle(Vehicle vehicle) {
		return null;
	}
}
