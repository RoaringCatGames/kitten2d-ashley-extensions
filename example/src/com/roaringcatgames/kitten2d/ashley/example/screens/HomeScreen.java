package com.roaringcatgames.kitten2d.ashley.example.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

import java.sql.Time;
//import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * Created by barry on 5/3/16 @ 6:52 PM.
 */
public class HomeScreen extends BaseDemoScreen{

    private TextureRegion asteroidTexture;
    public HomeScreen(IGameProcessor game){
        super(game);
    }

    @Override
    protected void childInit() {
        asteroidTexture = new TextureRegion(new Texture(Gdx.files.internal("assets/asteroid-b.png")));
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
                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, 4f)
                            .target(Color.GREEN.r, Color.GREEN.g, Color.GREEN.b))
                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, 4f)
                            .target(Color.YELLOW.r, Color.YELLOW.g, Color.YELLOW.b))
                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, 2f)
                            .target(Color.BLUE.r, Color.BLUE.g, Color.BLUE.b)
                            .ease(TweenEquations.easeInOutSine))

                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, 4f)
                                    .target(Color.PINK.r, Color.PINK.g, Color.PINK.b)
                    )
                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, 4f)
                                    .target(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b)
                    )
                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, 4f)
                                    .target(Color.ORANGE.r, Color.ORANGE.g, Color.ORANGE.b)
                    )
                    .push(Tween.to(cat, K2EntityTweenAccessor.COLOR, 4f)
                                    .target(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b)
                    )
                    .repeat(Tween.INFINITY, 0f);
        cat.add(TweenComponent.create(engine).setTimeline(tl));
        engine.addEntity(cat);

        Entity asteroid = engine.createEntity();
        asteroid.add(TransformComponent.create(engine)
            .setPosition(10f, 10f, 0f)
            .setTint(Color.ORANGE));
        asteroid.add(TextureComponent.create(engine)
            .setRegion(asteroidTexture));
        engine.addEntity(asteroid);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.A){
            this.game.switchScreens("LEVEL_1");
        }
        //TURN ON SYSTEMS


        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
