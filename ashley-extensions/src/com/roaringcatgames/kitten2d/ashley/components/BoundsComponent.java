package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by barry on 12/12/15 @ 1:28 PM.
 */
public class BoundsComponent implements Component {

    public Rectangle bounds = new Rectangle();
    public Vector2 offset = new Vector2(0f, 0f);

    public static BoundsComponent create(){
        return new BoundsComponent();
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
}
