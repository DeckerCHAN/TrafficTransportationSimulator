package org.procrastinationpatients.tts.entities;

import org.procrastinationpatients.tts.utils.RandomUtils;


public class Production {

	private static double Time_to_Generation;

	private final static double Simution_time = 0.2 ;
	private final static double LAMBDA  = 0.5 ;
	private final static double SCALE = 100 ;

	public static double getTime_to_Generation(){
		double x = Get_Random_Number(0,1000000) ;
		x = x / 1000000 ;
		Time_to_Generation = Math.log(x) * (1 / LAMBDA)  * -1;
		if((Time_to_Generation + Simution_time) * SCALE < 0.5)
			return 0.5 ;
		else
			return Time_to_Generation + Simution_time ;
	}

	public static int Get_Random_Number(int Min, int Max){
		int ran = RandomUtils.getRandomNumber(Min , Max) ;
		return ran + Min ;
	}

}
