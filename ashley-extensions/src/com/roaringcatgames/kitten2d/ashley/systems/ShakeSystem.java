package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.kitten2d.ashley.K2MathUtil;
import com.roaringcatgames.kitten2d.ashley.components.ShakeComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Created by barry on 4/26/16 @ 7:52 PM.
 */
public class ShakeSystem extends IteratingSystem {

    private ComponentMapper<ShakeComponent> sm;
    private ComponentMapper<TransformComponent> tm;

    public ShakeSystem(){
        super(Family.all(ShakeComponent.class, TransformComponent.class).get());
        sm = ComponentMapper.getFor(ShakeComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ShakeComponent sc = sm.get(entity);
        if(!sc.isPaused){

            if(sc.shakeDuration > 0f && sc.currentTime >= sc.shakeDuration){
                sc.setPaused(true);
                sc.setCurrentTime(0f);
            }else {
                TransformComponent tc = tm.get(entity);


                float lastXSinePos = (float) K2MathUtil.getSineYForTime(sc.speeds.x, sc.maxOffets.x, sc.currentTime);
                float lastYSinePos = (float) K2MathUtil.getSineYForTime(sc.speeds.y, sc.maxOffets.y, sc.currentTime);
                sc.currentTime += deltaTime;
                float currentXSinePos = (float) K2MathUtil.getSineYForTime(sc.speeds.x, sc.maxOffets.x, sc.currentTime);
                float currentYSinePos = (float) K2MathUtil.getSineYForTime(sc.speeds.y, sc.maxOffets.y, sc.currentTime);

                float targetX = tc.position.x + currentXSinePos - lastXSinePos;
                float targetY = tc.position.y + currentYSinePos - lastYSinePos;
                tc.setPosition(targetX, targetY);
            }
        }
    }
}
