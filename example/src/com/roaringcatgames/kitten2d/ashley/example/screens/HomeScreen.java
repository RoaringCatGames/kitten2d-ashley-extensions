package com.roaringcatgames.kitten2d.ashley.example.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roaringcatgames.kitten2d.ashley.IActionResolver;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.kitten2d.ashley.systems.*;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * Initial Demo Screen
 */
public class HomeScreen extends BaseDemoScreen{

    float tweenTime = 0.5f;
    private TextureRegion asteroidTexture;
    private Entity cat;
    public HomeScreen(IGameProcessor game){
        super(game);
    }

    @Override
    protected void childInit() {
        asteroidTexture = new TextureRegion(new Texture(Gdx.files.internal("assets/gray-asteroid.png")));
        engine.addSystem(new RotationSystem());
        engine.addSystem(new TweenSystem());
        engine.addSystem(new ParticleSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new FadingSystem());
        engine.addSystem(new ClickableSystem(game, new IActionResolver() {
            @Override
            public void resolveAction(String eventName, Entity firingEntity, Engine containerEngine) {
                switch(eventName){
                    case ClickableComponent.UNSET_EVENT_NAME:
                         //Do nothing;
                        break;
                    case "ASTEROID":
                        TransformComponent tc = K2ComponentMappers.transform.get(firingEntity);
                        ClickableComponent cc = K2ComponentMappers.clickable.get(firingEntity);
                        Gdx.app.log("HOME SCREEN", "ASTEROID EVENT FIRED! Count: " + cc.clickCount + " Current Color: " + tc.tint.toString());
                        break;
                }
            }
        }));

        cat = engine.createEntity();
        cat.add(TransformComponent.create(engine)
                .setPosition(game.getCamera().position.x, game.getCamera().position.y)
                .setRotation(15f)
                .setTint(Color.WHITE));
        cat.add(TextureComponent.create(engine)
                .setRegion(catTexture));
        cat.add(RotationComponent.create(engine)
                .setRotationSpeed(50f));
        cat.add(ParticleEmitterComponent.create(engine)
                .setParticleImage(catTexture)
                .setParticleMinMaxScale(0.02f, 0.3f)
                .setSpawnType(ParticleSpawnType.RANDOM_IN_BOUNDS)
                .setSpawnRate(50f)
                .setZIndex(0f)
                .setParticleLifespans(0.5f, 1f)
                .setShouldFade(true)
                .setShouldLoop(true)
                .setAngleRange(-90f, 90f)
                .setSpawnRange(1f, 1f)
                .setSpeed(5f, 10f));


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

        addAsteroid(5f, 15f, new Color(0x7f509FFF), new Color[]{Color.RED, Color.PURPLE, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.CYAN});
        addAsteroid(15f, 15f, new Color(0x58B9DFFF), new Color[]{Color.BLUE, Color.ORANGE, Color.RED, Color.PURPLE, Color.CYAN, Color.YELLOW});
        addAsteroid(5f, 5f, new Color(0xE5AEA3FF), new Color[]{Color.GREEN, Color.BLUE, Color.CYAN, Color.YELLOW, Color.PURPLE, Color.ORANGE});
        addAsteroid(15f, 5f, Color.WHITE, new Color[]{});

        BitmapFont font = new BitmapFont(Gdx.files.internal("assets/fonts/arial-32.fnt"));
        font.setColor(Color.PURPLE);

        Entity text = engine.createEntity();
        text.add(TransformComponent.create(engine)
            .setPosition(10f, 10f)
            .setTint(Color.RED.r, Color.RED.g, Color.RED.b, Color.RED.a));
        text.add(TextComponent.create(engine)
                .setFont(font)
                .setText("WOOOO"));
        engine.addEntity(text);

        Entity text2 = engine.createEntity();
        text2.add(TransformComponent.create(engine)
                .setPosition(10f, 9f)
                .setTint(Color.PINK.r, Color.PINK.g, Color.PINK.b, Color.PINK.a));
        text2.add(TextComponent.create(engine)
                .setFont(font)
                .setText("WOOOO"));
        engine.addEntity(text2);

    }


    private void addAsteroid(float x, float y, Color color, Color[] raveColors){
        Entity asteroid = engine.createEntity();
        asteroid.add(TransformComponent.create(engine)
                .setPosition(x, y, 0f)
                .setTint(color)
                .setScale(0.5f, 0.5f));
        asteroid.add(ClickableComponent.create(engine)
            .setEventName("ASTEROID"));
        asteroid.add(CircleBoundsComponent.create(engine)
            .setCircle(x, y, 2f));
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

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.SPACE){
            ParticleEmitterComponent pec = K2ComponentMappers.particleEmitter.get(cat);
            pec.setPaused(!pec.isPaused);
        }
        return super.keyDown(keycode);
    }
}
