package org.procrastinationpatients.tts.utils;

import org.procrastinationpatients.tts.entities.Vehicle;

/**
 * Created by jeffrey on 2015/2/2.
 */
public class VehicleList {

	private volatile Vehicle[] vehicles;
	private volatile int maxIndex;
	private int count;

	public VehicleList(){
		vehicles = new Vehicle[300] ;
		maxIndex = 0 ;
		count = 0 ;
	}

	public void add(Vehicle vehicle){
		for(int i = 0 ; i < 300 ; i++){
			if(vehicles[i] == null){
				vehicles[i] = vehicle;
				if(i > maxIndex){
					maxIndex = i ;
				}
				count++;
				return ;
			}
		}
	}

	public void remove(Vehicle vehicle){
		for(int i = 0 ; i < 300 ; i++){
			if(vehicles[i] == vehicle){
				vehicles[i] = null;
				if(i==maxIndex){
					updateMaxIndex();
				}
				count--;
				return;
			}
		}
	}

	public void remove(int i){
		vehicles[i] = null ;
		updateMaxIndex();
		count--;
	}

	public int getCount(){
		return this.count;
	}

	public void updateMaxIndex(){
		for(int i = 0 ; i < 300 ; i++){
			if(vehicles[i] != null){
				if(i >= maxIndex){
					maxIndex = i;
				}
			}
		}
	}

	public Vehicle[] getVehicles(){
		return this.vehicles;
	}

	public int getMaxIndex(){
		return this.maxIndex;
	}

}
