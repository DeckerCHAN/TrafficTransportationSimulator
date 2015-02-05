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

    private LinkedList<Vehicle> allVehicles;
    private FunctionalObject parent;

    public Lane(FunctionalObject parent, int line) {
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.allVehicles = new LinkedList<>();
        this.parent = parent;
        this.line = line;
        this.barriers=new ArrayList<>();
    }

    public Lane(FunctionalObject parent, int line, TrafficLight trafficLight) {
        this(parent, line);
        this.trafficLight = trafficLight;
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public LinkedList<Vehicle> getAllVehicles() {
        return allVehicles;
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
        int temp = 0;
        for (int i = 0; i <= Cur_loc; i++) {
            if (vehicles[i] == null) {
                temp = i;
            } else {
                break;
            }
        }
        vehicles[temp] = vehicle;
        allVehicles.add(vehicle);
    }

    public void addChangeLineVehicle(Vehicle vehicle) {
        for (int i = vehicle.getCur_Loc(); i >= 0; i--) {
            if (vehicles[i] == null) {
                vehicles[i] = vehicle;
                allVehicles.add(vehicle);
                break;
            }
        }
    }

    public boolean removeVehicle(Vehicle vehicle) {
        if (vehicle == null)
            return false;
        int v_location = vehicle.getCur_Loc();
        if (vehicles[v_location] != null) {
            vehicles[v_location] = null;
            allVehicles.remove(vehicle);
            return true;
        }
        return false;
    }

    public boolean removeVehicle(int location) {
        if (vehicles[location] == null)
            return false;
        vehicles[location] = null;
        allVehicles.remove(vehicles);
        return true;
    }

    public void addBarrier(Barrier barrier) {
        this.getBarriers().add(barrier);
        int start = barrier.getStart();
        int end = barrier.getStart() + barrier.getLength() - 1;
        Vehicle[] vehicles1 = barrier.getVehicles();
        for (int i = start, k = 0; i <= end; i++, k++) {
            vehicles[i] = vehicles1[k];
        }
    }

    public int getSafetyDistanceByID(int index) {
        for (int i = index + 1; i < this.Length; i++) {
            if (vehicles[i] != null) {
				if(i - index - 5 == 0){
					System.out.println("++++++++++ByID: 车距:" + i + "、" + index);
					return i - index - 5;
				}

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
		if (outputs.size() != 0 && outputs != null) {
			for(int k = vehicle.getCur_Loc()+1 ; k < this.Length ; k++){
				if(vehicles[k] != null){
					vehicles[vehicle.getCur_Loc()] = null;
					vehicle.setCur_Loc(k-1);
					vehicles[k-1] = vehicle;
					return;
				}
			}
			Lane outputLane = outputs.get(RandomUtils.getStartLine(outputs.size()));
			for(int j = 0 ; j <= (vehicle.getCur_Loc()+vehicle.getCur_Spd() - this.Length) ; j++){
				if(outputLane.getVehicles()[j] != null){
					if(j == 0){
						for(int i = vehicle.getCur_Loc() + 1 ; i < this.Length ; i++){
							if(vehicles[i] != null){
								vehicles[vehicle.getCur_Loc()] = null;
								vehicle.setCur_Loc(i-1);
								vehicles[i-1] = vehicle;
								return;
							}
						}
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
			vehicle.setCur_Loc(vehicle.getCur_Loc()+vehicle.getCur_Spd() - this.Length);
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
