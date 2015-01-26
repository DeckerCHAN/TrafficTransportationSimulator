package org.procrastinationpatients.tts.core;

import java.util.Collection;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class Vehicle {

	private int Cur_Spd;    //当前速度
	private int Cur_Loc;    //当前位置
	private int Cur_line;   //当前线路

	Container pare_cont; //当前所在的容器

	//构造方法
	public Vehicle(Container container) {
		this.pare_cont = container;
	}

	//速度变化规则
	public int Speed_From_VDR(int MAX_Speed) {
		if (this.Cur_Spd < MAX_Speed) {
			Cur_Spd++;
		}
		int safety_distance = pare_cont.getSafetyDistanceByID(Cur_line, Cur_Loc);
		this.Cur_Spd = (safety_distance < this.Cur_Spd) ? (safety_distance) : (this.Cur_Spd);
		return this.Cur_Spd;
	}

	//终于开始能动了啦
	public int move_Next_Location() {
		Vehicle nextVehicle = pare_cont.getNextVehicle(this);
		if (this.Cur_Spd + this.Cur_Loc > pare_cont.getLineLength())
			pare_cont.changeToNextContainer(this);
		if (this.Cur_Spd > nextVehicle.getCur_Spd())
			if (pare_cont.canChangeLine(this))
				pare_cont.changeLine(this);
		this.Cur_Loc = this.Cur_Loc + this.Cur_Spd;
		return this.Cur_Loc;
	}

	public void setCur_Line(int Cur_Line) {
		this.Cur_line = Cur_Line;
	}

	public void setCur_Loc(int Cur_Loc) {
		this.Cur_Loc = Cur_Loc;
	}

	public int getCur_line() {
		return Cur_line;
	}

	public int getCur_Spd() {
		return this.Cur_Spd;
	}

	public int getCur_Loc() {
		return this.Cur_Loc;
	}
}
