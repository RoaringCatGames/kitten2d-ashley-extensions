package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.RemainInBoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Created by barry on 4/16/16 @ 3:39 PM.
 */
public class RemainInBoundsSystem extends IteratingSystem {
    private ComponentMapper<RemainInBoundsComponent> ribm;
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<BoundsComponent> bm;

    private Rectangle bounds;
    private Array<Entity> queue;

    public RemainInBoundsSystem(Vector2 minBounds, Vector2 maxBounds){
        super(Family.all(RemainInBoundsComponent.class, TransformComponent.class).get());

        this.ribm = ComponentMapper.getFor(RemainInBoundsComponent.class);
        this.tm = ComponentMapper.getFor(TransformComponent.class);
        this.bm = ComponentMapper.getFor(BoundsComponent.class);

        this.bounds = new Rectangle(minBounds.x, minBounds.y, (maxBounds.x-minBounds.x), (maxBounds.y-minBounds.y));
        this.queue = new Array<>();

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for(Entity e:queue){
            RemainInBoundsComponent rc = ribm.get(e);
            TransformComponent tc = tm.get(e);
            BoundsComponent bc = bm.get(e);

            float left = bounds.x;
            float bottom = bounds.y;
            float right = bounds.x + bounds.width;
            float top = bounds.y + bounds.height;

            float xAdjust = 0f, yAdjust = 0f;
            switch(rc.mode){
                case CONTAINED:
                    if(!bm.has(e)){
                        Gdx.app.error("RemainInBoundsSystem", "CONTAINED entity does not have Bounds cannot calculate");
                        break;
                    }
                    if(bc.bounds.x < left){
                        xAdjust = left - bc.bounds.x;
                    }else if(bc.bounds.x + bc.bounds.width > right){
                        xAdjust = -((bc.bounds.x+bc.bounds.width) - right);
                    }

                    if(bc.bounds.y < bottom){
                        yAdjust = bottom - bc.bounds.y;
                    }else if(bc.bounds.y + bc.bounds.height > top){
                        yAdjust = -((bc.bounds.y + bc.bounds.height) - top);
                    }
                    break;

                case CENTER:

                    if(tc.position.x < left){
                        xAdjust = left - tc.position.x;
                    }else if(tc.position.x > right){
                        xAdjust = -(tc.position.x - right);
                    }

                    if(tc.position.y < bottom){
                        yAdjust = bottom - tc.position.y;
                    }else if(tc.position.y > top){
                        yAdjust = -(tc.position.y - top);
                    }
                    break;

                case EDGE:
                    if(!bm.has(e)){
                        Gdx.app.error("RemainInBoundsSystem", "EDGE entity does not have Bounds cannot calculate");
                        break;
                    }

                    if(bc.bounds.x + bc.bounds.width < left){
                        xAdjust = left - (bc.bounds.x + bc.bounds.width);
                    }else if(bc.bounds.x > right){
                        xAdjust = -(bc.bounds.x - right);
                    }

                    if(bc.bounds.y + bc.bounds.height < bottom){
                        yAdjust = bottom - (bc.bounds.y + bc.bounds.height);
                    }else if(bc.bounds.y > top){
                        yAdjust = -(bc.bounds.y - top);
                    }
                    break;

                default:
                    break;
            }

            tc.position.set(
                    tc.position.x + xAdjust,
                    tc.position.y + yAdjust,
                    tc.position.z);
        }
        queue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        queue.add(entity);
    }
}
