package com.roaringcatgames.kitten2d.ashley.example.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.kitten2d.ashley.systems.BoundsSystem;
import com.roaringcatgames.kitten2d.ashley.systems.DebugSystem;
import com.roaringcatgames.kitten2d.ashley.systems.RotationSystem;
import com.roaringcatgames.kitten2d.ashley.systems.TweenSystem;
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


        Entity cat = engine.createEntity();
        cat.add(TransformComponent.create(engine)
                .setPosition(renderer.getCamera().position.x, renderer.getCamera().position.y)
                .setRotation(15f)
                .setOriginOffset(-3f, -3f)
                .setTint(Color.WHITE));
        cat.add(BoundsComponent.create(engine)
            .setBounds(0f, 0f, 5f, 5f));
        cat.add(TextureComponent.create(engine)
                .setRegion(catTexture));
        cat.add(RotationComponent.create(engine)
                .setRotationSpeed(50f));
        engine.addEntity(cat);
    }
}
