package org.procrastinationpatients.tts.entities;

import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.utils.RandomUtils;
import org.procrastinationpatients.tts.utils.VehicleList;

import java.util.LinkedList;


public class Produce implements Runnable{

	private Margin[] margins ;
	private int i = 0 ;
	private VehicleList allVehicle = new VehicleList();

	public Produce(){
		this.margins = Engine.getInstance().getMargins() ;
	}

	@Override
	public void run() {
		try {

			while (true) {
				if (getIsStopped()) {
					return;
				} else if (this.getIsPaused()) {
					Thread.sleep(1);
					continue;
				}
				if (Movement.flag && allVehicle.getCount() < 300) {
					Movement.flag = false;
					produceVehicles();
					int time = (int) (Production.getTime_to_Generation() * 100);
					Movement.flag = true ;
					Thread.sleep(time + 100);
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

		public void produceVehicles(){

			int start = RandomUtils.getRandomNumber(margins.length);
//			int start = 11 ;
			if (margins[start] != null) {
				int line ;
				if(margins[start].getFirstInputLaneIndex() == 0){
					line = RandomUtils.getStartLine() ;
				}else{
					line = RandomUtils.getStartLine() + 3 ;
				}
				Lane lane = margins[start].getConnectedLink().getLanes()[2] ;
				Vehicle vehicle = new Vehicle(lane);
				vehicle.setId(i);
				vehicle.setSpeed(1,RandomUtils.getRandomSped());
				vehicle.setCur_Loc(0);
				vehicle.setCur_line(2);
				vehicle.setGoal_line(line);
				lane.addVehicle(vehicle);
				allVehicle.add(vehicle);
				i++;
			}
		}
	public VehicleList getAllVehicle(){
		return this.allVehicle;
	}

	public Boolean getIsPaused() {
		return Engine.getInstance().getIsPaused();
	}

	public Boolean getIsStopped() {
		return Engine.getInstance().getIsStopped();
	}

}

