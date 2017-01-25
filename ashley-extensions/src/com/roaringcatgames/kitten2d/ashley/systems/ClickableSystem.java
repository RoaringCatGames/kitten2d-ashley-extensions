package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.IActionResolver;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.CircleBoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.ClickableComponent;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * System to handle Click Events and Dispatch them to the EventResolver
 */
public class ClickableSystem extends IteratingSystem implements InputProcessor {

    private IGameProcessor game;
    private IActionResolver eventResolver;
    private Array<Entity> clickables;
    private Vector2 touchPoint = new Vector2();

    public ClickableSystem(IGameProcessor game, IActionResolver eventResolver){
        super(Family.all(ClickableComponent.class).one(BoundsComponent.class, CircleBoundsComponent.class).get());
        this.game = game;
        this.eventResolver = eventResolver;
        this.clickables = new Array<Entity>();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.game.addInputProcessor(this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        this.game.removeInputProcessor(this);
    }

    @Override
    public void update(float deltaTime) {
        clickables.clear();
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ClickableComponent cc = K2ComponentMappers.clickable.get(entity);
        if(cc.hasBeenClicked){
            cc.hasBeenClicked = false;
            this.eventResolver.resolveAction(cc.eventName, entity, getEngine());
        }else{
            clickables.add(entity);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
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
        touchPoint.set(screenX, screenY);
        this.game.getViewport().unproject(touchPoint);
        boolean touchFound = false;

        for(Entity e:clickables){
            if(K2ComponentMappers.bounds.has(e)){
                BoundsComponent bc = K2ComponentMappers.bounds.get(e);
                ClickableComponent cc = K2ComponentMappers.clickable.get(e);
                if((cc.pointer == ClickableComponent.ALL_POINTERS || cc.pointer == pointer || cc.pointer == button) &&
                    bc.bounds.contains(touchPoint)){

                    cc.hasBeenClicked = true;
                    cc.addClickCount(1);
                    touchFound = true;
                    break;
                }
            }else if(K2ComponentMappers.circleBounds.has(e)){
                CircleBoundsComponent cbc = K2ComponentMappers.circleBounds.get(e);
                ClickableComponent cc = K2ComponentMappers.clickable.get(e);
                if((cc.pointer == ClickableComponent.ALL_POINTERS || cc.pointer == pointer || cc.pointer == button) &&
                        cbc.circle.contains(touchPoint)){

                    cc.hasBeenClicked = true;
                    cc.addClickCount(1);
                    touchFound = true;
                    break;
                }
            }
        }

        return touchFound;
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
