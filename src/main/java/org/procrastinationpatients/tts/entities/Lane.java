package org.procrastinationpatients.tts.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by decker on 15-1-28.
 */
public class Lane {

    private List<Lane> inputs;
    private List<Lane> outputs;
    private Vehicle [] vehicles;
	private LinkedList<Vehicle> allVehicles ;


    public Lane() {
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
		allVehicles = new LinkedList<>();
    }

    public List<Lane> getInputs() {
        return inputs;
    }

    public void setInputs(List<Lane> inputs) {
        this.inputs = inputs;
    }

    public List<Lane> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Lane> outputs) {
        this.outputs = outputs;
    }

    public Vehicle[] getVehicles() {
        return vehicles;
    }

    public void setVehicles(Vehicle[] vehicles) {
        this.vehicles = vehicles;
    }

    public Integer getLength() {
        return this.vehicles.length;
    }

    public void setLength(Integer length) {
        this.vehicles=new Vehicle[length];
    }

	public void addVehicle(Vehicle vehicle){
		int Cur_loc = vehicle.getCur_Loc() ;
		int temp = 0 ;
		for(int i = 0 ; i >= Cur_loc ; i++){
			if(vehicles[Cur_loc] == null){
				temp = i ;
			}else{
				break;
			}
		}
		vehicles[temp] = vehicle ;
		allVehicles.add(vehicle);
	}

	public boolean removeVehicle(Vehicle vehicle){
		if(vehicle == null)
			return false;
		int v_location = vehicle.getCur_Loc();
		if(vehicles[v_location] != null){
			vehicles[v_location] = null ;
			allVehicles.remove(vehicle);
			return true ;
		}
		return false ;
	}

	public boolean removeVehicle(int location){
		if(vehicles[location] == null)
			return false ;
		vehicles[location] = null ;
		allVehicles.remove(vehicles) ;
		return true ;
	}
}
