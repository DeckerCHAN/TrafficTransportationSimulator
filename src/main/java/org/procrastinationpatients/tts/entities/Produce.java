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

	public Produce(){
		this.margins = Engine.getInstance().getMargins() ;
	}

	@Override
	public void run() {
		while (true) {
			produceVehicles();
			for (Vehicle vehicle : allVehicles) {
				vehicle.Speed_From_VDR();
				vehicle.move_Next_Location();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

		public void produceVehicles(){

			int start = RandomUtils.getRandomNumber(margins.length);
			if (margins[start] != null) {
				Lane lane = margins[start].getConnectedLink().getLanes()[RandomUtils.getRandomNumber(3)] ;
				Vehicle vehicle = new Vehicle(lane);
				vehicle.setSpeed(1,5);
				lane.addVehicle(vehicle);
				allVehicles.add(vehicle);
			}
		}
	}

