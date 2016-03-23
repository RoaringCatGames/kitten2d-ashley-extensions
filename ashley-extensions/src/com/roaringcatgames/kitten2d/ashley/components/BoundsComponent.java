package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 12/12/15 @ 1:28 PM.
 */
public class BoundsComponent implements Component, Pool.Poolable {

    public Rectangle bounds = new Rectangle();
    public Vector2 offset = new Vector2(0f, 0f);

    public static BoundsComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(BoundsComponent.class);
        }else {
            return new BoundsComponent();
        }
    }

    public BoundsComponent setBounds(Rectangle r){
        this.bounds = r;
        return this;
    }

    public BoundsComponent setBounds(float x, float y, float width, float height){
        this.bounds.set(x, y, width, height);
        return this;
    }

    public BoundsComponent setOffset(float x, float y){
        this.offset.set(x, y);
        return this;
    }

    @Override
    public void reset() {
        this.bounds.set(0f, 0f, 0f, 0f);
        this.offset.set(0f, 0f);
    }
}
