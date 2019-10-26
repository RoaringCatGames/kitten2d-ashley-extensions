package com.roaringcatgames.kitten2d.ashley;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

/**
 * Defines the contract for an IActionResolver.
 */
public interface IActionResolver {

    void resolveAction(String eventName, Entity firingEntity, Engine containerEngine);
}
