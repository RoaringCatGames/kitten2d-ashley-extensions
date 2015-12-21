package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by barry on 12/12/15 @ 1:07 AM.
 */
public class VelocityComponent implements Component {
    public Vector2 speed = new Vector2();

    public static VelocityComponent create(){
        return new VelocityComponent();
    }

    public VelocityComponent setSpeed(float x, float y){
        this.speed.set(x, y);
        return this;
    }
}
