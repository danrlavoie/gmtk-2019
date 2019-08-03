package com.gmtk.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy extends Sprite {
    private float xSpeed, ySpeed;
    final private float MAX_SPEED = 30;
    final private float DECELERATE = 0.9f;
    private int width, height;
    private Spear spear;
    private ActorState currentState;
    private ActorClass currentClass;
    private boolean chargingThrow;
    private boolean throwing;
    public Enemy(Texture texture, ActorClass classification, Spear spear) {
        super(texture);
        this.setX(1600 / 2 - 64 / 2);
        this.setY(900 / 2 - 64 / 2);
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.width = 64;
        this.height = 64;
        this.spear = spear;
        this.currentState = ActorState.IDLE;
        this.currentClass = classification;
        this.chargingThrow = false;
        this.throwing = false;
    }
}
