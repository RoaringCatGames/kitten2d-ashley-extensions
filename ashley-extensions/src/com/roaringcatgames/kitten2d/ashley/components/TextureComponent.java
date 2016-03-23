package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 12/8/15 @ 8:30 PM.
 */
public class TextureComponent implements Component, Pool.Poolable {
    public TextureRegion region = null;

    public static TextureComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(TextureComponent.class);
        }else {
            return new TextureComponent();
        }
    }
    public TextureComponent setRegion(TextureRegion region){
        this.region = region;
        return this;
    }

    @Override
    public void reset() {
        this.region = null;
    }
}
