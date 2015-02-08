package org.procrastinationpatients.tts.entities;


public class Barrier extends IdentifiableObject {

	private int start ;
	private int length ;
	private Vehicle[] vehicles;

    public Barrier(int id, int start, int length) {
        super(id);
        this.start = start;
        this.length = length ;
		this.vehicles = new Vehicle[length];
	}

	public void createBarrier(Lane lane){
		for(int i = 0 ; i < vehicles.length ; i++){
			vehicles[i] = new Vehicle(lane);
			vehicles[i].setStop(true);
		}
		lane.addBarrier(this);
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return start + length;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Vehicle[] getVehicles() {
		return vehicles;
	}

	public void setVehicles(Vehicle[] vehicles) {
		this.vehicles = vehicles;
	}
}
