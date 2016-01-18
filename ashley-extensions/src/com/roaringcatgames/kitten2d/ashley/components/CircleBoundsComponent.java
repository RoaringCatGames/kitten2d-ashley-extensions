package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by barry on 1/17/16 @ 4:16 PM.
 */
public class CircleBoundsComponent implements Component {

    public Circle circle = new Circle();
    public Vector2 offset = new Vector2(0f, 0f);

    public static CircleBoundsComponent create(){
        return new CircleBoundsComponent();
    }

    public CircleBoundsComponent setCircle(float x, float y, float radius){
        this.circle.set(x, y, radius);
        return this;
    }

    public CircleBoundsComponent setOffset(float x, float y){
        this.offset.set(x, y);
        return this;
    }
}
