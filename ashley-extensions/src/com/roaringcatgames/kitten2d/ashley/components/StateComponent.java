package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 12/8/15 @ 8:30 PM.
 */
public class StateComponent implements Component, Pool.Poolable {
    private String state = "DEFAULT";
    public float time = 0.0f;
    public boolean isLooping = false;

    public static StateComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(StateComponent.class);
        }else {
            return new StateComponent();
        }
    }

    //Creating Chainable Component Setters to make building easier
    public StateComponent set(String newState){
        state = newState;
        time = 0.0f;
        return this;
    }

    public StateComponent setLooping(boolean isLoopin){
        this.isLooping = isLoopin;
        return this;
    }

    public String get(){
        return state;
    }

    @Override
    public void reset() {
        this.state = "DEFAULT";
        this.time = 0f;
        this.isLooping = false;
    }
}