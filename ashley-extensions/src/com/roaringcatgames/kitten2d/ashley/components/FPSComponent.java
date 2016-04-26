package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

public class FPSComponent implements Component, Pool.Poolable {


    @Override
    public void reset() {

    }

    public static FPSComponent create(Engine e){
        if(e instanceof PooledEngine) {
            return ((PooledEngine)e).createComponent(FPSComponent.class);
        }else{
            return new FPSComponent();
        }
    }
}