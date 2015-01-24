package org.procrastinationpatients.tts.core;

/**
 * @Author Decker & his father -- Jeffrey
 *
 */
public class Vehicle {

	int Cur_Spd ;	//当前速度
	int Cur_Loc ;	//当前位置

	Container cont ; //当前所在的容器

	//构造方法
	public Vehicle(Container container) {
		this.cont = container ;
	}

	public int move_Next_Location(){
		int safety_distance = cont.getSafetyDistanceByID(Cur_Loc) ;
		return 0 ;
	}
}
