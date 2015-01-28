package org.procrastinationpatients.tts.utils;

import java.util.Random;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class RandomUtils {

	private static Random random ;

	static {
		random = new Random() ;
	}

	public static int getStartLine(){
		return random.nextInt(3) ;
	}

	public static int getStartSpd(){
		return random.nextInt(5) ;
	}

	public static int getStartSpd(int MAX_Speed){
		return random.nextInt(MAX_Speed) ;
	}

	public static int getNewLine(int index){
		if( index - 2 <= 0 )
			return random.nextInt(3) ;
		else
			return random.nextInt(3) + 3 ;
	}
}
