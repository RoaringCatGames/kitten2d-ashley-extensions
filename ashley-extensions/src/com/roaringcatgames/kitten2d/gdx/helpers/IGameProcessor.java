package com.roaringcatgames.kitten2d.gdx.helpers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * Provides an interface to access common globally available
 * items like {@link SpriteBatch} and {@link com.badlogic.gdx.graphics.Camera}
 * as well as a way to interact with your screen switching logic and handling
 * input processor wiring.
 */
public interface IGameProcessor {

    SpriteBatch getBatch();
    void switchScreens(String screenName);
    void addInputProcessor(InputProcessor processor);
    void removeInputProcessor(InputProcessor processor);

    OrthographicCamera getGUICam();
}