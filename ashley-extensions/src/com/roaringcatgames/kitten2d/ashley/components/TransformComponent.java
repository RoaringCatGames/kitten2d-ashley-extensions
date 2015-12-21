package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by barry on 12/8/15 @ 9:53 PM.
 */
public class TransformComponent implements Component {
    public final Vector3 position = new Vector3();
    public final Vector2 scale = new Vector2(1.0f, 1.0f);
    public float rotation = 0.0f;
    public boolean isHidden = false;

    public static TransformComponent create(){
        return new TransformComponent();
    }

    public TransformComponent setPosition(float x, float y){
        return setPosition(x, y, this.position.z);
    }
    public TransformComponent setPosition(float x, float y, float z){
        this.position.set(x, y, z);
        return this;
    }

    public TransformComponent setScale(float x, float y){
        this.scale.set(x, y);
        return this;
    }

    public TransformComponent setRotation(float rot){
        this.rotation = rot;
        return this;
    }

    public TransformComponent hide(){
        this.isHidden = true;
        return this;
    }

    public TransformComponent show(){
        this.isHidden = true;
        return this;
    }

    public TransformComponent setHidden(boolean isHidin){
        this.isHidden = isHidin;
        return this;
    }
}
