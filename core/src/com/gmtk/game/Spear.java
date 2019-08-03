package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Spear extends Sprite {
    private float speed;
    private int width, height;
    private float timeInFlight;

    public boolean getWasThrown() {
        return wasThrown;
    }

    public void setWasThrown(boolean wasThrown) {
        this.wasThrown = wasThrown;
    }

    public void equip() {
        this.timeInFlight = 0;
    }

    private boolean wasThrown;
    public Spear(Texture texture) {
        super(texture);
        this.setX(1600 / 2 - 64 / 2);
        this.setY(900 / 2 - 16 / 2);
        this.speed = 0;
        this.width = 16;
        this.height = 64;
        this.setRotation(0);
        this.wasThrown = false;
        this.timeInFlight = 0;
    }
    public void setSpeed(float value) {
        this.speed = value;
    }
    public void move(Array<Sprite> walls) {
        if (this.speed > 0) {
            this.timeInFlight += Gdx.graphics.getDeltaTime();
            if (this.timeInFlight > 1.5) {
                this.setSpeed(0);
                this.wasThrown = false;
                this.timeInFlight = 0;
            }
            float oldX = this.getX();
            float oldY = this.getY();
            float deltaX = (float)(
                    this.speed *
                            Gdx.graphics.getDeltaTime() *
                            Math.cos(Math.toRadians(this.getRotation()))
            );
            float deltaY = (float)(
                    this.speed *
                            Gdx.graphics.getDeltaTime() *
                            Math.sin(Math.toRadians(this.getRotation()))
            );
            this.translate(deltaX, deltaY);
            for (Sprite w : walls) {
                if (this.getBoundingRectangle().overlaps(w.getBoundingRectangle())) {
                    this.setPosition(oldX + (3 * deltaX), oldY + (3 *deltaY));
                    this.setSpeed(0);
                    this.wasThrown = false;
                    this.timeInFlight = 0;
                }
            }
        }

    }
}
