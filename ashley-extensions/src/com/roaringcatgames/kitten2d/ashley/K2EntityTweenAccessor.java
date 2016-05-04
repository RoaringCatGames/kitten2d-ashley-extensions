package com.roaringcatgames.kitten2d.ashley;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Created by barry on 5/3/16 @ 8:12 PM.
 */
public class K2EntityTweenAccessor implements TweenAccessor<Entity> {
    public static final int POSITION = 1;
    public static final int SCALE = 2;

    @Override
    public int getValues(Entity entity, int tweenType, float[] returnValues) {
        int result = 0;
        switch(tweenType){
            case POSITION:
                if(K2ComponentMappers.tm.has(entity)){
                    TransformComponent tc = K2ComponentMappers.tm.get(entity);
                    returnValues[0] = tc.position.x;
                    returnValues[1] = tc.position.y;
                    returnValues[2] = tc.position.z;
                    result = 3;
                }
                break;
            case SCALE:
                if(K2ComponentMappers.tm.has(entity)){
                    TransformComponent tc = K2ComponentMappers.tm.get(entity);
                    returnValues[0] = tc.scale.x;
                    returnValues[1] = tc.scale.y;
                    result = 2;
                }
            default:
                break;
        }
        return result;
    }

    @Override
    public void setValues(Entity entity, int tweenType, float[] newValues) {
        switch(tweenType){
            case POSITION:
                if(K2ComponentMappers.tm.has(entity)){
                    TransformComponent tc = K2ComponentMappers.tm.get(entity);
                    tc.position.set(newValues[0], newValues[1], newValues[2]);
                }
                break;
            case SCALE:
                if(K2ComponentMappers.tm.has(entity)){
                    TransformComponent tc = K2ComponentMappers.tm.get(entity);
                    tc.scale.set(newValues[0], newValues[1]);
                }
            default:
                break;
        }
    }
}
