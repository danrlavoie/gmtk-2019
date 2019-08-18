package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class Player extends Sprite {
    private float xSpeed, ySpeed;
    final private float MAX_SPEED = 20;
    final private float DECELERATE = 0.9f;
    private int width, height;

    public float getMaxSpeed() {
        return MAX_SPEED;
    }

    public boolean isMovingRight() {
        return this.xSpeed < 0;
    }

    public boolean isMoving() {
        return (this.xSpeed != 0 || this.ySpeed != 0);
    }

    public void stopMoving() {
        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    public Player(Texture texture) {
        super(texture);
        this.setX(1600 / 2 - 64 / 2);
        this.setY(900 / 2 - 64 / 2);
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.width = 64;
        this.height = 64;
    }

    public void accelerateX(float value) {
        if (
            (value > 0 && this.xSpeed < 0 ) ||
            (value < 0 && this.xSpeed > 0)
        ) {
            this.xSpeed = 0;
        }
        this.xSpeed += value;
        if (value == 0) {
            this.xSpeed *= (DECELERATE);
        }
//        Gdx.app.log("XVAL: ", Float.toString(value));
//        Gdx.app.log("XSPEED: ", Float.toString(this.xSpeed));
        if (this.xSpeed > getMaxSpeed()) this.xSpeed = getMaxSpeed();
        if (this.xSpeed < -getMaxSpeed()) this.xSpeed = -getMaxSpeed();
    }
    public void accelerateY(float value) {
        if (
            (value > 0 && this.ySpeed > 0 ) ||
            (value < 0 && this.ySpeed < 0)
        ) {
            this.ySpeed = 0;
        }
        // Inverted y axis
        this.ySpeed -= value;
        if (value == 0) {
            this.ySpeed *= (DECELERATE);
        }
//        Gdx.app.log("YVAL: ", Float.toString(value));
//        Gdx.app.log("YSPEED: ", Float.toString(this.ySpeed));
        if (this.ySpeed > getMaxSpeed()) this.ySpeed = getMaxSpeed();
        if (this.ySpeed < -getMaxSpeed()) this.ySpeed = -getMaxSpeed();
    }
    public void moveX() {
        float oldX = this.getX();
        float newX = this.getX() + 12 * (this.xSpeed * Gdx.graphics.getDeltaTime());
        if (newX < 0) newX = 0;
        if (newX > ( GameScreen.CANVAS_WIDTH - this.width))
            newX = (GameScreen.CANVAS_WIDTH - this.width);
        this.setX(newX);
    }
    public void moveY() {
        float oldY = this.getY();
        float newY = this.getY() + 12 * (this.ySpeed * Gdx.graphics.getDeltaTime());
        if (newY < 0) newY = 0;
        if (newY > ( GameScreen.CANVAS_HEIGHT - this.height))
            newY = ( GameScreen.CANVAS_HEIGHT - this.height);
        this.setY(newY);
    }
}
