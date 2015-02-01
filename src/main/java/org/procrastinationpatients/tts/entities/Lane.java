package org.procrastinationpatients.tts.entities;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.TransducedAccessor_field_Boolean;
import javafx.geometry.Point2D;
import org.procrastinationpatients.tts.utils.RandomUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by decker on 15-1-28.
 */
public class Lane {

	private int line;
	private int Length;
	private Point2D [] vehiclePositions;
    private List<Lane> inputs;
    private List<Lane> outputs;
    private Vehicle [] vehicles;


	private LinkedList<Vehicle> allVehicles ;
    private FunctionalObject parent;

    public Lane(FunctionalObject parent,int line) {
		this.inputs = new ArrayList<>();
		this.outputs = new ArrayList<>();
		this.allVehicles = new LinkedList<>();
		this.parent = parent;
		this.line = line;
    }



	public LinkedList<Vehicle> getAllVehicles() {
		return allVehicles;
	}

	public int getLine(){return this.line;}

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

	public FunctionalObject getParent(){
		return this.parent ;
	}

    public Integer getLength() {
        return this.Length;
    }

    public void setLength(Integer length) {
        this.vehicles=new Vehicle[length];
		this.vehiclePositions =new Point2D[length];
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

	public void addChangeLineVehicle(Vehicle vehicle){
		for(int i = vehicle.getCur_Loc() ; i >= 0 ; i--){
			if(vehicles[i] == null)
			{
				vehicles[i] = vehicle;
				allVehicles.add(vehicle);
				break;
			}
		}
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
		for (int i = index + 1; i < this.Length; i++) {
			if (vehicles[i] != null) {
				System.out.println("++++++++++++++++++" + (i-index)) ;
				return i - index - 8 ;
			}
		}
		return this.Length ;
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
			Lane outputLane = outputs.get(RandomUtils.getStartLine(outputs.size())) ;
			vehicle.setCur_Loc(vehicle.getCur_Loc() + vehicle.getCur_Spd() - this.getLength());
			vehicle.setOn_Link(outputLane);
			vehicle.setCur_line(outputLane.getLine());
			System.out.println(this.getParent()) ;
			System.out.println(outputLane.getParent()) ;
			outputLane.addVehicle(vehicle);
		}else{
			vehicle.setOn_Link(null);
		}
	}

	public void updateVehicle(Vehicle vehicle) {
		int Cur_Loc = vehicle.getCur_Loc();
		vehicles[Cur_Loc] = null;
		vehicle.setCur_Loc(Cur_Loc + vehicle.getCur_Spd());
		vehicles[vehicle.getCur_Loc()] = vehicle;
	}

	public Point2D[] getVehiclePositions() {
		return vehiclePositions;
	}
}
