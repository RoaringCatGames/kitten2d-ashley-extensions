package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 12/13/15 @ 5:54 PM.
 */
public class DamageComponent implements Component, Pool.Poolable{

    public float dps = 0f;

    public static DamageComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(DamageComponent.class);
        }else {
            return new DamageComponent();
        }
    }

    public DamageComponent setDPS(float dps){
        this.dps = dps;
        return this;
    }

    @Override
    public void reset() {
        this.dps = 0f;
    }
}
