package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 1/17/16 @ 4:16 PM.
 */
public class CircleBoundsComponent implements Component, Pool.Poolable {

    public Circle circle = new Circle();
    public Vector2 offset = new Vector2(0f, 0f);

    public static CircleBoundsComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(CircleBoundsComponent.class);
        }else {
            return new CircleBoundsComponent();
        }
    }

    public CircleBoundsComponent setCircle(float x, float y, float radius){
        this.circle.set(x, y, radius);
        return this;
    }

    public CircleBoundsComponent setOffset(float x, float y){
        this.offset.set(x, y);
        return this;
    }

    @Override
    public void reset() {
        this.circle.set(0f, 0f, 0f);
        this.offset.set(0f, 0f);
    }
}
