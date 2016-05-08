package com.roaringcatgames.kitten2d.ashley;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.ashley.core.Entity;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;

/**
 * Default TweenAccessor Implementation for Kitten2d Entities.
 * Provides Tween implementations for:
 *  Position,
 *  Scale,
 *  Rotation,
 *  Opacity,
 *  Velocity,
 *  Color
 */
public class K2EntityTweenAccessor implements TweenAccessor<Entity> {
    public static final int POSITION = 1;
    public static final int SCALE = 2;
    public static final int ROTATION = 3;
    public static final int OPACITY = 4;
    public static final int VELOCITY = 5;
    public static final int COLOR = 6;

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
                break;
            case ROTATION:
                if(K2ComponentMappers.tm.has(entity)){
                    TransformComponent tc = K2ComponentMappers.tm.get(entity);
                    returnValues[0] = tc.rotation;
                    result = 1;
                }
                break;
            case OPACITY:
                if(K2ComponentMappers.tm.has(entity)){
                    TransformComponent tc = K2ComponentMappers.tm.get(entity);
                    returnValues[0] = tc.tint.a;
                    result = 1;
                }
                break;
            case VELOCITY:
                if(K2ComponentMappers.vm.has(entity)){
                    VelocityComponent vc = K2ComponentMappers.vm.get(entity);
                    returnValues[0] = vc.speed.x;
                    returnValues[1] = vc.speed.y;
                    result = 2;
                }
                break;
            case COLOR:
                if(K2ComponentMappers.tm.has(entity)){
                    TransformComponent tm = K2ComponentMappers.tm.get(entity);
                    returnValues[0] = tm.tint.r;
                    returnValues[1] = tm.tint.g;
                    returnValues[2] = tm.tint.b;
                    result = 3;
                }
                break;
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
                break;
            case ROTATION:
                if(K2ComponentMappers.tm.has(entity)){
                    TransformComponent tc = K2ComponentMappers.tm.get(entity);
                    tc.rotation = newValues[0];
                }
                break;
            case OPACITY:
                if(K2ComponentMappers.tm.has(entity)){
                    TransformComponent tc = K2ComponentMappers.tm.get(entity);
                    tc.tint.a = newValues[0];
                }
                break;
            case VELOCITY:
                if(K2ComponentMappers.vm.has(entity)){
                    VelocityComponent vc = K2ComponentMappers.vm.get(entity);
                    vc.speed.set(newValues[0], newValues[1]);
                }
                break;

            case COLOR:
                if(K2ComponentMappers.tm.has(entity)){
                    TransformComponent tm = K2ComponentMappers.tm.get(entity);
                    tm.tint.set(newValues[0],
                            newValues[1],
                            newValues[2],
                            tm.tint.a);
                }
                break;
            default:
                break;
        }
    }
}
