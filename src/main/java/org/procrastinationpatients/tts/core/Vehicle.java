package org.procrastinationpatients.tts.core;

import org.procrastinationpatients.tts.utils.RandomUtils;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class Vehicle {

	private int Cur_Spd;      //当前速度
	private int Cur_Loc;     //当前位置
	private int Cur_line;     //当前线路
	private int MAX_Speed;   //最大速度

	Container pare_cont; //当前所在的容器

	//构造方法
	public Vehicle(Container container) {
		this.pare_cont = container;
		this.Cur_Spd = RandomUtils.getStartSpd();
		this.Cur_line = RandomUtils.getStartLine();
	}

	//速度变化规则
	public int Speed_From_VDR(int MAX_Speed) {
		this.MAX_Speed = MAX_Speed;
		if (this.Cur_Spd < MAX_Speed) {
			Cur_Spd++;
		}
		int safety_distance = pare_cont.getSafetyDistanceByID(Cur_line, Cur_Loc);
		this.Cur_Spd = (safety_distance < this.Cur_Spd) ? (safety_distance) : (this.Cur_Spd);
		return this.Cur_Spd;
	}

	//终于开始能动了啦
	public int move_Next_Location() {

		if (this.Cur_Spd + this.Cur_Loc > pare_cont.getLineLength()) {
			pare_cont.changeToNextContainer(this);
			return 1 ;
		}

		Vehicle nextVehicle = pare_cont.getNextVehicle(this);
		if(nextVehicle != null)
			if (this.Cur_Spd > nextVehicle.getCur_Spd())
				if (pare_cont.canChangeLine(this)) {
					pare_cont.changeLine(this);
					return 2 ;
				}

		this.Cur_Loc = this.Cur_Loc + this.Cur_Spd;
		return 3;
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

	public int getMAX_Speed() {
		return MAX_Speed;
	}

	public void setCur_Spd(int Cur_Speed) {
		this.Cur_Spd = Cur_Speed;
	}

	public void setMAX_Speed(int MAX_Speed) {
		this.MAX_Speed = MAX_Speed;
	}
}
