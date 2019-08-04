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
    private boolean wasThrown;
    private boolean hitAnEnemy;
    private boolean held;
    private int id;

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Spear && ((Spear)obj).id == this.id);
    }

    public boolean isHeld() { return held; }

    public void setHeld(boolean held) { this.held = held; }

    public boolean getWasThrown() {
        return wasThrown;
    }

    public void setWasThrown(boolean wasThrown) {
        this.wasThrown = wasThrown;
    }

    public boolean didHitAnEnemy() { return hitAnEnemy; }

    public void setHitAnEnemy(boolean hitAnEnemy) { this.hitAnEnemy = hitAnEnemy; }

    public void equip() {
        this.timeInFlight = 0;
    }


    public boolean isMoving() {
        return this.speed > 0.05;
    }

    public Spear(Texture texture, float x, float y, int id) {
        super(texture);
        this.setX(x);
        this.setY(y);
        this.speed = 0;
        this.width = 16;
        this.height = 64;
        this.setRotation(0);
        this.wasThrown = false;
        this.timeInFlight = 0;
        this.hitAnEnemy = false;
        this.held = false;
        this.id = id;
    }

    public void resetTimeInFlight() { this.timeInFlight = 0; }
    public void setSpeed(float value) {
        this.speed = value;
    }
    public float getSpeed() { return this.speed; }
    public void move(Array<Sprite> walls) {
        this.timeInFlight += Gdx.graphics.getDeltaTime();
        if (this.timeInFlight > 1.5) {
            this.setSpeed(0);
            this.wasThrown = false;
            this.timeInFlight = 0;
            this.hitAnEnemy = false;
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
                this.hitAnEnemy = false;
                GameScreen.playSpearWallSound();
            }
        }

    }
}
