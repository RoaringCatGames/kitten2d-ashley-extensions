package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 12/13/15 @ 5:48 PM.
 */
public class HealthComponent implements Component, Pool.Poolable {

    public float health = 1f;
    public float maxHealth = 1f;

    public static HealthComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(HealthComponent.class);
        }else {
            return new HealthComponent();
        }
    }

    public HealthComponent setMaxHealth(float maxHealth){
        this.maxHealth = maxHealth;
        return this;
    }

    public HealthComponent setHealth(float health){
        this.health = health;
        return this;
    }

    @Override
    public void reset() {
        this.health = 1f;
        this.maxHealth = 1f;
    }
}
