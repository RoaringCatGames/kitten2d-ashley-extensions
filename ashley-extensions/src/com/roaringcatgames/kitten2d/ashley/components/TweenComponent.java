package com.roaringcatgames.kitten2d.ashley.components;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 5/3/16 @ 7:48 PM.
 */
public class TweenComponent implements Component, Pool.Poolable {
    public Array<Tween> tweens = new Array<>();
    public Timeline timeline;

    public static TweenComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(TweenComponent.class);
        }else{
            return new TweenComponent();
        }
    }

    public TweenComponent addTween(Tween t){
        this.tweens.add(t);
        return this;
    }

    public TweenComponent setTimeline(Timeline t){
        this.timeline = t;
        return this;
    }

    @Override
    public void reset() {
        for(Tween t:tweens){
            t.kill();
            t.free();
        }
        tweens.clear();
        if(timeline != null){
            timeline.kill();
            timeline.free();
        }
        timeline = null;
    }
}
