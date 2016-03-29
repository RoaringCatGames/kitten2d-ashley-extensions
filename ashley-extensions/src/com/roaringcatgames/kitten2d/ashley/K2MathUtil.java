package com.roaringcatgames.kitten2d.ashley;

import java.util.Random;

/**
 * Created by barry on 3/5/16 @ 2:07 PM.
 */
public class K2MathUtil {

    private static Random r = new Random();

    public static double getSineYForTime(double period, double scale, double timePosition){
        return  Math.sin(timePosition*2*Math.PI/period)*(scale/2);
    }

    public static float getRandomInRange(float min, float max){
        return (r.nextFloat()* (max-min)) + min;
    }
}
