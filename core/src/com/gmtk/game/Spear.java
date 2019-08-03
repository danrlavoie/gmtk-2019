package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Spear extends Sprite {
    private float speed;
    private int width, height;

    public boolean getWasThrown() {
        return wasThrown;
    }

    public void setWasThrown(boolean wasThrown) {
        this.wasThrown = wasThrown;
    }

    private boolean wasThrown;
    public Spear(Texture texture) {
        super(texture);
        this.setX(1600 / 2 - 64 / 2);
        this.setY(900 / 2 - 16 / 2);
        this.speed = 80;
        this.width = 16;
        this.height = 64;
        this.setRotation(0);
        this.wasThrown = false;
    }
    public void setSpeed(float value) {
        this.speed = value;
    }
    public void move() {
        this.translate(
                (float)(
            this.speed *
            Gdx.graphics.getDeltaTime() *
            Math.cos(Math.toRadians(this.getRotation()))
        ),
                (float)(
            this.speed *
            Gdx.graphics.getDeltaTime() *
            Math.sin(Math.toRadians(this.getRotation()))
        )
        );
    }
}
