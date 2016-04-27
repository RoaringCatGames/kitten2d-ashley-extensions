package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.VectorUtils;
import com.roaringcatgames.kitten2d.ashley.components.FollowerComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Created by barry on 4/26/16 @ 7:31 PM.
 */
public class FollowerSystem extends IteratingSystem {

    ComponentMapper<FollowerComponent> fm;
    ComponentMapper<TransformComponent> tm;

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
                switch (fc.followMode) {
                    case STICKY:
                        TransformComponent tc = tm.get(entity);
                        Vector2 offset = VectorUtils.rotateVector(fc.offset, targetPos.rotation);
                        tc.position.set(targetPos.position.x + offset.x,
                                targetPos.position.y + offset.y,
                                tc.position.z);
                        tc.setOpacity(targetPos.opacity);
                        tc.setRotation(fc.baseRotation + targetPos.rotation);
                        break;
                    case MOVETO:

                        break;
                    default:
                        break;
                }
            }
        }
    }
}