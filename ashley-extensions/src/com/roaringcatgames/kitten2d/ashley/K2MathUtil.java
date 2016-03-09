package com.roaringcatgames.kitten2d.ashley;

/**
 * Created by barry on 3/5/16 @ 2:07 PM.
 */
public class K2MathUtil {

    public static double getSineYForTime(double period, double scale, double timePosition){
        return  Math.sin(timePosition*2*Math.PI/period)*(scale/2);
    }
}
