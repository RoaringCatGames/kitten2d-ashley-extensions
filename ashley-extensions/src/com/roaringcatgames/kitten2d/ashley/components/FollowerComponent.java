package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 4/26/16 @ 7:32 PM.
 */
public class FollowerComponent implements Component, Pool.Poolable{
    public Entity target = null;
    public Vector2 offset = new Vector2(0f, 0f);
    public float baseRotation = 0f;
    public boolean shouldMatchOpacity = true;
    public FollowMode followMode = FollowMode.STICKY;

    public static FollowerComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(FollowerComponent.class);
        }else{
            return new FollowerComponent();
        }

    }

    public FollowerComponent setTarget(Entity e){
        this.target = e;
        return this;
    }

    public FollowerComponent setOffset(float x, float y){
        this.offset.set(x, y);
        return this;
    }

    public FollowerComponent setMode(FollowMode mode){
        this.followMode = mode;
        return this;
    }

    public FollowerComponent setBaseRotation(float rot){
        this.baseRotation = rot;
        return this;
    }

    public FollowerComponent setMatchOpacity(boolean shouldMatch){
        this.shouldMatchOpacity = shouldMatch;
        return this;
    }

    @Override
    public void reset() {
        this.target = null;
        this.offset.set(0f, 0f);
        this.baseRotation = 0f;
        this.shouldMatchOpacity = true;
        this.followMode = FollowMode.STICKY;
    }
}
