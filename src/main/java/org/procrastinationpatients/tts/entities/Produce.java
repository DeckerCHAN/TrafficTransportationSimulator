package org.procrastinationpatients.tts.entities;

import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.utils.RandomUtils;

import java.util.LinkedList;

/**
 * Created by jeffrey on 2015/1/29.
 */
public class Produce implements Runnable{

	private Margin[] margins ;
	private LinkedList<Vehicle> allVehicles = new LinkedList();
	private Boolean isPaused;
	private Boolean isStopped;

	public Produce(){
		this.margins = Engine.getInstance().getMargins() ;
		this.isPaused = false;
		this.isStopped=false;
	}

	@Override
	public void run() {
		try {

			System.out.println("=============Produce==============");
			while (true) {
				if (getIsStopped()) {
					return;
				} else if (this.isPaused) {
					Thread.sleep(1);
					continue;
				}

				if (Movement.flag) {
					produceVehicles();
					int time = (int) (Production.getTime_to_Generation() * 100);
					Thread.sleep(time);
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

		public void produceVehicles(){

			int start = RandomUtils.getRandomNumber(margins.length);
			if (margins[start] != null) {
				int line ;
				if(margins[start].getFirstInputLaneIndex() == 0){
					line = RandomUtils.getStartLine() ;
				}else{
					line = RandomUtils.getStartLine() + 3 ;
				}
				Lane lane = margins[start].getConnectedLink().getLanes()[line] ;
				Vehicle vehicle = new Vehicle(lane);
				vehicle.setSpeed(1,5);
				vehicle.setCur_Loc(0);
				vehicle.setCur_line(line);
				lane.addVehicle(vehicle);
				allVehicles.add(vehicle);
			}
		}
	public LinkedList<Vehicle> getAllVehicles(){
		return this.allVehicles;
	}

	public Boolean getIsPaused() {
		return isPaused;
	}

	public void setIsPaused(Boolean isPaused) {
		this.isPaused = isPaused;
	}

	public Boolean getIsStopped() {
		return isStopped;
	}

	public void setIsStopped(Boolean isStopped) {
		this.isStopped = isStopped;
	}
}
