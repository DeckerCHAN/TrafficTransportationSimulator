package org.procrastinationpatients.tts.utils;

/**
 * Created by Decker on 2015/2/8.
 */
public class MathUtils {
    public static Integer getMaxInteger(Integer... integers) {
        Integer max=Integer.MAX_VALUE;
        for (Integer inst:integers)
        {
            if(inst>max)
            {
                max=inst;
            }
        }
        return max;
    }
}
