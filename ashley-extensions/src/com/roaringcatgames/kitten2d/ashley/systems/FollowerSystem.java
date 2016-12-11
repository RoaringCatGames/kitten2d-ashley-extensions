package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.VectorUtils;
import com.roaringcatgames.kitten2d.ashley.components.FollowMode;
import com.roaringcatgames.kitten2d.ashley.components.FollowerComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Created by barry on 4/26/16 @ 7:31 PM.
 */
public class FollowerSystem extends IteratingSystem {

    ComponentMapper<FollowerComponent> fm;
    ComponentMapper<TransformComponent> tm;

    private Vector2 moveToAdjustment = new Vector2();

    Array<Entity> queue;
    Family familyToWatchForRemovals;

    public FollowerSystem(Family familyToWatchForRemovals){
        super(Family.all(FollowerComponent.class).get());
        this.queue = new Array<>();
        fm = ComponentMapper.getFor(FollowerComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
        this.familyToWatchForRemovals = familyToWatchForRemovals;
    }

    EntityListener el;
    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        final Engine eg = engine;
        if(el == null){
            el = new EntityListener() {
                private ComponentMapper<FollowerComponent> fm = ComponentMapper.getFor(FollowerComponent.class);

                public void entityAdded(Entity entity) {

                }

                @Override
                public void entityRemoved(Entity entity) {
                    for (Entity follower : eg.getEntitiesFor(Family.all(FollowerComponent.class).get())) {
                        FollowerComponent fc = fm.get(follower);
                        if (fc != null && fc.target == entity) {
                            fc.target = null;
                            follower.removeAll();
                            eg.removeEntity(follower);
                        }
                    }

                }
            };
        }

        engine.addEntityListener(familyToWatchForRemovals, el);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        if(el != null) {
            engine.removeEntityListener(el);
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        FollowerComponent fc = fm.get(entity);
        if(fc.target != null) {
            TransformComponent targetPos = tm.get(fc.target);
            if (targetPos != null) {
                TransformComponent tc = tm.get(entity);
                Vector2 offset = VectorUtils.rotateVector(fc.offset, targetPos.rotation);

                switch (fc.followMode) {
                    case STICKY:
                        tc.position.set(targetPos.position.x + offset.x,
                                targetPos.position.y + offset.y,
                                tc.position.z);
                        tc.setOpacity(targetPos.tint.a);
                        tc.setRotation(fc.baseRotation + targetPos.rotation);
                        break;
                    case MOVETO:
                        processMoveToAdjustment(deltaTime, fc, targetPos, tc, offset);
                        break;
                    case MOVETOSTICKY:
                        processMoveToAdjustment(deltaTime, fc, targetPos, tc, offset);
                        if(tc.position.x == targetPos.position.x && tc.position.y == targetPos.position.y){
                            fc.setMode(FollowMode.STICKY);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void processMoveToAdjustment(float deltaTime, FollowerComponent fc, TransformComponent targetPos, TransformComponent tc, Vector2 offset) {
        moveToAdjustment.set(targetPos.position.x + offset.x, targetPos.position.y + offset.y);
        moveToAdjustment.sub(tc.position.x, tc.position.y)
                        .nor()
                        .scl(fc.followSpeed * deltaTime);

        float newX = tc.position.x + moveToAdjustment.x;
        float newY = tc.position.y + moveToAdjustment.y;

        if(tc.position.x < targetPos.position.x){
            newX = MathUtils.clamp(newX, tc.position.x, targetPos.position.x);
        }else{
            newX = MathUtils.clamp(newX, targetPos.position.x, tc.position.x);
        }

        if(tc.position.y < targetPos.position.y){
            newY = MathUtils.clamp(newY, tc.position.y, targetPos.position.y);
        }else{
            newY = MathUtils.clamp(newY, targetPos.position.y, tc.position.y);
        }

        tc.position.set(newX, newY, tc.position.z);

        tc.setOpacity(targetPos.tint.a);
        tc.setRotation(fc.baseRotation + targetPos.rotation);
    }
}