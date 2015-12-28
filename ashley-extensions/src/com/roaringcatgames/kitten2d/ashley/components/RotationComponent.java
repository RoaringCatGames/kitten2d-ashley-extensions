package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by barry on 12/27/15 @ 11:10 PM.
 */
public class RotationComponent implements Component {

    public float rotationSpeed = 0f;
    public boolean hasTargetRotation = false;
    public float targetRotation = 0f;

    public static RotationComponent create(){
        return new RotationComponent();
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
}
