package com.gmtk.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class NPC extends InteractableSprite {
    private int width, height;

    public NPC(Texture texture) {
        super(texture);
        this.setX(1600 / 4 - 64 / 2);
        this.setY(900 / 4 - 64 / 2);
        this.width = 64;
        this.height = 64;
    }

}
