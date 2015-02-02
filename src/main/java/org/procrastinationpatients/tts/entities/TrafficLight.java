package org.procrastinationpatients.tts.entities;


public class TrafficLight {
	private int Light;   //0为红灯,1为绿灯
	private boolean isRightLight;

	public TrafficLight() {
		this.setLight(1);
	}

	public TrafficLight(int light, boolean isRightLight){
		this.Light = light;
		this.isRightLight = isRightLight;
	}

	public int getLight() {
		return Light;
	}

	public void setLight(int light) {
		Light = light;
	}

	public boolean isRedLight() {
		return Light == 0;
	}
}
