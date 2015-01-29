package org.procrastinationpatients.tts.entities;

import org.procrastinationpatients.tts.utils.RandomUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by decker on 15-1-28.
 */
public class Lane {

	private int Length;
    private List<Lane> inputs;
    private List<Lane> outputs;
    private Vehicle [] vehicles;

	private LinkedList<Vehicle> allVehicles ;
    private FunctionalObject parent;

    public Lane(FunctionalObject parent) {
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
		this.allVehicles = new LinkedList<>();
		this.parent = parent;
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

	public FunctionalObject getParent(){
		return this.parent ;
	}

    public void setVehicles(Vehicle[] vehicles) {
        this.vehicles = vehicles;
    }

    public Integer getLength() {
        return this.Length;
    }

    public void setLength(Integer length) {
        this.vehicles=new Vehicle[length];
		this.Length = length ;
    }

	public void addVehicle(Vehicle vehicle){
		int Cur_loc = vehicle.getCur_Loc() ;
		int temp = 0 ;
		for(int i = 0 ; i <= Cur_loc ; i++){
			if(vehicles[i] == null){
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

	public int getSafetyDistanceByID(int index) {
		if (index < 0 || index >= this.Length)
			return -1;
		if (vehicles[index] == null)
			return 0;

		for (int i = index + 1; i < this.Length; i++) {
			if (vehicles[i] != null) {
				return i - index ;
			}
		}

		return this.Length - index ;
	}

	public Vehicle getNextVehicle(Vehicle vehicle) {
		int v_location = vehicle.getCur_Loc();

		for (int i = v_location + 1; i < this.Length; i++) {
			if (vehicles[i] != null)
				return vehicles[i];
		}
		return null;
	}

	public void changeToNextContainer(Vehicle vehicle){
		this.removeVehicle(vehicle);
		if(outputs.size()!=0 && outputs != null){
			System.out.print("OutPut Size!!" + outputs.size());
			Lane outputLane = outputs.get(RandomUtils.getStartLine(outputs.size())) ;
			vehicle.setCur_Loc(vehicle.getCur_Loc() + vehicle.getCur_Spd() - this.getLength());
			System.out.println(outputLane.getLength()) ;
			System.out.println(outputLane.getParent()) ;
			outputLane.addVehicle(vehicle);
		}
	}

	public void updateVehicle(Vehicle vehicle){
		int Cur_Loc = vehicle.getCur_Loc() ;
		vehicles[Cur_Loc] = null ;
		vehicle.setCur_Loc(vehicle.getCur_Loc() + vehicle.getCur_Spd());
		vehicles[vehicle.getCur_Loc()] = vehicle ;
	}
}
