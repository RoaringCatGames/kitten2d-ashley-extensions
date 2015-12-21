package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.HealthComponent;

/**
 * Created by barry on 12/13/15 @ 5:57 PM.
 */
public class HealthRenderSystem extends IteratingSystem {

    private OrthographicCamera cam;
    private ComponentMapper<HealthComponent> hm;
    private ComponentMapper<BoundsComponent> bm;

    private Array<Entity> healths = new Array<>();
    private ShapeRenderer filledRenderer;
    private ShapeRenderer lineRenderer;

    public HealthRenderSystem(OrthographicCamera camera){
        super(Family.all(HealthComponent.class, BoundsComponent.class).get());

        cam = camera;
        hm = ComponentMapper.getFor(HealthComponent.class);
        bm = ComponentMapper.getFor(BoundsComponent.class);
        filledRenderer = new ShapeRenderer();
        lineRenderer = new ShapeRenderer();

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Gdx.gl20.glLineWidth(0.5f);
        filledRenderer.setProjectionMatrix(cam.combined);
        filledRenderer.begin(ShapeRenderer.ShapeType.Filled);
        filledRenderer.setColor(Color.GREEN);

        for(Entity entity:healths) {
            BoundsComponent bc = bm.get(entity);
            HealthComponent hc = hm.get(entity);
            float healthPercent = (hc.health/hc.maxHealth);
            float healthWidth = bc.bounds.width * healthPercent;
            if(healthPercent < 0.75f){
                filledRenderer.setColor(Color.YELLOW);
            }else if(healthPercent < 0.5f){
                filledRenderer.setColor(Color.RED);
            }else{
                filledRenderer.setColor(Color.GREEN);
            }
            //Render Health
            filledRenderer.rect(bc.bounds.x, bc.bounds.y + bc.bounds.height + 0.25f,
                    healthWidth, 0.25f);
        }


        filledRenderer.end();

        lineRenderer.setProjectionMatrix(cam.combined);
        lineRenderer.begin(ShapeRenderer.ShapeType.Line);
        lineRenderer.setColor(Color.BLACK);
        for(Entity entity:healths) {
            BoundsComponent bc = bm.get(entity);
            HealthComponent hc = hm.get(entity);
            //Outline
            lineRenderer.rect(bc.bounds.x, bc.bounds.y + bc.bounds.height + 0.25f,
                    bc.bounds.width, 0.25f);
        }
        lineRenderer.end();

        healths.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        healths.add(entity);
    }
}
