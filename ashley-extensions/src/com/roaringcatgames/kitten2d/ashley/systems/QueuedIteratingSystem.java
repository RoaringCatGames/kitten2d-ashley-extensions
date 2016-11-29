package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;

/**
 * An abstract extension of the Iterating System that queues all of the target
 *   entities into an array that can be processed in bulk during the update method.
 *
 *   Override shouldBeQueued to customize how things are added to the queue
 */
public abstract class QueuedIteratingSystem extends IteratingSystem {

    private Array<Entity> queue;
    public QueuedIteratingSystem(Family family) {
        super(family);
        queue = new Array<>();
    }

    public QueuedIteratingSystem(Family family, int priority) {
        super(family, priority);
        queue = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        processQueue(queue, deltaTime);

        queue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(shouldBeQueued(entity, deltaTime)){
            queue.add(entity);
        }
    }

    protected boolean shouldBeQueued(Entity entity, float deltaTime){
        return true;
    };
    protected abstract void processQueue(Array<Entity> queue, float deltaTime);
}
