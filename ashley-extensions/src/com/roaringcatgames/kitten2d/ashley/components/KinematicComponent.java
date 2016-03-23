package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 12/12/15 @ 11:36 PM.
 */
public class KinematicComponent implements Component, Pool.Poolable{
    public static KinematicComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(KinematicComponent.class);
        }else {
            return new KinematicComponent();
        }
    }

    @Override
    public void reset() {

    }
}
