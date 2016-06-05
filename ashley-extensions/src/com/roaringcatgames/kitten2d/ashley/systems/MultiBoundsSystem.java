package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.kitten2d.ashley.VectorUtils;
import com.roaringcatgames.kitten2d.ashley.components.*;

/**
 * Created by barry on 4/16/16 @ 2:54 PM.
 */
public class MultiBoundsSystem extends IteratingSystem {
    ComponentMapper<MultiBoundsComponent> bm;
    ComponentMapper<TransformComponent> tm;
    public MultiBoundsSystem(){
        super(Family.all(TransformComponent.class, MultiBoundsComponent.class).get());

        bm = ComponentMapper.getFor(MultiBoundsComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent tfm = tm.get(entity);


        float xOffsetAdjust = tfm.scale.x >= 0f ? 1f : -1f;
        float yOffsetAdjust = tfm.scale.y >= 0f ? 1f : -1f;

        MultiBoundsComponent mbc = bm.get(entity);

        for(Bound bound:mbc.bounds){

            Vector2 rotatedOffset = bound.offset.cpy().scl(xOffsetAdjust, yOffsetAdjust);
            if (tfm.rotation != 0f) {
                rotatedOffset = VectorUtils.rotateVector(rotatedOffset, tfm.rotation);
            }
            if(bound.isCircle){
                bound.circle.x = tfm.position.x + tfm.originOffset.x + rotatedOffset.x;
                bound.circle.y = tfm.position.y + tfm.originOffset.y + rotatedOffset.y;
            }else{
                bound.rect.x = tfm.position.x - (bound.rect.width * 0.5f) +
                               tfm.originOffset.x + rotatedOffset.x;
                bound.rect.y = tfm.position.y - (bound.rect.height * 0.5f) +
                               tfm.originOffset.y + rotatedOffset.y;
            }
        }
    }
}
