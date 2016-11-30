package com.roaringcatgames.kitten2d.ashley.example.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;
import com.roaringcatgames.kitten2d.ashley.example.DemoGame;
import com.roaringcatgames.kitten2d.ashley.systems.DebugSystem;
import com.roaringcatgames.kitten2d.ashley.systems.RenderingSystem;
import com.roaringcatgames.kitten2d.ashley.systems.TextRenderingSystem;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;
import com.roaringcatgames.kitten2d.gdx.screens.LazyInitScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by barry on 5/3/16 @ 7:43 PM.
 */
public abstract class BaseDemoScreen extends LazyInitScreen implements InputProcessor {
    private static final float MAX_DELTA = 0.16f;

    abstract void childInit();

    protected IGameProcessor game;
    protected PooledEngine engine;
    protected RenderingSystem renderer;

    protected TextureRegion catTexture;

    public BaseDemoScreen(IGameProcessor game){
        this.game = game;
    }

    private void baseInit(){
        Texture kitty = new Texture(Gdx.files.internal("assets/rawry.png"));
        kitty.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        catTexture = new TextureRegion(kitty);

        engine = new PooledEngine();

        renderer = new RenderingSystem(game.getBatch(), game.getCamera(), DemoGame.PPM);

    }
    @Override
    protected void init() {
        baseInit();
        childInit();
        engine.addSystem(renderer);
        engine.addSystem(new TextRenderingSystem(game.getBatch(), game.getGUICamera(), game.getCamera()));
        engine.addSystem(new DebugSystem(game.getCamera(), Color.BLUE, Color.MAGENTA, Input.Keys.TAB));
    }

    @Override
    public void show() {
        super.show();
        this.game.addInputProcessor(this);
    }

    @Override
    public void hide() {
        super.hide();
        this.game.removeInputProcessor(this);
    }

    @Override
    protected void update(float deltaChange) {
        float clippedDelta = Math.min(deltaChange, MAX_DELTA);
        engine.update(clippedDelta);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.NUM_1){
            this.game.switchScreens("HOME");
        }

        if(keycode == Input.Keys.NUM_2){
            this.game.switchScreens("LEVEL_1");
        }

        if(keycode == Input.Keys.NUM_3){
            this.game.switchScreens("LEVEL_2");
        }

        if(keycode == Input.Keys.NUM_4){
            this.game.switchScreens("LEVEL_3");
        }

        if(keycode == Input.Keys.SPACE){
            Gdx.app.log("BaseDemoScreen Prefs Test", "KeyUpStr: " + this.game.getPreferenceManager().getStoredString("KeyUpStr"));
            Gdx.app.log("BaseDemoScreen Prefs Test", "KeyUpInt: " + this.game.getPreferenceManager().getStoredString("KeyUpInt"));
            Gdx.app.log("BaseDemoScreen Prefs Test", "KeyUpFlt: " + this.game.getPreferenceManager().getStoredString("KeyUpFlt"));
        }

        if(keycode == Input.Keys.DEL){
            this.game.getPreferenceManager().deleteValue("KeyUpStr");
        }

        if(keycode == Input.Keys.ESCAPE){
            this.game.getPreferenceManager().clear();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        Map<String, Object> updateVals = new HashMap<>();
        updateVals.put("KeyUpStr", Input.Keys.toString(keycode));
        updateVals.put("KeyUpFlt", (float) keycode);
        updateVals.put("KeyUpInt", keycode);
        this.game.getPreferenceManager().updateValues(updateVals);

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
