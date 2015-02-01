package org.procrastinationpatients.tts.entities;

import org.procrastinationpatients.tts.core.Engine;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by jeffrey on 2015/1/30.
 */
public class Movement implements Runnable {

	public static boolean flag = true ;
	private LinkedList<Vehicle> allVehicles = new LinkedList<>();
	private LinkedList<Vehicle> cacheVehicle = new LinkedList<>();
	public Movement(LinkedList<Vehicle> allVehicles){
		this.allVehicles = allVehicles;
	}

	@Override
	public void run() {
		try {
		while(true){
			if (getIsStopped()) {
				return;
			} else if (getIsPaused()) {
				Thread.sleep(1);
				continue;
			}
			System.out.println("=============MoveMent==============") ;
			flag = false ;
			Iterator<Vehicle> it = allVehicles.iterator() ;
			while(it.hasNext()){
				Vehicle vehicle = it.next() ;
				if(vehicle.getOn_Link() == null){
					cacheVehicle.add(vehicle);
					continue;
				}
				vehicle.Speed_From_VDR();
				System.out.println(vehicle.getCur_line() + "、" + vehicle.getCur_Loc() + "、" +vehicle.getCur_Spd()) ;
				System.out.println(vehicle.move_Next_Location());
				System.out.println(vehicle.getCur_line() + "、" + vehicle.getCur_Loc() + "、" +vehicle.getCur_Spd()) ;
			}
			flag = true ;
			for(Vehicle vehicle : cacheVehicle){
				allVehicles.remove(vehicle) ;
			}
			cacheVehicle.clear();
			Thread.sleep(100);


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
