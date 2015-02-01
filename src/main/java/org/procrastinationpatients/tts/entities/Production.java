package org.procrastinationpatients.tts.entities;

import org.procrastinationpatients.tts.utils.RandomUtils;

/**
 * Created by jeffrey on 2015/1/29.
 */
public class Production {

	private static double Time_to_Generation;

	private final static double Simution_time = 0.2 ;
	private final static double LAMBDA  = 0.5 ;

	public static double getTime_to_Generation(){
		double x = Get_Random_Number(0,1000000) ;
		x = x / 1000000 ;
		Time_to_Generation = Math.log(x) * (1 / LAMBDA)  * -1;
		return Time_to_Generation + Simution_time ;
	}

	public static int Get_Random_Number(int Min, int Max){
		int ran = RandomUtils.getRandomNumber(Min , Max) ;
		return ran + Min ;
	}

}
