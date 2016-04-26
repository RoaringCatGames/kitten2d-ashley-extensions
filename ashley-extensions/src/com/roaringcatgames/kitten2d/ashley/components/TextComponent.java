package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 3/1/16 @ 8:44 PM.
 */
public class TextComponent implements Component, Pool.Poolable {

    public String text = "";
    public BitmapFont font;

    public static TextComponent create(Engine engine){
        if(engine instanceof PooledEngine) {
            return ((PooledEngine)engine).createComponent(TextComponent.class);
        }else{
            return new TextComponent();
        }

    }

    public TextComponent setText(String text){
        this.text = text;
        return this;
    }

    public TextComponent setFont(BitmapFont font){
        this.font = font;
        return this;
    }

    @Override
    public void reset() {
        this.text = "";
        this.font = null;
    }
}