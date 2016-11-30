package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.BodyComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;


/**
 * A System to run Box2D physics simulations. Updates the TransformComponent on
 *  the entity to match the current phsyics position updates.
 */
public class Box2DPhysicsSystem extends IteratingSystem {

    private static final float MAX_STEP_TIME = 1/45f;
    private static float accumulator = 0f;

    private World world;
    private Array<Entity> bodiesQueue;

    public Box2DPhysicsSystem(World world) {
        super(Family.all(BodyComponent.class, TransformComponent.class).get());

        this.world = world;
        this.bodiesQueue = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        if(accumulator >= MAX_STEP_TIME) {
            world.step(MAX_STEP_TIME, 6, 2);
            accumulator -= MAX_STEP_TIME;

            //Entity Queue
            for (Entity entity : bodiesQueue) {
                TransformComponent tfm = K2ComponentMappers.transform.get(entity);
                BodyComponent bodyComp = K2ComponentMappers.body.get(entity);
                Vector2 position = bodyComp.body.getPosition();
                tfm.position.x = position.x;
                tfm.position.y = position.y;
                tfm.rotation = bodyComp.body.getAngle() * MathUtils.radiansToDegrees;
            }
        }

        bodiesQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        bodiesQueue.add(entity);
    }
}