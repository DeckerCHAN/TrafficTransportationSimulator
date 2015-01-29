package org.procrastinationpatients.tts.entities;

import java.util.LinkedList;

/**
 * Created by jeffrey on 2015/1/30.
 */
public class Movement implements Runnable {

	private LinkedList<Vehicle> allVehicles;
	public Movement(LinkedList<Vehicle> allVehicles){
		this.allVehicles = allVehicles;
	}
	@Override
	public void run() {
		while(true){
			for (Vehicle vehicle : allVehicles) {
				vehicle.Speed_From_VDR();
				System.out.println(vehicle.getCur_line() + "、" + vehicle.getCur_Loc() + "、" +vehicle.getCur_Spd()) ;
				System.out.println(vehicle.move_Next_Location());
				System.out.println(vehicle.getCur_line() + "、" + vehicle.getCur_Loc() + "、" +vehicle.getCur_Spd()) ;
				System.out.println("\n") ;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
