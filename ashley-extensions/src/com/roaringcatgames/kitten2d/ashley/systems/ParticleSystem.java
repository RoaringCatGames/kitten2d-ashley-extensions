package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.kitten2d.ashley.K2MathUtil;
import com.roaringcatgames.kitten2d.ashley.VectorUtils;
import com.roaringcatgames.kitten2d.ashley.components.*;

import java.util.Random;

/**
 * Created by barry on 3/13/16 @ 3:25 PM.
 */
public class ParticleSystem extends IteratingSystem {

    private static Random r = new Random();

    private ComponentMapper<ParticleEmitterComponent> pem;
    private ComponentMapper<ParticleComponent> pm;
    private ComponentMapper<TransformComponent> tm;

    public ParticleSystem(){
        super(Family.all(TransformComponent.class)
                .one(ParticleEmitterComponent.class, ParticleComponent.class).get());
        pem = ComponentMapper.getFor(ParticleEmitterComponent.class);
        pm = ComponentMapper.getFor(ParticleComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        if(pm.has(entity)) {
            //Release particles
            ParticleComponent pc = pm.get(entity);
            pc.timeAlive += deltaTime;
            if(pc.timeAlive >= pc.lifespan){
                getEngine().removeEntity(entity);
            }

        }else if(pem.has(entity)) {
            ParticleEmitterComponent pc = pem.get(entity);
            TransformComponent tc = tm.get(entity);

            float secsBetweenSpawns = 1f / pc.spawnRate;
            pc.elapsedTime += deltaTime;

            float timeThisSpawnBlock = pc.elapsedTime - pc.lastSpawnTime;
            if (timeThisSpawnBlock >= secsBetweenSpawns) {
                int numberToSpawnThisInterval = (int) Math.ceil(pc.spawnRate * timeThisSpawnBlock);

                for (int i = 0; i < numberToSpawnThisInterval; i++) {
                    spawnParticle(pc, tc);
                }

                pc.lastSpawnTime = pc.elapsedTime;
            }

            //Once it is done, remove
            if (!pc.isLooping && pc.elapsedTime >= pc.duration) {
                entity.remove(pc.getClass());
            }
        }
    }

    Vector2 upNorm = new Vector2(0f, 1f).nor();
    private void spawnParticle(ParticleEmitterComponent pc, TransformComponent tc){

        PooledEngine engine = (PooledEngine)getEngine();
        Entity particle = engine.createEntity();

        particle.add(TransformComponent.create(engine)
                .setPosition(tc.position.x, tc.position.y, pc.zIndex)
                .setScale(0.5f, 0.5f));

        float lifeSpan = K2MathUtil.getRandomInRange(pc.particleMinMaxLifespans.x, pc.particleMinMaxLifespans.y); //(r.nextFloat() * (pc.particleMinMaxLifespans.y - pc.particleMinMaxLifespans.x)) + pc.particleMinMaxLifespans.x;
        particle.add(ParticleComponent.create(engine)
                .setLifespan(lifeSpan));


        float angle = tc.rotation + (pc.angleRange.getBetween(r.nextFloat()));
        float particleSpeed = K2MathUtil.getRandomInRange(pc.particleMinMaxSpeeds.x, pc.particleMinMaxSpeeds.y);
        Vector2 speed = VectorUtils.rotateVector(upNorm.cpy(), angle).scl(particleSpeed);
        particle.add(VelocityComponent.create(engine)
                .setSpeed(speed.x, speed.y));

        particle.add(TextureComponent.create(engine)
                .setRegion(pc.particleImages.get(r.nextInt(pc.particleImages.size))));

        if(pc.shouldFade) {
            particle.add(FadingComponent.create(engine)
                    .setPercentPerSecond(100f/lifeSpan));
        }

        getEngine().addEntity(particle);
    }
}