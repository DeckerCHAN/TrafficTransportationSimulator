package org.procrastinationpatients.tts.entities;

import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.utils.VehicleList;

import java.util.Iterator;
import java.util.LinkedList;


public class Movement implements Runnable {

	public volatile static boolean flag = true ;
	private VehicleList vehicles;
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
					for (int i = 0; i <= vehicles.getMaxIndex(); i++) {
						Vehicle vehicle = vehicles.getVehicles()[i];
						if (vehicle != null) {
							if (vehicle.getOn_Link() == null) {
								vehicles.remove(i);
							}else{
								vehicle.Speed_From_VDR();
//								System.out.println(vehicle.getId() + "-->" + vehicle.getCur_line() + "、" + vehicle.getCur_Loc() + "、" + vehicle.getCur_Spd());
								vehicle.move_Next_Location();
//								System.out.println(vehicle.getId() + "-->" + vehicle.getCur_line() + "、" + vehicle.getCur_Loc() + "、" + vehicle.getCur_Spd());
							}
						}
					}
					flag = true;
				}
				Thread.sleep(200);
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
