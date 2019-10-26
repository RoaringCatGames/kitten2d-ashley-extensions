package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.utils.Pool;

/**
 * A component to define the event which will fire when the component is clicked
 */
public class ClickableComponent implements Component, Pool.Poolable{

    public static final int ALL_POINTERS = 4;
    public static final String UNSET_EVENT_NAME = "UNSET_EVENT";

    public String eventName = UNSET_EVENT_NAME;
    public boolean hasBeenClicked = false;
    public int clickCount = 0;
    /**
     * 1 - Left Click
     * 2 - Right Click
     * 3 - Middle Click
     * 4 - Any Click
     */
    public int pointer = ALL_POINTERS;

    public static ClickableComponent create(Engine engine){
        return engine.createComponent(ClickableComponent.class);
    }

    public ClickableComponent setEventName(String eName){
        this.eventName = eName;
        return this;
    }

    public ClickableComponent setClicked(boolean clicked){
        this.hasBeenClicked = clicked;
        return this;
    }

    public ClickableComponent setClickCount(int count){
        this.clickCount = count;
        return this;
    }

    public ClickableComponent addClickCount(int count){
        this.clickCount += count;
        return this;
    }

    public ClickableComponent setPointer(int pointerTarget){
        this.pointer = pointerTarget;
        return this;
    }

    @Override
    public void reset() {
        this.eventName = UNSET_EVENT_NAME;
        this.hasBeenClicked = false;
        this.clickCount = 0;
        this.pointer = ALL_POINTERS;
    }
}
