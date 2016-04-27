package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 3/13/16 @ 4:30 PM.
 */
public class ParticleComponent implements Component, Pool.Poolable {

    public float lifespan = 1f;
    public float timeAlive = 0f;

    public static ParticleComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(ParticleComponent.class);
        }else{
            return new ParticleComponent();
        }

    }

    public ParticleComponent setLifespan(float life){
        this.lifespan = life;
        return this;
    }

    @Override
    public void reset() {
        this.lifespan = 1f;
        this.timeAlive = 0f;
    }
}