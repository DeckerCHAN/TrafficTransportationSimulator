package org.procrastinationpatients.tts.entities;

import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.utils.RandomUtils;

import java.util.LinkedList;

/**
 * Created by jeffrey on 2015/1/29.
 */
public class Produce implements Runnable{

	private Margin[] margins ;
	private int i = 0 ;
	private LinkedList<Vehicle> allVehicles = new LinkedList();


	public Produce(){
		this.margins = Engine.getInstance().getMargins() ;
	}

	@Override
	public void run() {
		try {

			System.out.println("=============Produce==============");
			while (true) {
				if (getIsStopped()) {
					return;
				} else if (this.getIsPaused()) {
					Thread.sleep(1);
					continue;
				}

				if (Movement.flag && allVehicles.size()<5) {
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

//			int start = RandomUtils.getRandomNumber(margins.length);
			int start = 13 ;
			if (margins[start] != null) {
				int line ;
				if(margins[start].getFirstInputLaneIndex() == 0){
					line = RandomUtils.getStartLine() ;
				}else{
					line = RandomUtils.getStartLine() + 3 ;
				}
				Lane lane = margins[start].getConnectedLink().getLanes()[line] ;
				Vehicle vehicle = new Vehicle(lane);
				vehicle.setId(i);
				vehicle.setSpeed(1,5);
				vehicle.setCur_Loc(0);
				vehicle.setCur_line(line);
				vehicle.setId_margin(start);
				lane.addVehicle(vehicle);
				allVehicles.add(vehicle);
				i++;
			}
		}
	public LinkedList<Vehicle> getAllVehicles(){
		return this.allVehicles;
	}

	public Boolean getIsPaused() {
		return Engine.getInstance().getIsPaused();
	}

	public Boolean getIsStopped() {
		return Engine.getInstance().getIsStopped();
	}
}

