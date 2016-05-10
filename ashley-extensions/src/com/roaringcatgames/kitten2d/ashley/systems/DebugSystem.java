package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.VectorUtils;
import com.roaringcatgames.kitten2d.ashley.components.*;

/**
 * Created by barry on 12/13/15 @ 2:22 PM.
 */
public class DebugSystem extends IteratingSystem {

    private static final float ORIGIN_PIXEL_RADIUS = 5f;

    private int[] debugKeys;
    private OrthographicCamera cam;
    private Color boundsColor;
    private Color originColor;
    private ShapeRenderer shapeRenderer;

    private Array<Entity> rectangles;
    private Array<Entity> circles;
    private Array<Entity> multiBounded;
    private Array<Entity> paths;

    private int pixelsPerUnit = 1;
    private Vector2 v1, v2, pPos;

    ComponentMapper<BoundsComponent> bm;
    ComponentMapper<CircleBoundsComponent> cm;
    ComponentMapper<MultiBoundsComponent> mbm;
    ComponentMapper<TransformComponent> tm;
    ComponentMapper<PathFollowComponent> pm;

    private boolean isDebugMode = false;
    private float originRadius;


    public DebugSystem(OrthographicCamera camera, Color boundsColor, Color originColor, int...debugKeys){
        super(Family.one(CircleBoundsComponent.class,
                          MultiBoundsComponent.class,
                          BoundsComponent.class, 
                          PathFollowComponent.class).get());
        init(camera, boundsColor, originColor, debugKeys);
    }

    public DebugSystem(OrthographicCamera camera, int...debugKeys){
        super(Family.one(CircleBoundsComponent.class,
                          MultiBoundsComponent.class,
                          BoundsComponent.class,
                          PathFollowComponent.class).get());
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

        originRadius = ORIGIN_PIXEL_RADIUS * (this.cam.viewportWidth/Gdx.graphics.getWidth());
        Gdx.app.log("DebugSystem", "Origin Radius Initialized As: " + originRadius);

        shapeRenderer = new ShapeRenderer();
        rectangles = new Array<>();
        circles = new Array<>();
        paths = new Array<>();
        multiBounded = new Array<>();

        v1 = new Vector2();
        v2 = new Vector2();
        pPos = new Vector2();

        bm = ComponentMapper.getFor(BoundsComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
        cm = ComponentMapper.getFor(CircleBoundsComponent.class);
        pm = ComponentMapper.getFor(PathFollowComponent.class);
        mbm = ComponentMapper.getFor(MultiBoundsComponent.class);
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
                TransformComponent tc = tm.get(rect);
                shapeRenderer.rect(bounds.bounds.x, bounds.bounds.y, bounds.bounds.width, bounds.bounds.height);
                ;
                float boundsCenterX = bounds.bounds.x + (bounds.bounds.width / 2f);
                float boundsCenterY = bounds.bounds.y + (bounds.bounds.height / 2f);
                shapeRenderer.circle(boundsCenterX, boundsCenterY, originRadius);

                float originX = boundsCenterX;
                float originY = boundsCenterY;
                if(tc != null){
                    originX += tc.originOffset.x;
                    originY += tc.originOffset.y;
                }
                shapeRenderer.setColor(originColor);
                shapeRenderer.circle(originX, originY, originRadius);
                if(bounds.offset.x != 0f || bounds.offset.y != 0f) {
                    Vector2 offset = bounds.offset;
                    if(tc != null) {
                        offset = VectorUtils.rotateVector(offset, tc.rotation);
                    }
                    shapeRenderer.line(boundsCenterX, boundsCenterY, boundsCenterX - offset.x, boundsCenterY - offset.y);
                }
            }


            for(Entity circle:circles){
                shapeRenderer.setColor(boundsColor);
                CircleBoundsComponent bounds = cm.get(circle);
                TransformComponent tc = tm.get(circle);
                shapeRenderer.circle(bounds.circle.x, bounds.circle.y, bounds.circle.radius);
                shapeRenderer.circle(bounds.circle.x, bounds.circle.y, 0.2f);

                float boundsCenterX = bounds.circle.x;
                float boundsCenterY = bounds.circle.y;
                shapeRenderer.circle(boundsCenterX, boundsCenterY, originRadius);

                float originX = boundsCenterX;
                float originY = boundsCenterY;
                if(tc != null){
                    originX += tc.originOffset.x;
                    originY += tc.originOffset.y;
                }
                shapeRenderer.setColor(originColor);
                shapeRenderer.circle(originX, originY, originRadius);
                if(bounds.offset.x != 0f || bounds.offset.y != 0f) {
                    Vector2 offset = bounds.offset;
                    if(tc != null) {
                        offset = VectorUtils.rotateVector(offset, tc.rotation);
                    }
                    shapeRenderer.line(boundsCenterX, boundsCenterY, boundsCenterX - offset.x, boundsCenterY - offset.y);
                }
            }
            
            for(Entity multi:multiBounded){
                MultiBoundsComponent mbc = mbm.get(multi);
                TransformComponent tc = tm.get(multi);
                for(Bound bound:mbc.bounds){
                    shapeRenderer.setColor(boundsColor);
                    float boundsCenterX;
                    float boundsCenterY;
                    if(bound.isCircle){

                        shapeRenderer.circle(bound.circle.x, bound.circle.y, bound.circle.radius);
                        shapeRenderer.circle(bound.circle.x, bound.circle.y, 0.2f);

                        boundsCenterX = bound.circle.x;
                        boundsCenterY = bound.circle.y;

                    }else{
                        shapeRenderer.rect(bound.rect.x, bound.rect.y, bound.rect.width, bound.rect.height);
                        boundsCenterX = bound.rect.x + (bound.rect.width / 2f);
                        boundsCenterY = bound.rect.y + (bound.rect.height / 2f);
                    }
                    shapeRenderer.circle(boundsCenterX, boundsCenterY, originRadius);
                    float originX = boundsCenterX;
                    float originY = boundsCenterY;
                    if(tc != null){
                        originX += tc.originOffset.x;
                        originY += tc.originOffset.y;
                    }
                    shapeRenderer.setColor(originColor);
                    shapeRenderer.circle(originX, originY, originRadius);

                    if(bound.offset.x != 0f || bound.offset.y != 0f) {
                        Vector2 offset = bound.offset;
                        if(tc != null) {
                            offset = VectorUtils.rotateVector(offset, tc.rotation);
                        }
                        shapeRenderer.line(boundsCenterX, boundsCenterY, boundsCenterX - offset.x, boundsCenterY - offset.y);
                    }
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
                pc.path.valueAt(pPos, pc.pathPosition);
                shapeRenderer.circle(v1.x, v1.y, 1f);
                shapeRenderer.circle(v2.x, v2.y, 1f);
                shapeRenderer.circle(pPos.x, pPos.y, 1f);



                shapeRenderer.end();
            }
        }

        rectangles.clear();
        circles.clear();
        paths.clear();
        multiBounded.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        if(isDebugMode) {
            
            if(bm.has(entity)){
                rectangles.add(entity);
            }
            if(cm.has(entity)){
                circles.add(entity);
            }
            if(mbm.has(entity)){
                multiBounded.add(entity);
            }

            if (pm.has(entity)) {
                paths.add(entity);
            }
        }
    }
}
