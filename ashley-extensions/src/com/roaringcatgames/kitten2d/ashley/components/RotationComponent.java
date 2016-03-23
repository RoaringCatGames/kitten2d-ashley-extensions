package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 12/27/15 @ 11:10 PM.
 */
public class RotationComponent implements Component, Pool.Poolable {

    public float rotationSpeed = 0f;
    public boolean hasTargetRotation = false;
    public float targetRotation = 0f;

    public static RotationComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(RotationComponent.class);
        }else {
            return new RotationComponent();
        }
    }

    public RotationComponent setRotationSpeed(float speed){
        this.rotationSpeed = speed;
        return this;
    }

    public RotationComponent setTargetRotation(float target){
        this.targetRotation = target;
        return this;
    }

    public RotationComponent setHasTargetRotation(boolean hasTarget){
        this.hasTargetRotation = hasTarget;
        return this;
    }

    @Override
    public void reset() {
        this.rotationSpeed = 0f;
        this.hasTargetRotation = false;
        this.targetRotation = 0f;
    }
}
