package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 4/12/16 @ 7:28 PM.
 */
public class MoveToComponent implements Component, Pool.Poolable {

    public Vector3 target = new Vector3(0f, 0f, 0f);
    public float speed = 0f;
    public boolean hasArrived = false;


    public static MoveToComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(MoveToComponent.class);
        }else{
            return new MoveToComponent();
        }
    }

    public MoveToComponent setTarget(float x, float y, float z){
        this.target.set(x, y, z);
        return this;
    }

    public MoveToComponent setTarget(float x, float y){
        this.target.set(x, y, this.target.z);
        return this;
    }

    public MoveToComponent setSpeed(float spd){
        this.speed = spd;
        return this;
    }

    public MoveToComponent setArrived(boolean hasArrived){
        this.hasArrived = hasArrived;
        return this;
    }

    @Override
    public void reset() {
        this.target.set(0f, 0f, 0f);
        this.speed = 0f;
        this.hasArrived = false;
    }
}