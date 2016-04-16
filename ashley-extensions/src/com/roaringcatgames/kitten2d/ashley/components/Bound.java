package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by barry on 4/16/16 @ 2:19 PM.
 */
public class Bound {

    public Rectangle rect;
    public Circle circle;
    public Vector2 offset = new Vector2(0f, 0f);

    public boolean isCircle = false;

    public Bound(Circle circle, float xOff, float yOff){
        this.circle = circle;
        this.offset.set(xOff, yOff);
        isCircle = true;
    }

    public Bound(Rectangle rect, float xOff, float yOff){
        this.rect = rect;
        this.offset.set(xOff, yOff);
        isCircle = false;
    }
}
