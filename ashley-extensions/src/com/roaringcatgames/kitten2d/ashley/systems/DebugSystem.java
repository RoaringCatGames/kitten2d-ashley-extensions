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
import com.roaringcatgames.kitten2d.ashley.components.CircleBoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.PathFollowComponent;
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

    private Array<Entity> rectangles;
    private Array<Entity> circles;
    private Array<Entity> paths;

    private int pixelsPerUnit = 1;
    private Vector2 v1, v2, pPos;

    ComponentMapper<BoundsComponent> bm;
    ComponentMapper<CircleBoundsComponent> cm;
    ComponentMapper<TransformComponent> tm;
    ComponentMapper<PathFollowComponent> pm;

    private boolean isDebugMode = false;


    public DebugSystem(OrthographicCamera camera, Color boundsColor, Color originColor, int...debugKeys){
        super(Family.one(CircleBoundsComponent.class, BoundsComponent.class, PathFollowComponent.class).get());
        init(camera, boundsColor, originColor, debugKeys);
    }

    public DebugSystem(OrthographicCamera camera, int...debugKeys){
        super(Family.all(BoundsComponent.class).get());
        init(camera, Color.YELLOW, Color.RED, debugKeys);
    }

    public void setPixelsPerUnit(int ppu){
        this.pixelsPerUnit = ppu;
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
        rectangles = new Array<>();
        circles = new Array<>();
        paths = new Array<>();
        v1 = new Vector2();
        v2 = new Vector2();
        pPos = new Vector2();

        bm = ComponentMapper.getFor(BoundsComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
        cm = ComponentMapper.getFor(CircleBoundsComponent.class);
        pm = ComponentMapper.getFor(PathFollowComponent.class);
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


            for(Entity rect:rectangles){
                shapeRenderer.setColor(boundsColor);
                BoundsComponent bounds = bm.get(rect);
                shapeRenderer.rect(bounds.bounds.x, bounds.bounds.y, bounds.bounds.width, bounds.bounds.height);
                shapeRenderer.setColor(originColor);
                float boundsCenterX = bounds.bounds.x + (bounds.bounds.width / 2f);
                float boundsCenterY = bounds.bounds.y + (bounds.bounds.height / 2f);
                shapeRenderer.circle(boundsCenterX, boundsCenterY, 0.2f);
                if(bounds.offset.x != 0f || bounds.offset.y != 0f) {
                    Vector2 offset = bounds.offset;
                    TransformComponent tc = tm.get(rect);
                    if(tc != null) {
                        offset = VectorUtils.rotateVector(offset, tc.rotation);
                    }
                    shapeRenderer.line(boundsCenterX, boundsCenterY, boundsCenterX - offset.x, boundsCenterY - offset.y);
                }
            }


            for(Entity circle:circles){
                shapeRenderer.setColor(boundsColor);
                CircleBoundsComponent bounds = cm.get(circle);
                shapeRenderer.circle(bounds.circle.x, bounds.circle.y, bounds.circle.radius);
                shapeRenderer.circle(bounds.circle.x, bounds.circle.y, 0.2f);
                shapeRenderer.setColor(originColor);
                float boundsCenterX = bounds.circle.x;
                float boundsCenterY = bounds.circle.y;
                shapeRenderer.circle(boundsCenterX, boundsCenterY, 0.2f);
                if(bounds.offset.x != 0f || bounds.offset.y != 0f) {
                    Vector2 offset = bounds.offset;
                    TransformComponent tc = tm.get(circle);
                    if(tc != null) {
                        offset = VectorUtils.rotateVector(offset, tc.rotation);
                    }
                    shapeRenderer.line(boundsCenterX, boundsCenterY, boundsCenterX - offset.x, boundsCenterY - offset.y);
                }
            }

            shapeRenderer.end();

            for(Entity path:paths){
                //Render Curve with 100 samples
                PathFollowComponent pc = pm.get(path);
                float k = 100f;
                Gdx.gl20.glLineWidth(3f);
                shapeRenderer.setProjectionMatrix(cam.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.GREEN);
                for(float i = 0f; i < k-1f; ++i)
                {
                    pc.path.valueAt(v1, (i / k));
                    pc.path.valueAt(v2, ((i + 1f) / k));
                    shapeRenderer.line(v1, v2);
                }
                //Render End Points
                shapeRenderer.setColor(Color.MAGENTA);
                pc.path.valueAt(v1, 0f);
                pc.path.valueAt(v2, 1f);
                pc.path.valueAt(pPos, (pc.elapsedTime/pc.totalPathTime));
                shapeRenderer.circle(v1.x, v1.y, 1f);
                shapeRenderer.circle(v2.x, v2.y, 1f);
                shapeRenderer.circle(pPos.x, pPos.y, 1f);



                shapeRenderer.end();
            }
        }

        rectangles.clear();
        circles.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        if(bm.has(entity)){
            rectangles.add(entity);
        }else{
            circles.add(entity);
        }

        if(pm.has(entity)){
            paths.add(entity);
        }
    }
}
