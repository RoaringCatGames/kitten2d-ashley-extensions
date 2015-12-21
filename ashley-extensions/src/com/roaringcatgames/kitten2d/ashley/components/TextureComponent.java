package com.roaringcatgames.kitten2d.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by barry on 12/8/15 @ 8:30 PM.
 */
public class TextureComponent implements Component {
    public TextureRegion region = null;

    public static TextureComponent create(){
        return new TextureComponent();
    }
    public TextureComponent setRegion(TextureRegion region){
        this.region = region;
        return this;
    }
}
