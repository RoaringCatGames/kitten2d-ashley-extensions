package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 3/13/16 @ 2:37 PM.
 */
public class ParticleEmitterComponent implements Component, Pool.Poolable {

    public Array<TextureRegion> particleImages = new Array<>();
    public float elapsedTime = 0f;
    public float lastSpawnTime = 0f;
    public float spawnRate = 100f;
    public float duration = 1f;
    public Vector2 particleMinMaxSpeeds = new Vector2(1f, 1f);
    public Vector2 particleMinMaxLifespans = new Vector2(1f, 1f);
    public Vector2 particleSpawnRange = new Vector2(0f, 0f);
    public Vector2 particleMinMaxScale = new Vector2(1f, 1f);
    public Range angleRange = new Range(-45f, 45f);
    public boolean shouldFade = false;
    public boolean isLooping = false;
    public float zIndex = 100f;
    public ParticleSpawnType spawnType = ParticleSpawnType.FROM_CENTER;

    public static ParticleEmitterComponent create(Engine engine) {

        if(engine instanceof PooledEngine) {
            return ((PooledEngine)engine).createComponent(ParticleEmitterComponent.class);
        }else{
            return new ParticleEmitterComponent();
        }
    }

    public ParticleEmitterComponent setParticleImage(TextureRegion region){
        this.particleImages.clear();
        this.particleImages.add(region);
        return this;
    }
    public ParticleEmitterComponent setParticleImages(Array<? extends TextureRegion> particleImages) {
        this.particleImages.clear();
        this.particleImages.addAll(particleImages);
        return this;
    }

    public ParticleEmitterComponent setSpawnRate(float spawnRate) {
        this.spawnRate = spawnRate;
        return this;
    }

    public ParticleEmitterComponent setParticleLifespans(float minLifespan, float maxLifespan) {
        this.particleMinMaxLifespans.set(minLifespan, maxLifespan);
        return this;
    }

    public ParticleEmitterComponent setAngleRange(float min, float max) {
        this.angleRange.setMin(min);
        this.angleRange.setMax(max);
        return this;
    }

    public ParticleEmitterComponent setShouldFade(boolean isFading) {
        this.shouldFade = isFading;
        return this;
    }

    public ParticleEmitterComponent setShouldLoop(boolean shouldLoop) {
        this.isLooping = shouldLoop;
        return this;
    }

    public ParticleEmitterComponent setDuration(float duration) {
        this.duration = duration;
        return this;
    }

    public ParticleEmitterComponent setSpawnType(ParticleSpawnType spawnType){
        this.spawnType = spawnType;
        return this;
    }

    public ParticleEmitterComponent setSpawnRange(float xOffset, float yOffset){
        this.particleSpawnRange.set(xOffset, yOffset);
        return this;
    }

    public ParticleEmitterComponent setSpeed(float min, float max) {
        this.particleMinMaxSpeeds.set(min, max);
        return this;
    }

    public ParticleEmitterComponent setParticleMinMaxScale(float min, float max){
        this.particleMinMaxScale.set(min, max);
        return this;
    }

    public ParticleEmitterComponent setZIndex(float index) {
        this.zIndex = index;
        return this;
    }

    @Override
    public void reset() {
        this.particleImages.clear();
        this.elapsedTime = 0f;
        this.lastSpawnTime = 0f;
        this.spawnRate = 100f;
        this.duration = 1f;
        this.particleMinMaxSpeeds.set(1f, 1f);
        this.particleMinMaxLifespans.set(1f, 1f);
        this.particleSpawnRange.set(0f, 0f);
        this.particleMinMaxScale.set(1f, 1f);
        this.angleRange.setMin(-45f);
        this.angleRange.setMax(45f);
        this.shouldFade = false;
        this.isLooping = false;
        this.zIndex = 100f;
        this.spawnType = ParticleSpawnType.FROM_CENTER;

    }
}
