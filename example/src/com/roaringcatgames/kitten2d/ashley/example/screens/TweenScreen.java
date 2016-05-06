package com.roaringcatgames.kitten2d.ashley.example.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.Entity;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.TweenComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import com.roaringcatgames.kitten2d.ashley.systems.MovementSystem;
import com.roaringcatgames.kitten2d.ashley.systems.TweenSystem;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * Example Screen with a TweenSystem and various tweens applied
 * to our Demo Kitten.
 */
public class TweenScreen extends BaseDemoScreen {

    public TweenScreen(IGameProcessor game) {
        super(game);
    }

    @Override
    protected void childInit() {
        engine.addSystem(new TweenSystem());
        engine.addSystem(new MovementSystem());


        Entity cat = engine.createEntity();
        Tween tweenPos = Tween.to(cat, K2EntityTweenAccessor.POSITION, 3)
                            .ease(TweenEquations.easeInOutSine)
                            .target(1f, 2f, 0f)
                            .repeatYoyo(Tween.INFINITY, 0f);
        Tween tweenScale = Tween.to(cat, K2EntityTweenAccessor.SCALE, 2)
                            .ease(TweenEquations.easeInOutElastic)
                            .target(0.5f, 0.5f)
                            .repeatYoyo(Tween.INFINITY, 1);
        Tween tweenOpacity = Tween.to(cat, K2EntityTweenAccessor.OPACITY, 3)
                            .ease(TweenEquations.easeNone)
                            .target(0.5f)
                            .repeatYoyo(3, 0);
        Tween tweenRotation = Tween.to(cat, K2EntityTweenAccessor.ROTATION, 4)
                .ease(TweenEquations.easeInBounce)
                .target(-15f)
                .repeatYoyo(Tween.INFINITY, 0.5f);
        cat.add(TransformComponent.create(engine)
                .setPosition(renderer.getCamera().position.x,
                             renderer.getCamera().position.y,
                             10f)
                .setOpacity(1f)
                .setRotation(15f));
        cat.add(TextureComponent.create(engine)
                .setRegion(catTexture));
        cat.add(TweenComponent.create(engine)
                .addTween(tweenPos)
                .addTween(tweenScale)
                .addTween(tweenOpacity)
                .addTween(tweenRotation));
        engine.addEntity(cat);


        Entity veloCat = engine.createEntity();
        Timeline tl = Timeline.createSequence()
                              .push(Tween.to(veloCat, K2EntityTweenAccessor.VELOCITY, 1f)
                                         .ease(TweenEquations.easeInSine)
                                         .target(-5f, 0f))
                              .push(Tween.to(veloCat, K2EntityTweenAccessor.VELOCITY, 1f)
                                      .ease(TweenEquations.easeOutSine)
                                      .target(5f, 0f))
                              .repeatYoyo(Tween.INFINITY, 0);

        veloCat.add(TransformComponent.create(engine)
                .setPosition(17f, 17f, 1f)
                .setScale(0.25f, 0.25f)
                .setOpacity(1f)
                .setRotation(15f));
        veloCat.add(VelocityComponent.create(engine)
            .setSpeed(0f, 0f));
        veloCat.add(TextureComponent.create(engine)
                .setRegion(catTexture));
        veloCat.add(TweenComponent.create(engine)
                .setTimeline(tl));
        engine.addEntity(veloCat);


    }
}
