package com.gmtk.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Spear extends Sprite {
    private float x, y;
    private float xSpeed, ySpeed;
    private int width, height;
    private int rotation;
    public Spear(Texture texture) {
        super(texture);
        this.x = 1600 / 2 - 64 / 2;
        this.y = 900 / 2 - 64 / 2;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.width = 16;
        this.height = 64;
        this.rotation = 0;
        this.setPosition(this.x, this.y);
        this.setRotation(this.rotation);
    }
}
