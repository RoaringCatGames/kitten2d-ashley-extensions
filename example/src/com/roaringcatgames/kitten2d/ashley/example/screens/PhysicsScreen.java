package com.roaringcatgames.kitten2d.ashley.example.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.kitten2d.ashley.systems.*;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * Sample Screen showing off the PhysicsScreen
 */
public class PhysicsScreen extends BaseDemoScreen implements InputProcessor{
    private World world;
    private Vector2 gravity = new Vector2(0f, -9.8f);
    private Array<TextureRegion> catFrames = new Array<>();

    private Entity cat;

    public PhysicsScreen(IGameProcessor game) {
        super(game);
    }

    @Override
    void childInit() {
        catFrames.add(new TextureRegion(new Texture(Gdx.files.internal("assets/cat/death_000.png"))));
        catFrames.add(new TextureRegion(new Texture(Gdx.files.internal("assets/cat/death_001.png"))));
        catFrames.add(new TextureRegion(new Texture(Gdx.files.internal("assets/cat/death_002.png"))));
        catFrames.add(new TextureRegion(new Texture(Gdx.files.internal("assets/cat/death_003.png"))));
        catFrames.add(new TextureRegion(new Texture(Gdx.files.internal("assets/cat/death_004.png"))));
        catFrames.add(new TextureRegion(new Texture(Gdx.files.internal("assets/cat/death_005.png"))));
        catFrames.add(new TextureRegion(new Texture(Gdx.files.internal("assets/cat/death_006.png"))));
        catFrames.add(new TextureRegion(new Texture(Gdx.files.internal("assets/cat/death_007.png"))));
        catFrames.add(new TextureRegion(new Texture(Gdx.files.internal("assets/cat/death_008.png"))));
        catFrames.add(new TextureRegion(new Texture(Gdx.files.internal("assets/cat/death_009.png"))));


        this.world = new World(gravity, true);

        Box2DPhysicsSystem physicsSystem = new Box2DPhysicsSystem(world);
        AnimationSystem aniSystem = new AnimationSystem();
        Box2DPhysicsDebugSystem physicsDebugSystem = new Box2DPhysicsDebugSystem(world, game.getCamera());
        FollowerSystem followerSystem = new FollowerSystem(Family.one(TransformComponent.class).get());
        BoundsSystem boundsSystem = new BoundsSystem();

        engine.addSystem(physicsSystem);
        engine.addSystem(aniSystem);
        engine.addSystem(physicsDebugSystem);
        engine.addSystem(boundsSystem);
        engine.addSystem(followerSystem);

        this.cat = buildBouncingCat(world);
        Entity followBox = engine.createEntity();
        followBox.add(TransformComponent.create(engine)
            .setPosition(10f, 10f, 1f));
        followBox.add(BoundsComponent.create(engine)
            .setBounds(0f, 0f, 2f, 2f));
        followBox.add(FollowerComponent.create(engine)
            .setOffset(1f, 1f)
            .setMode(FollowMode.MOVETO)
            .setFollowSpeed(10f)
            .setTarget(cat));

        Entity followBox2 = engine.createEntity();
        followBox2.add(TransformComponent.create(engine)
                .setPosition(10f, 10f, 1f));
        followBox2.add(BoundsComponent.create(engine)
                .setBounds(0f, 0f, 2f, 2f));
        followBox2.add(FollowerComponent.create(engine)
                .setOffset(-1f, -1f)
                .setMode(FollowMode.MOVETOSTICKY)
                .setFollowSpeed(10f)
                .setTarget(cat));

        engine.addEntity(cat);
        engine.addEntity(followBox);
        engine.addEntity(followBox2);
        engine.addEntity(buildBasicBox(world, game.getCamera().viewportWidth/2f, 1f, 5f, 1f));
        //engine.addEntity(buildBasicBox(world, game.getCamera().viewportWidth/2f, game.getCamera().viewportHeight - 1f, 5f, 1f));
        engine.addEntity(buildBasicBox(world, 1f, game.getCamera().viewportHeight/2f, 1f, 5f));
        engine.addEntity(buildBasicBox(world, game.getCamera().viewportWidth - 1f, game.getCamera().viewportHeight/2f, 1f, 5f));
    }


    private Entity buildBouncingCat(World world) {
        Entity e = engine.createEntity();

        AnimationComponent a = new AnimationComponent();
        a.animations.put("DEFAULT", new Animation(1f/16f, catFrames, Animation.PlayMode.LOOP));
        e.add(a);
        StateComponent state = StateComponent.create(engine).set("DEFAULT");
        e.add(state);
        TextureComponent tc = TextureComponent.create(engine);
        e.add(tc);

        TransformComponent tfc = TransformComponent.create(engine)
            .setPosition(10f, 5f, 1f)
            .setRotation(15f)
            .setScale(0.25f, 0.25f);
        e.add(tfc);

        BodyComponent bc = BodyComponent.create(engine);
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Set our body's starting position in the world
        bodyDef.position.set(10f, 23f);

        // Create our body in the world using our body definition
        bc.body = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(2f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 20f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        bc.body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();

        e.add(bc);
        return e;
    }

    private Entity buildFloorEntity(World world){
        Entity e = engine.createEntity();
        // Create our body definition
        BodyDef groundBodyDef =new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        // Set its world position
        groundBodyDef.position.set(new Vector2(game.getCamera().viewportWidth/2f, 1f));
        groundBodyDef.angle = 0f;

        BodyComponent bc = new BodyComponent();
        // Create a body from the defintion and add it to the world
        bc.body = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)

        groundBox.setAsBox(5f, 1f);

        // Create a fixture from our polygon shape and add it to our ground body
        bc.body.createFixture(groundBox, 0.0f);


        // Clean up after ourselves
        groundBox.dispose();

        e.add(bc);

        return e;
    }

    private Entity buildBasicBox(World world, float x, float y, float w, float h){
        Entity e = engine.createEntity();
        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        // Set its world position
        groundBodyDef.position.set(new Vector2(x, y));
        groundBodyDef.angle = 0f;

        BodyComponent bc = new BodyComponent();
        // Create a body from the defintion and add it to the world
        bc.body = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)

        groundBox.setAsBox(w, h);

        // Create a fixture from our polygon shape and add it to our ground body
        bc.body.createFixture(groundBox, 0.0f);


        // Clean up after ourselves
        groundBox.dispose();

        e.add(bc);

        return e;
    }

    private Vector2 force = new Vector2(0f, 1000f);
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.W){
            BodyComponent bc = K2ComponentMappers.body.get(cat);
            force.set(0f, bc.body.getMass()*10f);
            bc.body.applyLinearImpulse(force, bc.body.getWorldCenter(), true);
            bc.body.applyAngularImpulse(bc.body.getMass() * 5f, true);
        }

        if(keycode == Input.Keys.A){
            BodyComponent bc = K2ComponentMappers.body.get(cat);
            bc.body.applyAngularImpulse(bc.body.getMass() * 5f, true);
        }

        if(keycode == Input.Keys.D){
            BodyComponent bc = K2ComponentMappers.body.get(cat);

            bc.body.applyAngularImpulse(bc.body.getMass() * -5f, true);
        }

        if(keycode == Input.Keys.S){
            BodyComponent bc = K2ComponentMappers.body.get(cat);
            force.set(0f, bc.body.getMass()*-5f);
            bc.body.applyLinearImpulse(force, bc.body.getWorldCenter(), true);
        }
        return super.keyDown(keycode);
    }
}
