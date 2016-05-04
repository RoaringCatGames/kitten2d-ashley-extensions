package com.roaringcatgames.kitten2d.ashley.example.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.Entity;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.TweenComponent;
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


        Entity cat = engine.createEntity();
        Tween tweenPos = Tween.to(cat, K2EntityTweenAccessor.POSITION, 3)
                            .ease(TweenEquations.easeInOutSine)
                            .target(1f, 2f, 0f)
                            .repeatYoyo(Tween.INFINITY, 0f);
        Tween tweenScale = Tween.to(cat, K2EntityTweenAccessor.SCALE, 2)
                            .ease(TweenEquations.easeInOutElastic)
                            .target(0.5f, 0.5f)
                            .repeatYoyo(Tween.INFINITY, 1);
        cat.add(TransformComponent.create(engine)
                .setPosition(renderer.getCamera().position.x, renderer.getCamera().position.y)
                .setRotation(15f));
        cat.add(TextureComponent.create(engine)
                .setRegion(catTexture));
        cat.add(TweenComponent.create(engine)
            .addTween(tweenPos)
            .addTween(tweenScale));
        engine.addEntity(cat);

    }
}
