package org.procrastinationpatients.tts.utils;

import java.util.Random;


public class RandomUtils {

	private static Random random ;

	static {
		random = new Random() ;
	}

	public static int getStartLine(){
		return random.nextInt(3) ;
	}

	public static int getRandomNumber(int number){ return random.nextInt(number); }

	public static int getRandomNumber(int MIN , int MAX){
		return random.nextInt(MAX-MIN) + MIN ;
	}

	public static int getStartLine(int i){
		return random.nextInt(i) ;
	}

	public static int getRandomSped(){ return random.nextInt(5) + 5 ;}

	public static int getNewLine(int index){
		if( index - 2 <= 0 )
			return random.nextInt(3) ;
		else
			return random.nextInt(3) + 3 ;
	}
}
