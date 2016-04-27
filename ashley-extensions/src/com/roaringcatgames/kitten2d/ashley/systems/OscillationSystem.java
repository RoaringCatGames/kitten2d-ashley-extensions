package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.kitten2d.ashley.components.OscillationComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Created by barry on 4/26/16 @ 7:40 PM.
 */
public class OscillationSystem extends IteratingSystem {

    private ComponentMapper<OscillationComponent> om;
    private ComponentMapper<TransformComponent> tm;

    public OscillationSystem(){
        super(Family.all(OscillationComponent.class, TransformComponent.class).get());
        om = ComponentMapper.getFor(OscillationComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        OscillationComponent oc = om.get(entity);
        TransformComponent tc = tm.get(entity);

        boolean isAboveMax = tc.rotation >= oc.maxRotation;
        boolean isBelowMin = tc.rotation <= oc.minRotation;

        if(isAboveMax || isBelowMin){
            if(isAboveMax){
                tc.setRotation(oc.maxRotation);
            }else{
                tc.setRotation(oc.minRotation);
            }
            oc.isClockwise = !oc.isClockwise;
        }


        float direction = oc.isClockwise ? -1 : 1;
        float adjustment = oc.rotationSpeed * direction * deltaTime;
        tc.setRotation(tc.rotation+adjustment);
    }
}