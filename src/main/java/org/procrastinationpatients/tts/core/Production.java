package org.procrastinationpatients.tts.core;

import java.util.LinkedList;
import java.util.Random;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class Production {

	/**
	 * 单例对象实例
	 */
	private static Production instance;
	private Margin[] margins;
	private LinkedList<Vehicle> allVehicles = new LinkedList();

	/**
	 * 获取单例
	 *
	 * @return 全局唯一单例
	 */
	public static Production getInstance() {
		return instance;
	}

	static {
		//初始化单例
		instance = new Production();
	}

	//不断执行
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

	//产生Vehicle
	public void produceVehicles() {

		int start = new Random().nextInt(margins.length);
		if (margins[start] != null) {
			Vehicle vehicle = new Vehicle(margins[start]);
			vehicle.setMAX_Speed(5);
			margins[start].addVehicle(vehicle);
			allVehicles.add(vehicle);
		}
	}

	public void setMargins(Margin[] margins) {
		this.margins = margins;
	}

	public LinkedList<Vehicle> getAllVehicles() {
		return this.allVehicles;
	}
}
