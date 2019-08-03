package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class Player extends Sprite {
    private float xSpeed, ySpeed;
    final private float MAX_SPEED = 30;
    final private float DECELERATE = 0.9f;
    private int width, height;
    private Spear spear;
    public Player(Texture texture, Spear spear) {
        super(texture);
        this.setX(1600 / 2 - 64 / 2);
        this.setY(900 / 2 - 64 / 2);
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.width = 64;
        this.height = 64;
        this.spear = spear;
    }

    public void setSpear(Spear s) {
        this.spear = s;
    }

    public boolean hasSpear(Spear s) {
        if (this.spear == null) return false;
        return (this.spear.equals(s));
    }

    public boolean hasASpear() {
        return (this.spear != null);
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
    public void moveX(Array<Sprite> walls) {
        float oldX = this.getX();
        float newX = this.getX() + 14 * (this.xSpeed * Gdx.graphics.getDeltaTime());
        if (newX < 0) newX = 0;
        if (newX > ( GMTKGame.CANVAS_WIDTH - this.width))
            newX = (GMTKGame.CANVAS_WIDTH - this.width);
        this.setX(newX);
        for (Sprite w : walls) {
            if (this.getBoundingRectangle().overlaps(w.getBoundingRectangle())) {
                this.setX(oldX);
                break;
            }
        }
    }
    public void moveY(Array<Sprite> walls) {
        float oldY = this.getY();
        float newY = this.getY() + 14 * (this.ySpeed * Gdx.graphics.getDeltaTime());
        if (newY < 0) newY = 0;
        if (newY > ( GMTKGame.CANVAS_HEIGHT - this.height))
            newY = ( GMTKGame.CANVAS_HEIGHT - this.height);
        this.setY(newY);
        for (Sprite w : walls) {
            if (this.getBoundingRectangle().overlaps(w.getBoundingRectangle())) {
                this.setY(oldY);
                break;
            }
        }

    }
}
