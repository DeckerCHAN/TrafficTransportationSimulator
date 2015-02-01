package org.procrastinationpatients.tts.entities;

/**
 * Created by jeffrey on 2015/2/1.
 */
public class TrafficLight {
	private boolean hasLight;
	private int Light;   //0为红灯,1为绿灯
	private int position;

	public TrafficLight(boolean hasLight){
		this.hasLight = hasLight ;
	}

	public int getLight() {
		return Light;
	}

	public void setLight(int light) {
		Light = light;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isHasLight() {
		return hasLight;
	}

	public void setHasLight(boolean hasLight) {
		this.hasLight = hasLight;
	}
}
