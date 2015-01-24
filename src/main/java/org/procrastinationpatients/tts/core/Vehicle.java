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
		return 0 ;
	}

	//速度变化规则
	public int Speed_From_VDR(int MAX_Speed){
		if( this.Cur_Spd < MAX_Speed){
			Cur_Spd++ ;
		}
		int safety_distance = cont.getSafetyDistanceByID(Cur_Loc) ;
		this.Cur_Spd = (safety_distance < this.Cur_Spd) ? (safety_distance) : (this.Cur_Spd) ;
		return this.Cur_Spd ;
	}

	public int getCur_Spd(){
		return this.Cur_Spd ;
	}
}
