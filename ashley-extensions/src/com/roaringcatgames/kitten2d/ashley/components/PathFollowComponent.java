package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 2/29/16 @ 6:14 PM.
 */
public class PathFollowComponent  implements Component, Pool.Poolable {

    public Vector2 point = new Vector2();
    public Path<Vector2> path;
    public boolean isFacingPath = false;
    public float baseRotation = 0f;
    public boolean isPaused = false;

    /**
     * Path Lifecycles Per Second
     */
    public float speed = 5f;
    /**
     * Position on path from 0f-1f where 0 is p0,
     * and 1f is pEnd
     */
    public float pathPosition = 0f;


    public static PathFollowComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(PathFollowComponent.class);
        }else {
            return new PathFollowComponent();
        }
    }

    public PathFollowComponent setPath(Path<Vector2> path){
        this.path = path;
        return this;
    }


    public PathFollowComponent setSpeed(float spd){
        this.speed = spd;
        return this;
    }

    public PathFollowComponent setPathPosition(float position){
        this.pathPosition = position;
        return this;
    }

    public PathFollowComponent setPaused(boolean shouldPause){
        this.isPaused = shouldPause;
        return this;
    }

    public PathFollowComponent setFacingPath(boolean shouldFacePath){
        this.isFacingPath = shouldFacePath;
        return this;
    }

    public PathFollowComponent setBaseRotation(float baseRotation){
        this.baseRotation = baseRotation;
        return this;
    }

    @Override
    public void reset() {
        this.path = null;
        this.point.set(0f, 0f);
        this.speed = 5f;
        this.pathPosition = 0f;
        this.isFacingPath = false;
        this.baseRotation = 0f;
    }
}
