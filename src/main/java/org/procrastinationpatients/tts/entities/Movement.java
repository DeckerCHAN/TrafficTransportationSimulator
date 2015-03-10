package org.procrastinationpatients.tts.entities;

import com.sun.javafx.binding.StringFormatter;
import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.source.StaticConfig;
import org.procrastinationpatients.tts.utils.VehicleList;

import java.util.LinkedList;


public class Movement implements Runnable {

	public volatile static boolean flag = true ;
	private VehicleList vehicles;
	private LinkedList<Vehicle> showVehicles = new LinkedList<>();
	public Movement(VehicleList vehicles){ this.vehicles = vehicles;}

	@Override
	public void run() {
		try {
			while (true) {
				if (getIsStopped()) {return;} else if (getIsPaused()) {
					Thread.sleep(1);
					continue;
				}
				if (flag) {
					flag = false;
					for (int i = 0; i < 300; i++) {
						Vehicle vehicle = vehicles.getVehicles()[i];
						if (vehicle != null) {
							if (vehicle.getOn_Link() == null) {

//								showVehicles.add(vehicle);
								System.out.println(new String().format("Vehicle ID: %d, from %d to %d, Max speed %d, use time %f", vehicle.getId(), vehicle.getInputNum(), vehicle.getOutputNum(), vehicle.getMAX_Speed(), vehicle.getEnd_TIME() - vehicle.getStart_TIME()));
								vehicles.remove(i);
							}else{
								vehicle.Speed_From_VDR();
								vehicle.move_Next_Location();
							}
						}
					}
					flag = true;
				}
				Thread.sleep(StaticConfig.MOVE_TIMESLOT);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}

	public Boolean getIsPaused() {
		return Engine.getInstance().getIsPaused();
	}

	public Boolean getIsStopped() {
		return Engine.getInstance().getIsStopped();
	}
}
