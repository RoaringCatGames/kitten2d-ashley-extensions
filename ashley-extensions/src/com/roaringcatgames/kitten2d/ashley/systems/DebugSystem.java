package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.VectorUtils;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Created by barry on 12/13/15 @ 2:22 PM.
 */
public class DebugSystem extends IteratingSystem {

    private int[] debugKeys;
    private OrthographicCamera cam;
    private Color boundsColor;
    private Color originColor;
    private ShapeRenderer shapeRenderer;
    private Array<Entity> processQueue;
    ComponentMapper<BoundsComponent> bm;
    ComponentMapper<TransformComponent> tm;

    private boolean isDebugMode = false;


    public DebugSystem(OrthographicCamera camera, Color boundsColor, Color originColor, int...debugKeys){
        super(Family.all(BoundsComponent.class).get());
        init(camera, boundsColor, originColor, debugKeys);
    }

    public DebugSystem(OrthographicCamera camera, int...debugKeys){
        super(Family.all(BoundsComponent.class).get());
        init(camera, Color.YELLOW, Color.RED, debugKeys);
    }

    private void init(OrthographicCamera camera, Color boundsColor, Color originColor, int...debugKeys ){
        if(debugKeys != null && debugKeys.length > 0){
            this.debugKeys = debugKeys;
        }else{
            this.debugKeys = new int [] { Input.Keys.TAB };
        }
        this.cam = camera;
        this.boundsColor = boundsColor;
        this.originColor = originColor;

        shapeRenderer = new ShapeRenderer();
        processQueue = new Array<>();

        bm = ComponentMapper.getFor(BoundsComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for(int key:debugKeys) {
            if (Gdx.input.isKeyJustPressed(key)) {
                isDebugMode = !isDebugMode;
                break;
            }
        }

        if(isDebugMode) {
            Gdx.gl20.glLineWidth(1f);
            shapeRenderer.setProjectionMatrix(cam.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(boundsColor);
            for (Entity e : processQueue) {
                BoundsComponent bounds = bm.get(e);
                shapeRenderer.rect(bounds.bounds.x, bounds.bounds.y, bounds.bounds.width, bounds.bounds.height);
                shapeRenderer.setColor(originColor);
                float boundsCenterX = bounds.bounds.x + (bounds.bounds.width / 2f);
                float boundsCenterY = bounds.bounds.y + (bounds.bounds.height / 2f);
                shapeRenderer.circle(boundsCenterX, boundsCenterY, 0.1f);
                if(bounds.offset.x != 0f || bounds.offset.y != 0f) {
                    Vector2 offset = bounds.offset;
                    TransformComponent tc = tm.get(e);
                    if(tc != null) {
                        offset = VectorUtils.rotateVector(offset, tc.rotation);
                    }
                    shapeRenderer.line(boundsCenterX, boundsCenterY, boundsCenterX - offset.x, boundsCenterY - offset.y);
                }
            }
            shapeRenderer.end();
        }

        processQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        processQueue.add(entity);
    }
}
