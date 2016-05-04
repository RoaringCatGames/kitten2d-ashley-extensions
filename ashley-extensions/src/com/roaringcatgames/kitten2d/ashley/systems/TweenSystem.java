package com.roaringcatgames.kitten2d.ashley.systems;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.TweenComponent;

/**
 * Will hold a TweenManager and iterate over tweens automatically.
 * Once finished the tweens will be removed
 */
public class TweenSystem extends IteratingSystem {
    private TweenManager tweenManager;
    private TweenAccessor<Entity> defaultAccesor;

    public TweenSystem(){
        super(Family.all(TweenComponent.class).get());
        this.tweenManager = new TweenManager();
        this.defaultAccesor = new K2EntityTweenAccessor();
    }

    public TweenSystem(TweenAccessor<Entity> customDefault){
        super(Family.all(TweenComponent.class).get());
        this.tweenManager = new TweenManager();
        this.defaultAccesor = customDefault;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        tweenManager.update(deltaTime);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        Tween.registerAccessor(Entity.class, defaultAccesor);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
       TweenComponent tc = K2ComponentMappers.twm.get(entity);
        for(Tween tween:tc.tweens) {
            if (tween.isStarted()) {
                if (tween.isFinished()) {
                    entity.remove(TweenComponent.class);
                }
            } else {
                tween.start(tweenManager);
            }
        }

    }
}
