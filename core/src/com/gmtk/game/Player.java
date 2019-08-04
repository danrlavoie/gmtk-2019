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
    private Spear spear;
    private ActorState currentState;
    private boolean chargingThrow;
    private boolean throwing;
    private int health;
    private boolean dying;
    private boolean dead;
    final public static ActorClass actorClass = ActorClass.PLAYER;

    public boolean isChargingThrow() {
        return chargingThrow;
    }

    public void setChargingThrow(boolean chargingThrow) {
        this.chargingThrow = chargingThrow;
    }

    public boolean isThrowing() {
        return throwing;
    }

    public void setThrowing(boolean throwing) {
        this.throwing = throwing;
    }


    public ActorState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ActorState currentState) {
        this.currentState = currentState;
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

    public void hurt() {
        this.health -= 1;
    }

    public int getHealth() {
        return this.health;
    }

    public Player(Texture texture, Spear spear) {
        super(texture);
        this.setX(1600 / 2 - 64 / 2);
        this.setY(900 / 2 - 64 / 2);
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.width = 64;
        this.height = 64;
        this.spear = spear;
        this.currentState = ActorState.IDLE;
        this.chargingThrow = false;
        this.throwing = false;
        this.health = 3;
        this.dying = false;
        this.dead = false;
    }

    public boolean isDying() { return this.dying; }

    public void setDying(boolean dying) { this.dying = dying; }

    public boolean isDead() { return this.dead; }

    public void setDead(boolean dead) { this.dead = dead; }

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
        float newX = this.getX() + 12 * (this.xSpeed * Gdx.graphics.getDeltaTime());
        if (newX < 0) newX = 0;
        if (newX > ( GameScreen.CANVAS_WIDTH - this.width))
            newX = (GameScreen.CANVAS_WIDTH - this.width);
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
        float newY = this.getY() + 12 * (this.ySpeed * Gdx.graphics.getDeltaTime());
        if (newY < 0) newY = 0;
        if (newY > ( GameScreen.CANVAS_HEIGHT - this.height))
            newY = ( GameScreen.CANVAS_HEIGHT - this.height);
        this.setY(newY);
        for (Sprite w : walls) {
            if (this.getBoundingRectangle().overlaps(w.getBoundingRectangle())) {
                this.setY(oldY);
                break;
            }
        }
    }
}
