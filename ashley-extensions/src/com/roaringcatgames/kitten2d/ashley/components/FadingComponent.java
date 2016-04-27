package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 3/3/16 @ 10:08 PM.
 */
public class FadingComponent implements Component, Pool.Poolable {

    public float percentPerSecond = 5f;

    public static FadingComponent create(Engine engine){
        if(engine instanceof PooledEngine) {
            return ((PooledEngine)engine).createComponent(FadingComponent.class);
        }else{
            return new FadingComponent();
        }
    }

    public FadingComponent setPercentPerSecond(float pfps){
        this.percentPerSecond = pfps;
        return this;
    }

    @Override
    public void reset() {
        this.percentPerSecond = 5f;
    }
}