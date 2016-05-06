package com.roaringcatgames.kitten2d.ashley;

import com.badlogic.ashley.core.ComponentMapper;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.TweenComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;

/**
 * Provides static mapper instances so that we can
 * quickly just grab a mapper and do work without
 * having to call {@link ComponentMapper} to get an
 * instance all over the place.
 */
public class K2ComponentMappers {

    public static ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    public static ComponentMapper<TweenComponent> twm = ComponentMapper.getFor(TweenComponent.class);
    public static ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
}
