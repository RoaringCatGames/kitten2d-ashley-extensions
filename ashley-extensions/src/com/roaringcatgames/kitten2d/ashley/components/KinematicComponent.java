package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by barry on 12/12/15 @ 11:36 PM.
 */
public class KinematicComponent implements Component{
    public static KinematicComponent create(){
        return new KinematicComponent();
    }
}
