package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by barry on 12/13/15 @ 5:48 PM.
 */
public class HealthComponent implements Component {

    public float health = 1f;
    public float maxHealth = 1f;

    public static HealthComponent create(){
        return new HealthComponent();
    }

    public HealthComponent setMaxHealth(float maxHealth){
        this.maxHealth = maxHealth;
        return this;
    }

    public HealthComponent setHealth(float health){
        this.health = health;
        return this;
    }
}
