package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.kitten2d.ashley.components.PathFollowComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Created by barry on 2/29/16 @ 6:24 PM.
 */
public class PathFollowSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<PathFollowComponent> pm;


    public PathFollowSystem(){
        super(Family.all(TransformComponent.class, PathFollowComponent.class).get());
        tm = ComponentMapper.getFor(TransformComponent.class);
        pm = ComponentMapper.getFor(PathFollowComponent.class);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PathFollowComponent pc = pm.get(entity);
        if(!pc.isPaused && pc.path != null){

            float adjust = pc.speed*deltaTime;
            float newPosition = Math.min(pc.pathPosition + adjust, 1f);
            if(newPosition == 1f){
                pc.setPaused(true);
            }
            pc.setPathPosition(newPosition);
//            float newElapsed = Math.min((pc.elapsedTime + deltaTime), pc.totalPathTime);
//            if(newElapsed == pc.totalPathTime){
//                pc.setPaused(true);
//            }
            //pc.setElapsedTime(newElapsed);

            //float pathTime = pc.elapsedTime/pc.totalPathTime;
            pc.path.valueAt(pc.point, pc.pathPosition); //pathTime);

            TransformComponent tc = tm.get(entity);
            tc.setPosition(pc.point.x, pc.point.y);

            if(pc.isFacingPath) {
                pc.path.derivativeAt(pc.point, pc.pathPosition);//pathTime);
                //We add 90 degrees to our angle, because the angle
                //  starts
                tc.rotation = pc.baseRotation + (pc.point.angle() - 90f);
            }
        }
    }
}
