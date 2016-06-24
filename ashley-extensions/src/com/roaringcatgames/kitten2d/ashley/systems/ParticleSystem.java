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
 * System to spawn and clean-up particles
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
            if(pc.isPaused){
                return;
            }

            TransformComponent tc = tm.get(entity);

            for (int i = 0; i < pc.spawnRate; i++) {
                spawnParticle(pc, tc);
            }


            float secsBetweenSpawns = 1f / pc.spawnRate;
            pc.elapsedTime += deltaTime;

//            float timeThisSpawnBlock = pc.elapsedTime - pc.lastSpawnTime;
//            if (timeThisSpawnBlock >= secsBetweenSpawns) {
//                int numberToSpawnThisInterval = (int) Math.ceil(pc.spawnRate * timeThisSpawnBlock);
//
//                for (int i = 0; i < numberToSpawnThisInterval; i++) {
//                    spawnParticle(pc, tc);
//                }
//
//                pc.lastSpawnTime = pc.elapsedTime;
//            }

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

        float x = tc.position.x;
        float y = tc.position.y;
        float scale = K2MathUtil.getRandomInRange(pc.particleMinMaxScale.x, pc.particleMinMaxScale.y);
        if(pc.spawnType == ParticleSpawnType.RANDOM_IN_BOUNDS){
            x += K2MathUtil.getRandomInRange(-pc.particleSpawnRange.x, pc.particleSpawnRange.x);
            y += K2MathUtil.getRandomInRange(-pc.particleSpawnRange.y, pc.particleSpawnRange.y);
        }


        particle.add(TransformComponent.create(engine)
                .setPosition(x, y, pc.zIndex)
                .setScale(scale, scale));



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
                    .setPercentPerSecond(100f / lifeSpan));
        }

        getEngine().addEntity(particle);
    }
}