package com.roaringcatgames.kitten2d.ashley;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.roaringcatgames.kitten2d.ashley.components.*;

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
    public static final int POSITION_X = 2;
    public static final int POSITION_Y = 3;
    public static final int POSITION_Z = 4;
    public static final int POSITION_XY = 5;
    public static final int SCALE = 6;
    public static final int ROTATION = 7;
    public static final int OPACITY = 8;
    public static final int VELOCITY = 9;
    public static final int COLOR = 10;
    public static final int PATH_FOLLOW_SPEED = 11;
    public static final int BOUNDS_RADIUS = 12;
    public static final int BOUNDS_XY = 13;

    @Override
    public int getValues(Entity entity, int tweenType, float[] returnValues) {
        int result = 0;
        switch(tweenType){
            case POSITION:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    returnValues[0] = tc.position.x;
                    returnValues[1] = tc.position.y;
                    returnValues[2] = tc.position.z;
                    result = 3;
                }
                break;
            case POSITION_X:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    returnValues[0] = tc.position.x;
                    result = 1;
                }
                break;
            case POSITION_Y:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    returnValues[0] = tc.position.y;
                    result = 1;
                }
                break;
            case POSITION_Z:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    returnValues[0] = tc.position.z;
                    Gdx.app.log("TweenAccessor", "Z: " + returnValues[0]);
                    result = 1;
                }
                break;
            case POSITION_XY:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    returnValues[0] = tc.position.x;
                    returnValues[1] = tc.position.y;
                    result = 2;
                }
                break;
            case SCALE:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    returnValues[0] = tc.scale.x;
                    returnValues[1] = tc.scale.y;
                    result = 2;
                }
                break;
            case ROTATION:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    returnValues[0] = tc.rotation;
                    result = 1;
                }
                break;
            case OPACITY:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    returnValues[0] = tc.tint.a;
                    result = 1;
                }
                break;
            case VELOCITY:
                if(K2ComponentMappers.velocity.has(entity)){
                    VelocityComponent vc = K2ComponentMappers.velocity.get(entity);
                    returnValues[0] = vc.speed.x;
                    returnValues[1] = vc.speed.y;
                    result = 2;
                }
                break;
            case COLOR:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tm = K2ComponentMappers.transform.get(entity);
                    returnValues[0] = tm.tint.r;
                    returnValues[1] = tm.tint.g;
                    returnValues[2] = tm.tint.b;
                    result = 3;
                }
                break;
            case PATH_FOLLOW_SPEED:
                if(K2ComponentMappers.pathFollow.has(entity)){
                    PathFollowComponent pfc = K2ComponentMappers.pathFollow.get(entity);
                    returnValues[0] = pfc.speed;
                    result = 1;
                }
                break;
            case BOUNDS_RADIUS:
                if(K2ComponentMappers.circleBounds.has(entity)){
                    CircleBoundsComponent cbc = K2ComponentMappers.circleBounds.get(entity);
                    returnValues[0] = cbc.circle.radius;
                    result = 1;
                }
                break;
            case BOUNDS_XY:
                if(K2ComponentMappers.bounds.has(entity)){
                    BoundsComponent bc = K2ComponentMappers.bounds.get(entity);
                    returnValues[0] = bc.bounds.width;
                    returnValues[1] = bc.bounds.height;
                    result = 2;
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
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    tc.position.set(newValues[0], newValues[1], newValues[2]);
                }
                break;
            case POSITION_X:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    tc.position.set(newValues[0], tc.position.y, tc.position.z);
                }
                break;
            case POSITION_Y:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    tc.position.set(tc.position.x, newValues[0], tc.position.z);
                }
                break;
            case POSITION_Z:
                if(K2ComponentMappers.transform.has(entity)){
                    Gdx.app.log("TweenAccessor", "New Z: " + newValues[0]);
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    tc.position.set(tc.position.x, tc.position.y, newValues[0]);
                }
                break;
            case POSITION_XY:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    tc.position.set(newValues[0], newValues[1], tc.position.z);
                }
                break;
            case SCALE:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    tc.scale.set(newValues[0], newValues[1]);
                }
                break;
            case ROTATION:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    tc.rotation = newValues[0];
                }
                break;
            case OPACITY:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tc = K2ComponentMappers.transform.get(entity);
                    tc.tint.a = newValues[0];
                }
                break;
            case VELOCITY:
                if(K2ComponentMappers.velocity.has(entity)){
                    VelocityComponent vc = K2ComponentMappers.velocity.get(entity);
                    vc.speed.set(newValues[0], newValues[1]);
                }
                break;

            case COLOR:
                if(K2ComponentMappers.transform.has(entity)){
                    TransformComponent tm = K2ComponentMappers.transform.get(entity);
                    tm.tint.set(newValues[0],
                            newValues[1],
                            newValues[2],
                            tm.tint.a);
                }
                break;
            case PATH_FOLLOW_SPEED:
                if(K2ComponentMappers.pathFollow.has(entity)){
                    PathFollowComponent pfc = K2ComponentMappers.pathFollow.get(entity);
                    pfc.setSpeed(newValues[0]);
                }
                break;
            case BOUNDS_RADIUS:
                if(K2ComponentMappers.circleBounds.has(entity)){
                    CircleBoundsComponent cbc = K2ComponentMappers.circleBounds.get(entity);
                    cbc.circle.setRadius(newValues[0]);
                }
                break;
            case BOUNDS_XY:
                if(K2ComponentMappers.bounds.has(entity)){
                    BoundsComponent bc = K2ComponentMappers.bounds.get(entity);
                    bc.bounds.setWidth(newValues[0]);
                    bc.bounds.setHeight(newValues[1]);
                }
                break;
            default:
                break;
        }
    }
}
