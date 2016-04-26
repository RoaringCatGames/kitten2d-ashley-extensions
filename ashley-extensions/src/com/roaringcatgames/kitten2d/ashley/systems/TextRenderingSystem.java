package com.roaringcatgames.kitten2d.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.components.TextComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Created by barry on 3/1/16 @ 8:46 PM.
 */
public class TextRenderingSystem extends QueuedIteratingSystem {

    private ComponentMapper<TextComponent> tm;
    private ComponentMapper<TransformComponent> tfm;
    private OrthographicCamera guiCam;
    private OrthographicCamera worldCam;
    private float rateW;
    private float rateH;

    private SpriteBatch batch;

    public TextRenderingSystem(SpriteBatch batch, OrthographicCamera guiCam, OrthographicCamera worldCam){
        super(Family.all(TextComponent.class, TransformComponent.class).get());
        this.tm = ComponentMapper.getFor(TextComponent.class);
        this.tfm = ComponentMapper.getFor(TransformComponent.class);
        this.guiCam = guiCam;
        this.worldCam = worldCam;
        this.batch = batch;

        rateW = guiCam.viewportWidth/worldCam.viewportWidth;
        rateH = guiCam.viewportHeight/worldCam.viewportHeight;
    }

    @Override
    protected void processQueue(Array<Entity> queue, float deltaTime) {
        guiCam.update();
        batch.setProjectionMatrix(guiCam.combined);
        batch.enableBlending();
        batch.begin();

        for(Entity entity:queue) {
            TextComponent text = tm.get(entity);
            TransformComponent transform = tfm.get(entity);

            if (text.text != null && !transform.isHidden) {
                GlyphLayout textData = new GlyphLayout(text.font, text.text);
                float targetX = (transform.position.x - ((textData.width/2f)/rateW));
                float xTranslatedPosition = guiCam.position.x-(worldCam.position.x-targetX)*rateW;//(transform.position.x - textData.width/2f);
                float yTranslatedPosition = guiCam.position.y-(worldCam.position.y-transform.position.y)*rateH;//(transform.position.y - textData.height/2f);
                text.font.draw(batch, text.text, xTranslatedPosition, yTranslatedPosition);
            }
        }
        batch.end();
    }
}