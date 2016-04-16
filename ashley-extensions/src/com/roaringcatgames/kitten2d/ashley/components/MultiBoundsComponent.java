package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 4/16/16 @ 2:18 PM.
 */
public class MultiBoundsComponent implements Component, Pool.Poolable {
    public Array<Bound> bounds = new Array<Bound>();

    public static MultiBoundsComponent create(Engine engine){
        if(engine instanceof PooledEngine) {
            return ((PooledEngine) engine).createComponent(MultiBoundsComponent.class);
        }else{
            return new MultiBoundsComponent();
        }
    }

    public MultiBoundsComponent addBound(Bound bound){
        this.bounds.add(bound);
        return this;
    }

    @Override
    public void reset() {
        this.bounds.clear();
    }
}
