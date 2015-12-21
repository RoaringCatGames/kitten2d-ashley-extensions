package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;

/**
 * Created by barry on 12/12/15 @ 1:43 AM.
 */
public class MovementSystem extends IteratingSystem {

    private ComponentMapper<VelocityComponent> vm;
    private ComponentMapper<TransformComponent> tm;

    public MovementSystem(){
        super(Family.all(VelocityComponent.class, TransformComponent.class).get());
        vm = ComponentMapper.getFor(VelocityComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos = tm.get(entity);
        VelocityComponent vel = vm.get(entity);

        Vector2 adjustment = vel.speed.cpy().scl(deltaTime);
        pos.position.add(adjustment.x, adjustment.y, 0f);
    }
}
