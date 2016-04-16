package com.roaringcatgames.kitten2d.ashley.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.kitten2d.ashley.components.FadingComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Created by barry on 3/3/16 @ 10:11 PM.
 */
public class FadingSystem extends IteratingSystem {

    private ComponentMapper<FadingComponent> fm;
    private ComponentMapper<TransformComponent> tm;

    public FadingSystem(){
        super(Family.all(FadingComponent.class, TransformComponent.class).get());
        this.fm = ComponentMapper.getFor(FadingComponent.class);
        this.tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent tc = tm.get(entity);
        if(tc.opacity > 0f){
            FadingComponent fc = fm.get(entity);
            float fadeAmount = (fc.percentPerSecond/100f)*deltaTime;
            tc.setOpacity(Math.max(0f, (tc.opacity-fadeAmount)));
        }
    }
}