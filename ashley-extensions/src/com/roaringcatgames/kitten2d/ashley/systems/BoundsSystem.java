package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.kitten2d.ashley.VectorUtils;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.CircleBoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Created by barry on 12/13/15 @ 2:20 PM.
 */
public class BoundsSystem extends IteratingSystem {

    ComponentMapper<BoundsComponent> bm;
    ComponentMapper<CircleBoundsComponent> cm;
    ComponentMapper<TransformComponent> tm;
    public BoundsSystem(){
        super(Family.all(TransformComponent.class)
                .one(BoundsComponent.class, CircleBoundsComponent.class).get());

        bm = ComponentMapper.getFor(BoundsComponent.class);
        cm = ComponentMapper.getFor(CircleBoundsComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent tfm = tm.get(entity);


        float xOffsetAdjust = tfm.scale.x >= 0f ? 1f : -1f;
        float yOffsetAdjust = tfm.scale.y >= 0f ? 1f : -1f;

        if(bm.has(entity)) {
            BoundsComponent bounds = bm.get(entity);
            Vector2 rotatedOffset = bounds.offset.cpy().scl(xOffsetAdjust, yOffsetAdjust);
            if (tfm.rotation != 0f) {
                rotatedOffset = VectorUtils.rotateVector(rotatedOffset, tfm.rotation);
            }

            bounds.bounds.x = tfm.position.x - bounds.bounds.width * 0.5f + rotatedOffset.x;
            bounds.bounds.y = tfm.position.y - bounds.bounds.height * 0.5f + rotatedOffset.y;
        }else if(cm.has(entity)){
            CircleBoundsComponent bounds = cm.get(entity);
            Vector2 rotatedOffset = bounds.offset.cpy().scl(xOffsetAdjust, yOffsetAdjust);
            if (tfm.rotation != 0f) {
                rotatedOffset = VectorUtils.rotateVector(rotatedOffset, tfm.rotation);
            }

            bounds.circle.x = tfm.position.x + rotatedOffset.x;
            bounds.circle.y = tfm.position.y + rotatedOffset.y;
        }
    }
}
