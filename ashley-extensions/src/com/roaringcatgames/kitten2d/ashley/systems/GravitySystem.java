package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.components.KinematicComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;

/**
 * Created by barry on 12/12/15 @ 1:13 AM.
 */
public class GravitySystem extends IteratingSystem {

    private Vector2 gravity;
    private Array<Entity> gravityQueue;

    private ComponentMapper<VelocityComponent> vm;

    public GravitySystem(Vector2 gravity){
        super(Family.all(VelocityComponent.class)
                .exclude(KinematicComponent.class).get());
        if(gravity == null){
            throw new IllegalArgumentException("Gravity System must have a non-null gravity");
        }

        this.gravity = gravity;
        this.vm = ComponentMapper.getFor(VelocityComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent vel = vm.get(entity);
        vel.speed.add(gravity.cpy().scl(deltaTime));
    }
}
