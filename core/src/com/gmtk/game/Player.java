package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite {
    private float xSpeed, ySpeed;
    final private float MAX_SPEED = 30;
    final private float DECELERATE = 0.9f;
    private int width, height;
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
        this.setX(this.getX() + 14 * (this.xSpeed * Gdx.graphics.getDeltaTime()));
        if (this.getX() < 0) this.setX(0);
        if (this.getX() > ( GMTKGame.CANVAS_WIDTH - this.width))
            this.setX(GMTKGame.CANVAS_WIDTH - this.width);
    }
    public void moveY() {
        this.setY(this.getY() + 14 * (this.ySpeed * Gdx.graphics.getDeltaTime()));
        if (this.getY() < 0) this.setY(0);
        if (this.getY() > ( GMTKGame.CANVAS_HEIGHT - this.height))
            this.setY( GMTKGame.CANVAS_HEIGHT - this.height);
    }
}
