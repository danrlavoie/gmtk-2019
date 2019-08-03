package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Rectangle {
    private float x, y;
    private float xSpeed, ySpeed;
    final private float MAX_SPEED = 30;
    final private float DECELERATE = 0.9f;
    private int width, height;
    public Player() {
        super();
        this.x = 1600 / 2 - 64 / 2;
        this.y = 900 / 2 - 64 / 2;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.width = 64;
        this.height = 64;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
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
        if (this.xSpeed > MAX_SPEED) this.xSpeed = MAX_SPEED;
        if (this.xSpeed < -MAX_SPEED) this.xSpeed = -MAX_SPEED;
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
        if (this.ySpeed > MAX_SPEED) this.ySpeed = MAX_SPEED;
        if (this.ySpeed < -MAX_SPEED) this.ySpeed = -MAX_SPEED;
    }
    public void moveX() {
        this.x += 14 * (this.xSpeed * Gdx.graphics.getDeltaTime());
        if (this.x < 0) this.x = 0;
        if (this.x > GMTKGame.CANVAS_WIDTH) this.x = GMTKGame.CANVAS_WIDTH;
    }
    public void moveY() {
        this.y += 14 * (this.ySpeed * Gdx.graphics.getDeltaTime());
        if (this.y < 0) this.y = 0;
        if (this.y > GMTKGame.CANVAS_HEIGHT) this.y = GMTKGame.CANVAS_HEIGHT;
    }
}
