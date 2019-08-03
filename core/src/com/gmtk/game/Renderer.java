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

    Animation<TextureRegion> idleAnimationP;
    Animation<TextureRegion> walkAnimationP;
    Animation<TextureRegion> throwAnimationP;
    Animation<TextureRegion> hurtAnimationP;
    Animation<TextureRegion> dieAnimationP;
    Animation<TextureRegion> currentAnimation;
    Animation<TextureRegion> idleAnimationE;
    Animation<TextureRegion> walkAnimationE;
    Animation<TextureRegion> throwAnimationE;
    Animation<TextureRegion> hurtAnimationE;
    Animation<TextureRegion> dieAnimationE;
    Animation<TextureRegion> idleAnimationS;
    Animation<TextureRegion> walkAnimationS;
    Animation<TextureRegion> throwAnimationS;
    Animation<TextureRegion> hurtAnimationS;
    Animation<TextureRegion> dieAnimationS;
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


        TextureRegion[][] player = TextureRegion.split(playerSheet, 64, 64);
        TextureRegion[][] enemy = TextureRegion.split(enemySheet, 64, 64);
        TextureRegion[][] spearEnemy = TextureRegion.split(spearEnemySheet, 64, 64);
        TextureRegion[] idleFramesP = new TextureRegion[5];
        TextureRegion[] idleFramesE = new TextureRegion[5];
        TextureRegion[] idleFramesS = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            idleFramesP[i] = player[0][i];
            idleFramesE[i] = enemy[0][i];
            idleFramesS[i] = spearEnemy[0][i];
        }
        TextureRegion[] walkFramesP = new TextureRegion[8];
        TextureRegion[] walkFramesE = new TextureRegion[8];
        TextureRegion[] walkFramesS = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            walkFramesP[i] = player[1][i];
            walkFramesE[i] = enemy[1][i];
            walkFramesS[i] = spearEnemy[1][i];
        }
        TextureRegion[] throwFramesP = new TextureRegion[7];
        TextureRegion[] throwFramesE = new TextureRegion[7];
        TextureRegion[] throwFramesS = new TextureRegion[7];
        for (int i = 0; i < 7; i++) {
            throwFramesP[i] = player[2][i];
            throwFramesE[i] = enemy[2][i];
            throwFramesS[i] = spearEnemy[2][i];
        }
        TextureRegion[] hurtFramesP = new TextureRegion[3];
        TextureRegion[] hurtFramesE = new TextureRegion[3];
        TextureRegion[] hurtFramesS = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            hurtFramesP[i] = player[3][i];
            hurtFramesE[i] = enemy[3][i];
            hurtFramesS[i] = spearEnemy[3][i];
        }
        TextureRegion[] dieFramesP = new TextureRegion[7];
        TextureRegion[] dieFramesE = new TextureRegion[7];
        TextureRegion[] dieFramesS = new TextureRegion[7];
        for (int i = 0; i < 7; i++) {
            dieFramesP[i] = player[4][i];
            dieFramesE[i] = enemy[4][i];
            dieFramesS[i] = spearEnemy[4][i];
        }

        idleAnimationP = new Animation<TextureRegion>(0.1f, idleFramesP);
        walkAnimationP = new Animation<TextureRegion>(0.1f, walkFramesP);
        throwAnimationP = new Animation<TextureRegion>(0.1f, throwFramesP);
        hurtAnimationP = new Animation<TextureRegion>(0.1f, hurtFramesP);
        dieAnimationP = new Animation<TextureRegion>(0.1f, dieFramesP);
        currentAnimation = idleAnimationP;
        idleAnimationE = new Animation<TextureRegion>(0.1f, idleFramesE);
        walkAnimationE = new Animation<TextureRegion>(0.1f, walkFramesE);
        throwAnimationE = new Animation<TextureRegion>(0.1f, throwFramesE);
        hurtAnimationP = new Animation<TextureRegion>(0.1f, hurtFramesE);
        dieAnimationE = new Animation<TextureRegion>(0.1f, dieFramesE);
        idleAnimationS = new Animation<TextureRegion>(0.1f, idleFramesS);
        walkAnimationS = new Animation<TextureRegion>(0.1f, walkFramesS);
        throwAnimationS = new Animation<TextureRegion>(0.1f, throwFramesS);
        hurtAnimationS = new Animation<TextureRegion>(0.1f, hurtFramesS);
        dieAnimationS = new Animation<TextureRegion>(0.1f, dieFramesS);
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
            player.setCurrentState(ActorState.THROWING);
        }
        else if (
                player.isMoving()
        ) {
            player.setCurrentState(ActorState.WALKING);
        }
        else {
            player.setCurrentState(ActorState.IDLE);
        }
        switch(player.getCurrentState()) {
            case WALKING:
                currentAnimation = walkAnimationP;
                break;
            case HURT:
                currentAnimation = hurtAnimationP;
                break;
            case DYING:
                currentAnimation = dieAnimationP;
                break;
            case THROWING:
                currentAnimation = throwAnimationP;
                break;
            default:
                currentAnimation = idleAnimationP;
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
