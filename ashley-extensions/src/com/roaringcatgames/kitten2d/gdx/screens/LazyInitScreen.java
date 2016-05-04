package com.roaringcatgames.kitten2d.gdx.screens;

/**
 * Created by barry on 5/3/16 @ 6:55 PM.
 */
import com.badlogic.gdx.ScreenAdapter;

/**
 * Lazy initialized Screens will wait until they are shown
 * before calling their init(). If not initialized, init() is
 * immediately called when {@link this.show} is called.
 */
public abstract class LazyInitScreen extends ScreenAdapter {

    protected boolean isInitialized = false;

    /**
     * Do your initialization here.
     */
    protected abstract void init();

    /**
     * Perform any updates here which will fire each {@link this.render}
     * call.
     * @param deltaChange the time in seconds since the last update call
     */
    protected abstract void update(float deltaChange);

    @Override
    public void show() {
        super.show();
        if(!isInitialized) {
            init();
            isInitialized = true;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        super.render(delta);
    }
}