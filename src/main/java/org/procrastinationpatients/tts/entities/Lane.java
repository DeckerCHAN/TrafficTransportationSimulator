package org.procrastinationpatients.tts.entities;

import javafx.geometry.Point2D;
import org.procrastinationpatients.tts.utils.RandomUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Lane {

    private int line;
    private int Length;
    private Point2D[] vehiclePositions;
    private List<Lane> inputs;
    private List<Lane> outputs;
    private Vehicle[] vehicles;
    private TrafficLight trafficLight;
    private ArrayList<Barrier> barriers;

    private FunctionalObject parent;

    public Lane(FunctionalObject parent, int line) {
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.parent = parent;
        this.line = line;
        this.barriers = new ArrayList<>();
    }

    public Lane(FunctionalObject parent, int line, TrafficLight trafficLight) {
        this(parent, line);
        this.trafficLight = trafficLight;
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public int getLine() {
        return this.line;
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

    public FunctionalObject getParent() {
        return this.parent;
    }

    public Integer getLength() {
        return this.Length;
    }

    public void setLength(Integer length) {
        this.vehicles = new Vehicle[length];
        this.vehiclePositions = new Point2D[length];
        this.Length = length;
    }

    public void addVehicle(Vehicle vehicle) {
        int Cur_loc = vehicle.getCur_Loc();
        vehicles[Cur_loc] = vehicle;
    }

    public boolean removeVehicle(Vehicle vehicle) {
        int v_location = vehicle.getCur_Loc();
        if (vehicles[v_location] != null) {
            vehicles[v_location] = null;
            return true;
        }
        return false;
    }

    public void addBarrier(Barrier barrier) {
        this.getBarriers().add(barrier);
        int start = barrier.getStart();
        int end = barrier.getStart() + barrier.getLength() - 1;
        for (int i = start; i <= end; i++) {
            Vehicle vehicle = new Vehicle(this);
			vehicle.setStop(true);
			vehicle.setCur_Loc(i);
			vehicle.setCur_Spd(0);
			vehicles[i] = vehicle;
        }
    }

    public int getSafetyDistanceByID(int index) {
        for (int i = index + 1; i < this.Length; i++) {
            if (vehicles[i] != null) {
				return i - index - 5;
            }
        }

        if (this.outputs.size() != 0 && this.outputs.get(0).getTrafficLight() != null) {
            if (this.outputs.get(0).getTrafficLight().isRedLight()) {
				return this.Length - index - 5;
            }
        }
        return this.Length;
    }

    public Vehicle getNextVehicle(Vehicle vehicle) {
        int v_location = vehicle.getCur_Loc();

        for (int i = v_location + 1; i < this.Length; i++) {
            if (vehicles[i] != null)
                return vehicles[i];
        }
        return null;
    }

    public void changeToNextContainer(Vehicle vehicle) {

		//如果还在地图内
		if (outputs.size() != 0 && outputs != null) {
			//找到距离车最近的一辆车或障碍物(依旧未移动到下一个Container)
			for(int k = vehicle.getCur_Loc()+1 ; k < this.Length ; k++){
				//移动到最近的一个位置
				if(vehicles[k] != null){
					vehicles[vehicle.getCur_Loc()] = null;
					vehicle.setCur_Loc(k-1);
					vehicles[k-1] = vehicle;
					return;
				}
			}

			Lane outputLane ;
			if(outputs.size() == 3){
				if(vehicle.getMAX_Speed() == 2){
					outputLane = outputs.get(0);
				}
				else if(vehicle.getMAX_Speed() > 2 && vehicle.getMAX_Speed() < 5){
					outputLane = outputs.get(1);
				}else{
					outputLane = outputs.get(2);
				}
			}else{
				outputLane = outputs.get(0);
			}

			for(int j = 0 ; j <= (vehicle.getCur_Loc()+vehicle.getCur_Spd() - this.Length) ; j++){
				//如果有车
				if(outputLane.getVehicles()[j] != null){
					//如果第一个位置就有车
					if(j == 0){
						vehicles[vehicle.getCur_line()] = null ;
						vehicle.setCur_Loc(this.Length - 1);
						vehicles[this.Length-1] = vehicle;
						return;
					}else{
						this.removeVehicle(vehicle);
						vehicle.setCur_Loc(j-1);
						vehicle.setOn_Link(outputLane);
						vehicle.setCur_line(outputLane.getLine());
						outputLane.addVehicle(vehicle);
						return;
					}
				}
			}
			this.removeVehicle(vehicle);
			vehicle.setCur_Loc(vehicle.getCur_Loc() + vehicle.getCur_Spd() - this.Length);
			vehicle.setOn_Link(outputLane);
			vehicle.setCur_line(outputLane.getLine());
			outputLane.addVehicle(vehicle);
			return;
		}else{
			this.removeVehicle(vehicle);
			vehicle.setOn_Link(null);
			vehicle.setEnd_TIME(System.currentTimeMillis());
		}
	}

	public void updateVehicle(Vehicle vehicle) {
		int Cur_Loc = vehicle.getCur_Loc();
		int new_loc = Cur_Loc + vehicle.getCur_Spd();
		for(int i = Cur_Loc+1; i <= new_loc ; i++){
			if( vehicles[i] != null){
				vehicles[Cur_Loc] = null;
				vehicle.setCur_Loc(i-1);
				vehicles[i-1] = vehicle;
				return;
			}
		}
		vehicles[Cur_Loc] = null;
		vehicle.setCur_Loc(new_loc);
		vehicles[new_loc] = vehicle;
	}

	public ArrayList<Barrier> getBarriers() {
		return barriers;
	}

	public Point2D[] getVehiclePositions() {
		return vehiclePositions;
	}
}
