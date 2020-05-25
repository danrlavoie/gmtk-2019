package com.gmtk.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class InteractableSprite extends Sprite {

    public InteractableSprite(Texture texture) {
        super(texture);
    }
    public Rectangle getInteractionRectangle() {
        Rectangle bounds = new Rectangle(this.getBoundingRectangle());
        bounds.set(bounds.x-5, bounds.y-5, bounds.width+10, bounds.height+10);
        return bounds;
    }
}
