package com.roaringcatgames.kitten2d.ashley.example.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.kitten2d.ashley.systems.BoundsSystem;
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
    protected void update(float deltaChange) {





        super.update(deltaChange);
    }

    @Override
    protected void childInit() {
        engine.addSystem(new TweenSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BoundsSystem());

        Entity cat = engine.createEntity();
        Tween tweenPos = Tween.to(cat, K2EntityTweenAccessor.POSITION, 3)
                            .ease(TweenEquations.easeInOutSine)
                            .target(1f, 2f, 100f)
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
                             100f)
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


        Entity catStill = engine.createEntity();
        catStill.add(TextureComponent.create(engine).setRegion(catTexture));
        catStill.add(TransformComponent.create(engine)
                .setPosition(3.5f, 9f, 50f)
                .setScale(0.10f, 0.10f)
                .setTint(Color.CORAL));
        engine.addEntity(catStill);

        //POSITION_X Tween Example
        Entity catX = engine.createEntity();
        catX.add(TextureComponent.create(engine).setRegion(catTexture));
        catX.add(TransformComponent.create(engine)
                .setTint(255f, 0f, 0f, 1f)
                .setPosition(3f, 9f, 50f)
                .setScale(0.10f, 0.10f));
        catX.add(VelocityComponent.create(engine).setSpeed(0f, 0.5f));
        catX.add(TweenComponent.create(engine)
                .addTween(Tween.to(catX, K2EntityTweenAccessor.POSITION_X, 1f)
                        .target(5f)
                        .repeatYoyo(Tween.INFINITY, 0)));
        engine.addEntity(catX);

        //POSITION_Y
        Entity catY = engine.createEntity();
        catY.add(TextureComponent.create(engine).setRegion(catTexture));
        catY.add(TransformComponent.create(engine)
                .setTint(0f, 255f, 0f, 1f)
                .setPosition(3f, 9f, 50f)
                .setScale(0.10f, 0.10f));
        catY.add(VelocityComponent.create(engine).setSpeed(0.5f, 0f));
        catY.add(TweenComponent.create(engine)
                .addTween(Tween.to(catY, K2EntityTweenAccessor.POSITION_Y, 1f)
                        .target(12f)
                        .repeatYoyo(Tween.INFINITY, 0)));
        engine.addEntity(catY);

        //POSITION_Z
        Entity catZ = engine.createEntity();
        catZ.add(TextureComponent.create(engine).setRegion(catTexture));
        catZ.add(TransformComponent.create(engine)
                .setTint(0f, 0f, 255f, 1f)
                .setPosition(3f, 9f, 1f)
                .setScale(0.10f, 0.10f));
        catZ.add(TweenComponent.create(engine)
                .addTween(Tween.to(catZ, K2EntityTweenAccessor.POSITION_Z, 2f)
                        .target(100f)
                        .repeatYoyo(Tween.INFINITY, 0)));
        engine.addEntity(catZ);

        //POSITION_XY
        Entity catXY = engine.createEntity();
        catXY.add(TextureComponent.create(engine).setRegion(catTexture));
        catXY.add(TransformComponent.create(engine)
                .setTint(255f, 0f, 255f, 1f)
                .setPosition(3f, 9f, 100f)
                .setScale(0.10f, 0.10f));
        catXY.add(TweenComponent.create(engine)
                .addTween(Tween.to(catXY, K2EntityTweenAccessor.POSITION_XY, 1f)
                        .target(1f, 4f)
                        .repeatYoyo(Tween.INFINITY, 0)));
        engine.addEntity(catXY);

        Entity circleThing = engine.createEntity();
        circleThing.add(TransformComponent.create(engine)
                .setPosition(3f, 9f, 100f)
                .setScale(1f, 1f));
        circleThing.add(CircleBoundsComponent.create(engine)
            .setCircle(0f, 0f, 2f));
        circleThing.add(TweenComponent.create(engine)
            .addTween(Tween.to(circleThing, K2EntityTweenAccessor.BOUNDS_RADIUS, 2f)
                    .target(0.5f)
                    .repeatYoyo(Tween.INFINITY, 0)));
        engine.addEntity(circleThing);

        Entity squareThing = engine.createEntity();
        squareThing.add(TransformComponent.create(engine)
                .setPosition(3f, 9f, 100f)
                .setScale(1f, 1f));
        squareThing.add(BoundsComponent.create(engine)
                .setBounds(3f, 9f, 3f, 6f));
        squareThing.add(TweenComponent.create(engine)
                .addTween(Tween.to(squareThing, K2EntityTweenAccessor.BOUNDS_XY, 2f)
                        .target(0.5f, 0.5f)
                        .repeatYoyo(Tween.INFINITY, 0)));
        engine.addEntity(squareThing);
    }


}
