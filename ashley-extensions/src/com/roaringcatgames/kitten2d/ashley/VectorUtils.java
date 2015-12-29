package com.roaringcatgames.kitten2d.ashley;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by barry on 12/28/15 @ 11:51 PM.
 */
public class VectorUtils {

    public static Vector2 rotateVector(Vector2 v, float degrees){
        //Implemented from http://www.collisiondetection2d.net
        float radians = MathUtils.degreesToRadians * degrees;
        float sine = MathUtils.sin(radians);
        float cosine = MathUtils.cos(radians);
        float newX = (v.x * cosine) - (v.y * sine);
        float newY = (v.x * sine) + (v.y * cosine);
        return new Vector2(newX, newY);
    }
}
