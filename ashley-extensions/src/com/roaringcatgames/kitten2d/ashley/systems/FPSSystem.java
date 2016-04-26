package com.roaringcatgames.kitten2d.ashley.systems;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.kitten2d.ashley.components.FPSComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

public class FPSSystem extends IteratingSystem {


    private Entity fpsText;
    private BitmapFont font;
    private Vector2 position;
    private int cyclesPassed = 0;
    private float timeElapsed = 0f;
    private float maximumCycles = 5f;

    public FPSSystem(BitmapFont font, Vector2 position){
        super(Family.all(FPSComponent.class).get());
        this.font = font;
        this.position = position;
    }

    public FPSSystem(BitmapFont font, Vector2 position, int cyclesToCount) {
        super(Family.all(FPSComponent.class).get());
        this.maximumCycles = cyclesToCount;
        this.font = font;
        this.position = position;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        if(fpsText == null){
            fpsText = ((PooledEngine)engine).createEntity();
            fpsText.add(TextComponent.create(engine)
                    .setFont(font)
                    .setText("FPS: 0"));
            fpsText.add(TransformComponent.create(engine)
                    .setPosition(position.x, position.y, 0f));
            fpsText.add(FPSComponent.create(engine));
            engine.addEntity(fpsText);
        }
    }

    @Override
    public void update(float deltaTime) {
        cyclesPassed++;
        timeElapsed += deltaTime;

        if(cyclesPassed == maximumCycles){
            super.update(deltaTime);
            cyclesPassed = 0;
            timeElapsed = 0f;
        }

    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        if(fpsText != null){
            engine.removeEntity(fpsText);
            fpsText = null;
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        //1 second / Average(cycleTime)
        int fps = (int)Math.floor(1f/(timeElapsed/cyclesPassed));
        fpsText.getComponent(TextComponent.class).setText("FPS: " + fps);
    }
}