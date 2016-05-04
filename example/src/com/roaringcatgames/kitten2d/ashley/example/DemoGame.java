package com.roaringcatgames.kitten2d.ashley.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.roaringcatgames.kitten2d.ashley.example.screens.HomeScreen;
import com.roaringcatgames.kitten2d.ashley.example.screens.TweenScreen;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * Created by barry on 5/3/16 @ 6:33 PM.
 */
public class DemoGame extends Game implements IGameProcessor{

    public static final float PPM = 32f;
    private static final float VIEWPORT_SIZE = 640f/PPM;

    private InputMultiplexer multiplexer = new InputMultiplexer();
    private SpriteBatch batch;
    private OrthographicCamera cam;
    private OrthographicCamera guiCam;

    private HomeScreen home;
    private TweenScreen tween;
    @Override
    public void create () {
        batch = new SpriteBatch();
        //TODO: Camera System
        cam = new OrthographicCamera(VIEWPORT_SIZE, VIEWPORT_SIZE);
        guiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        home = new HomeScreen(this);
        tween = new TweenScreen(this);
        setScreen(home);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render () {
        float r = 29/255f;
        float g = 29/255f;
        float b = 27/255f;
        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }


    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Kitten2d Ashley Extensions Example";
        config.height = 640;
        config.width = 640;
        config.samples = 4;

        new LwjglApplication(new DemoGame(), config);
    }

    @Override
    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void switchScreens(String screenName) {
        if("LEVEL_1".equals(screenName)){
            setScreen(tween);
        }else{
            setScreen(home);
        }
    }

    @Override
    public void addInputProcessor(InputProcessor processor) {
        multiplexer.addProcessor(processor);
    }

    @Override
    public void removeInputProcessor(InputProcessor processor) {
        multiplexer.removeProcessor(processor);
    }

    @Override
    public OrthographicCamera getGUICam() {
        return guiCam;
    }
}
