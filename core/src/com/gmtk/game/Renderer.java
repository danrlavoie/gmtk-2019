package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Renderer {
    SpriteBatch batch;
    Texture playerImage;
    Texture spearImage;
    Texture wallImage;

    float animationTime;

    Animation<TextureRegion> idleAnimation;
    Animation<TextureRegion> walkAnimation;
    Animation<TextureRegion> throwAnimation;
    Animation<TextureRegion> hurtAnimation;
    Animation<TextureRegion> dieAnimation;
    Animation<TextureRegion> currentAnimation;
    Texture playerSheet;
    Texture enemySheet;
    Texture spearEnemySheet;

    public Renderer() {
        this.batch = new SpriteBatch();
        playerImage = new Texture(Gdx.files.internal("bucket.png"));
        spearImage = new Texture(Gdx.files.internal("spear.png"));
        wallImage = new Texture(Gdx.files.internal("wall.png"));
        playerSheet = new Texture(Gdx.files.internal("player.png"));
        enemySheet = new Texture(Gdx.files.internal("gladiator.png"));
        spearEnemySheet = new Texture(Gdx.files.internal("spear-gladiator.png"));


        TextureRegion[][] tmp = TextureRegion.split(playerSheet, 64, 64);
        TextureRegion[] idleFrames = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            idleFrames[i] = tmp[0][i];
        }
        TextureRegion[] walkFrames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            walkFrames[i] = tmp[1][i];
        }
        TextureRegion[] throwFrames = new TextureRegion[7];
        for (int i = 0; i < 7; i++) {
            throwFrames[i] = tmp[2][i];
        }
        TextureRegion[] hurtFrames = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            hurtFrames[i] = tmp[3][i];
        }
        TextureRegion[] dieFrames = new TextureRegion[7];
        for (int i = 0; i < 7; i++) {
            dieFrames[i] = tmp[4][i];
        }

        idleAnimation = new Animation<TextureRegion>(0.1f, idleFrames);
        walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);
        throwAnimation = new Animation<TextureRegion>(0.1f, throwFrames);
        hurtAnimation = new Animation<TextureRegion>(0.1f, hurtFrames);
        dieAnimation = new Animation<TextureRegion>(0.1f, dieFrames);
        currentAnimation = idleAnimation;
        animationTime = 0f;
    }

    public void drawSprites(Player player, Array<Spear> spears, Array<Sprite> walls) {
        animationTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        for (Sprite w : walls) {
            w.draw(batch);
        }
        if (
                (player.isChargingThrow() || player.isThrowing())
        ) {
            player.setCurrentState(Player.PlayerState.THROWING);
        }
        else if (
                player.isMoving()
        ) {
            player.setCurrentState(Player.PlayerState.WALKING);
        }
        else {
            player.setCurrentState(Player.PlayerState.IDLE);
        }
        switch(player.getCurrentState()) {
            case WALKING:
                currentAnimation = walkAnimation;
                break;
            case HURT:
                currentAnimation = hurtAnimation;
                break;
            case DYING:
                currentAnimation = dieAnimation;
                break;
            case THROWING:
                currentAnimation = throwAnimation;
                break;
            default:
                currentAnimation = idleAnimation;
        }
        TextureRegion currentFrame;
        if (player.isChargingThrow()) {
            // Charging throw, hold on the first animation frame
            currentFrame = currentAnimation.getKeyFrames()[0];
            animationTime -= Gdx.graphics.getDeltaTime();
        }
        else {
            currentFrame = currentAnimation.getKeyFrame(animationTime, false);
        }
        if (currentAnimation.isAnimationFinished(animationTime)) {
            animationTime = 0f;
        }
        boolean flip = (player.isMovingRight());
        batch.draw(
                currentFrame,
                flip ? player.getX() + player.getWidth() : player.getX(),
                player.getY(),
                flip ? -player.getWidth() : player.getWidth(),
                player.getHeight()
        );
        for (Spear s : spears) {
            s.draw(batch);
        }
        batch.end();
    }
    public void dispose() {
        batch.dispose();
        playerImage.dispose();
        playerSheet.dispose();
        spearImage.dispose();
        wallImage.dispose();
    }
}
