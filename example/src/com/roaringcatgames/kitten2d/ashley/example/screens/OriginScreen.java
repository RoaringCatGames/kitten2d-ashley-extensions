package com.roaringcatgames.kitten2d.ashley.example.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.kitten2d.ashley.systems.*;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * Created by barry on 5/9/16 @ 9:13 PM.
 */
public class OriginScreen extends BaseDemoScreen {

    public OriginScreen(IGameProcessor game){
        super(game);
    }

    @Override
    void childInit() {
        engine.addSystem(new RotationSystem());
        engine.addSystem(new TweenSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new MultiBoundsSystem());


        Entity cat = engine.createEntity();
        cat.add(TransformComponent.create(engine)
                .setPosition(renderer.getCamera().position.x, renderer.getCamera().position.y)
                .setRotation(15f)
                .setOriginOffset(-2f, -2f)
                .setTint(Color.WHITE));
        cat.add(BoundsComponent.create(engine)
            .setBounds(0f, 0f, 5f, 5f));
        cat.add(MultiBoundsComponent.create(engine)
            .addBound(new Bound(new Circle(0f, 0f, 2f), -4f, 0f))
            .addBound(new Bound(new Circle(0f, 0f, 2f), 0f, 0f))
            .addBound(new Bound(new Circle(0f, 0f, 2f), 4f, 0f))
            .addBound(new Bound(new Rectangle(0f, 0f, 1f, 1f), 0f, -4f))
            .addBound(new Bound(new Rectangle(0f, 0f, 1f, 1f), 0f, 4f)));
        cat.add(TextureComponent.create(engine)
                .setRegion(catTexture));
        cat.add(RotationComponent.create(engine)
                .setRotationSpeed(50f));
        engine.addEntity(cat);
    }
}
