package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

import java.util.Comparator;

/**
 * This system will handle rendering all of our
 * entities with a Texture and Transform. Uses the
 * Z value of the TransformComponent.position to determine
 * rendering order HIGH to LOW. (ex: 100 renders behind 99)
 */
public class RenderingSystem extends IteratingSystem {

    private final float PPM;

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;
    private OrthographicCamera cam;

    private Color tintPlaceholder = Color.WHITE.cpy();

    public RenderingSystem(SpriteBatch batch, OrthographicCamera cam, float pixelsPerMeter) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get());//, new ZComparator());
        PPM = pixelsPerMeter;


        renderQueue = new Array<>();
        comparator = new ZComparator();
//        comparator = new Comparator<Entity>() {
//            @Override
//            public int compare(Entity entityA, Entity entityB) {
//                return (int) Math.signum(K2ComponentMappers.transform.get(entityB).position.z -
//                        K2ComponentMappers.transform.get(entityA).position.z);
//            }
//        };

        this.batch = batch;


        this.cam = cam;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        renderQueue.sort(comparator);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.enableBlending();
        batch.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = K2ComponentMappers.texture.get(entity);
            TransformComponent t = K2ComponentMappers.transform.get(entity);

            if (tex.region == null || t.isHidden) {
                continue;
            }


            Color c = batch.getColor();
            tintPlaceholder.set(t.tint.r, t.tint.g, t.tint.b, t.tint.a);
            batch.setColor(tintPlaceholder);
            float width = PixelsToMeters(tex.region.getRegionWidth());
            float height = PixelsToMeters(tex.region.getRegionHeight());
            float halfWidth = width/2f;
            float halfHeight = height/2f;
            //Allow for Offset
            float originX = halfWidth + t.originOffset.x;
            float originY = halfHeight + t.originOffset.y;

            batch.draw(tex.region,
                    t.position.x - halfWidth, t.position.y - halfHeight,
                    originX, originY,
                    width, height,
                    t.scale.x, t.scale.y,
                    t.rotation);
            batch.setColor(c);
        }

        batch.end();
        renderQueue.clear();
    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return cam;
    }


    private float PixelsToMeters(float pixelValue){
        return pixelValue * (1.0f/PPM);
    }

    private float MetersToPixels(float meterValue){
        return  PPM * meterValue;
    }
}
