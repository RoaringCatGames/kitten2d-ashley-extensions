package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Created by barry on 12/13/15 @ 2:20 PM.
 */
public class BoundsSystem extends IteratingSystem {

    ComponentMapper<BoundsComponent> bm;
    ComponentMapper<TransformComponent> tm;
    public BoundsSystem(){
        super(Family.all(BoundsComponent.class,
                TransformComponent.class).get());

        bm = ComponentMapper.getFor(BoundsComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent tfm = tm.get(entity);
        BoundsComponent bounds = bm.get(entity);

        float xOffsetAdjust = tfm.scale.x >= 0f ? 1f : -1f;
        float yOffsetAdjust = tfm.scale.y >= 0f ? 1f : -1f;

        bounds.bounds.x = tfm.position.x - bounds.bounds.width * 0.5f + (bounds.offset.x*xOffsetAdjust);
        bounds.bounds.y = tfm.position.y - bounds.bounds.height * 0.5f + (bounds.offset.y*yOffsetAdjust);
    }
}
