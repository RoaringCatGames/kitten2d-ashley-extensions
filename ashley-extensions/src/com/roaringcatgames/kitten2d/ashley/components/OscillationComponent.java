package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 4/26/16 @ 7:40 PM.
 */
public class OscillationComponent implements Component, Pool.Poolable {
    public float minRotation = 0f;
    public float maxRotation = 0f;
    public float rotationSpeed = 1f;
    public boolean isClockwise = false;

    public static OscillationComponent create(Engine engine){
        if(engine instanceof PooledEngine) {
            return ((PooledEngine)engine).createComponent(OscillationComponent.class);
        }else{
            return new OscillationComponent();
        }
    }

    public OscillationComponent setMinimumRotation(float minRot){
        this.minRotation = minRot;
        return this;
    }

    public OscillationComponent setMaximumRotation(float maxRot){
        this.maxRotation = maxRot;
        return this;
    }

    public OscillationComponent setSpeed(float rotSpeed){
        this.rotationSpeed = rotSpeed;
        return this;
    }

    public OscillationComponent setClockwise(boolean isClockwise){
        this.isClockwise = isClockwise;
        return this;
    }

    @Override
    public void reset() {
        this.minRotation = 0f;
        this.maxRotation = 0f;
        this.rotationSpeed = 1f;
        this.isClockwise = false;
    }
}