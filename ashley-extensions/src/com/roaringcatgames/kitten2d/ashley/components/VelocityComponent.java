package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 12/12/15 @ 1:07 AM.
 */
public class VelocityComponent implements Component, Pool.Poolable {
    public Vector2 speed = new Vector2();

    public static VelocityComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(VelocityComponent.class);
        }else {
            return new VelocityComponent();
        }
    }

    public VelocityComponent setSpeed(float x, float y){
        this.speed.set(x, y);
        return this;
    }

    @Override
    public void reset() {
        this.speed.set(0f, 0f);
    }
}
