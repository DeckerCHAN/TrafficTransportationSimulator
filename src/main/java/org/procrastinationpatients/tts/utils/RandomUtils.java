package org.procrastinationpatients.tts.utils;

import java.util.Random;

/**
 * Created by jeffrey on 2015/1/26.
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
}
