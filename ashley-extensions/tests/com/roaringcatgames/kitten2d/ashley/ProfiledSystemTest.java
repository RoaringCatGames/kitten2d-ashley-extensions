package com.roaringcatgames.kitten2d.ashley;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.roaringcatgames.kitten2d.ashley.systems.ProfiledSystem;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Assert.*;

/**
 * Created by barry on 3/31/16 @ 7:23 PM.
 */
public class ProfiledSystemTest {

    private class SystemA extends EntitySystem {
        public void update(float deltaTime) {
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {

            }
        }
    }

    private class SystemB extends EntitySystem {
        public void update(float deltaTime) {
            try {
                Thread.sleep(2L);
            } catch (InterruptedException e) {

            }
        }
    }

    @Test
    public void profiledSystemTest () {
        Engine engine = new Engine();
        ProfiledSystem<SystemA> system = new ProfiledSystem<SystemA>(new SystemA());

        engine.addSystem(system);
        engine.update(0.0f);

        Assert.assertTrue(system.getLastUpdateTime() > 0L);
    }

    @Ignore()
    @Test
    public void engineSupportsMultipleProfiledSystemsTest () {
        Engine engine = new Engine();
        ProfiledSystem<SystemA> systemA = new ProfiledSystem<SystemA>(new SystemA());
        ProfiledSystem<SystemB> systemB = new ProfiledSystem<SystemB>(new SystemB());

        engine.addSystem(systemA);
        engine.addSystem(systemB);
        engine.update(0.0f);

        Assert.assertEquals("Engine should have 2 registered Systems", engine.getSystems().size());
        Assert.assertTrue("SystemA should have been run and be greater than 0L", systemA.getLastUpdateTime() > 0L);
        Assert.assertTrue("SystemB should have been run and be greater than 1L", systemB.getLastUpdateTime() > 1L);

    }
}
