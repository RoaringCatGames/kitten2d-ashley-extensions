package com.roaringcatgames.kitten2d.ashley.example.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.RotationComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.TweenComponent;
import com.roaringcatgames.kitten2d.ashley.systems.RotationSystem;
import com.roaringcatgames.kitten2d.ashley.systems.TweenSystem;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * Initial Demo Screen
 */
public class HomeScreen extends BaseDemoScreen{

    float tweenTime = 0.5f;
    private TextureRegion asteroidTexture;
    public HomeScreen(IGameProcessor game){
        super(game);
    }

    @Override
    protected void childInit() {
        asteroidTexture = new TextureRegion(new Texture(Gdx.files.internal("assets/gray-asteroid.png")));
        engine.addSystem(new RotationSystem());
        engine.addSystem(new TweenSystem());

        Entity cat = engine.createEntity();
        cat.add(TransformComponent.create(engine)
                .setPosition(renderer.getCamera().position.x, renderer.getCamera().position.y)
                .setRotation(15f)
                .setTint(Color.WHITE));
        cat.add(TextureComponent.create(engine)
                .setRegion(catTexture));
        cat.add(RotationComponent.create(engine)
                .setRotationSpeed(50f));


        Timeline tl = Timeline.createSequence()
                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, tweenTime)
                            .target(Color.RED.r, Color.RED.g, Color.RED.b)
                            .ease(TweenEquations.easeInOutSine))
                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, tweenTime)
                            .target(Color.PINK.r, Color.PINK.g, Color.PINK.b)
                            .ease(TweenEquations.easeInOutSine))
                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, tweenTime)
                            .target(Color.CYAN.r, Color.CYAN.g, Color.CYAN.b)
                            .ease(TweenEquations.easeInOutSine))
                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, tweenTime)
                            .target(Color.GREEN.r, Color.GREEN.g, Color.GREEN.b)
                            .ease(TweenEquations.easeInOutSine))
                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, tweenTime)
                            .target(Color.ORANGE.r, Color.ORANGE.g, Color.ORANGE.b)
                            .ease(TweenEquations.easeInOutSine))
                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, tweenTime)
                            .target(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b)
                            .ease(TweenEquations.easeInOutSine))
                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, tweenTime)
                            .target(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b)
                            .ease(TweenEquations.easeInOutSine))
                    .repeat(Tween.INFINITY, 0f);
        cat.add(TweenComponent.create(engine).setTimeline(tl));
        engine.addEntity(cat);

        addAsteroid(5f, 15f, new Color(0x7f509FFF), new Color[] {Color.RED, Color.PURPLE, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.CYAN });
        addAsteroid(15f, 15f, new Color(0x58B9DFFF), new Color[] {Color.BLUE, Color.ORANGE, Color.RED, Color.PURPLE, Color.CYAN, Color.YELLOW });
        addAsteroid(5f, 5f, new Color(0xE5AEA3FF), new Color[] {Color.GREEN, Color.BLUE, Color.CYAN, Color.YELLOW, Color.PURPLE, Color.ORANGE});
        addAsteroid(15f, 5f, Color.WHITE, new Color[]{});

    }


    private void addAsteroid(float x, float y, Color color, Color[] raveColors){
        Entity asteroid = engine.createEntity();
        asteroid.add(TransformComponent.create(engine)
                .setPosition(x, y, 0f)
                .setTint(color)
                .setScale(0.5f, 0.5f));
        Timeline tl = Timeline.createSequence();
        for(Color c:raveColors){
            tl.push(Tween.to(asteroid, K2EntityTweenAccessor.COLOR, tweenTime)
                .target(c.r, c.g, c.b));
            tl.push(Tween.to(asteroid, K2EntityTweenAccessor.COLOR, tweenTime)
                .target(color.r, color.g, color.b));
        }
        tl.repeat(Tween.INFINITY, 1f);

        asteroid.add(TweenComponent.create(engine)
            .setTimeline(tl));
        asteroid.add(TextureComponent.create(engine)
                .setRegion(asteroidTexture));
        engine.addEntity(asteroid);
    }
}
