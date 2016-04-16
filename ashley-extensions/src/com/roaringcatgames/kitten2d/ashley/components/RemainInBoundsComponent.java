package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 4/16/16 @ 3:40 PM.
 */
public class RemainInBoundsComponent implements Component, Pool.Poolable {
    public BoundMode mode = BoundMode.CONTAINED;

    public static RemainInBoundsComponent create(Engine engine){
        if(engine instanceof PooledEngine) {
            return ((PooledEngine)engine).createComponent(RemainInBoundsComponent.class);
        }else{
            return new RemainInBoundsComponent();
        }
    }

    public RemainInBoundsComponent setMode(BoundMode m){
        this.mode = m;
        return this;
    }

    @Override
    public void reset() {
        this.mode = BoundMode.CONTAINED;
    }
}
