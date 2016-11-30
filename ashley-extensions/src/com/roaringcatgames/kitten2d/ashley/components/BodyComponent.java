package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

/**
 * Component holds a Box2D Body object
 */
public class BodyComponent implements Component, Pool.Poolable {

    public Body body;

    public static BodyComponent create(Engine engine){
        return engine.createComponent(BodyComponent.class);
    }

    public BodyComponent setBody(Body body){
        this.body = body;
        return this;
    }

    @Override
    public void reset() {
        this.body = null;
    }
}