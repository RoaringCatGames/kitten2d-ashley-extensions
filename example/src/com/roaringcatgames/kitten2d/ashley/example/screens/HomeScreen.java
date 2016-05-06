package com.roaringcatgames.kitten2d.ashley.example.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.roaringcatgames.kitten2d.ashley.components.RotationComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.systems.RotationSystem;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;
//import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * Created by barry on 5/3/16 @ 6:52 PM.
 */
public class HomeScreen extends BaseDemoScreen{

    public HomeScreen(IGameProcessor game){
        super(game);
    }

    @Override
    protected void childInit() {
        engine.addSystem(new RotationSystem());

        Entity cat = engine.createEntity();
        cat.add(TransformComponent.create(engine)
            .setPosition(renderer.getCamera().position.x, renderer.getCamera().position.y)
            .setRotation(15f));
        cat.add(TextureComponent.create(engine)
            .setRegion(catTexture));
        cat.add(RotationComponent.create(engine)
            .setRotationSpeed(50f));
        engine.addEntity(cat);
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
