package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Renderer {
    SpriteBatch batch;
    Texture playerImage;
    Texture wallImage;
    BitmapFont font;

    float animationTimeP;
    float animationTimeES;

    Animation<TextureRegion> idleAnimationP;
    Animation<TextureRegion> walkAnimationP;
    Animation<TextureRegion> throwAnimationP;
    Animation<TextureRegion> hurtAnimationP;
    Animation<TextureRegion> dieAnimationP;
    Texture playerSheet;

    public Renderer() {
        this.font = new BitmapFont();
        this.font.getData().setScale(4,4);

        this.batch = new SpriteBatch();
        playerImage = new Texture(Gdx.files.internal("bucket.png"));
        wallImage = new Texture(Gdx.files.internal("wall.png"));
        playerSheet = new Texture(Gdx.files.internal("player.png"));


        TextureRegion[][] player = TextureRegion.split(playerSheet, 64, 64);
        TextureRegion[] idleFramesP = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            idleFramesP[i] = player[0][i];
        }
        TextureRegion[] walkFramesP = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            walkFramesP[i] = player[1][i];
        }
        TextureRegion[] throwFramesP = new TextureRegion[7];
        for (int i = 0; i < 7; i++) {
            throwFramesP[i] = player[2][i];
        }
        TextureRegion[] hurtFramesP = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            hurtFramesP[i] = player[3][i];
        }
        TextureRegion[] dieFramesP = new TextureRegion[7];
        for (int i = 0; i < 7; i++) {
            dieFramesP[i] = player[4][i];
        }

        idleAnimationP = new Animation<TextureRegion>(0.1f, idleFramesP);
        walkAnimationP = new Animation<TextureRegion>(0.1f, walkFramesP);
        throwAnimationP = new Animation<TextureRegion>(0.1f, throwFramesP);
        hurtAnimationP = new Animation<TextureRegion>(0.1f, hurtFramesP);
        dieAnimationP = new Animation<TextureRegion>(0.1f, dieFramesP);
        animationTimeP = 0f;
    }

    public void drawSprites(
            Player player,
            Array<Sprite> walls
    ) {
        animationTimeP += Gdx.graphics.getDeltaTime();
        animationTimeES += Gdx.graphics.getDeltaTime();
        batch.begin();
        for (Sprite w : walls) {
            w.draw(batch);
        }
        drawPlayer(player);
        batch.end();
    }

    public void drawPlayer(Player player) {
        Animation<TextureRegion> currentAnimation;

        if (player.isMoving()) {
            currentAnimation = walkAnimationP;
        }
        else {
                currentAnimation = idleAnimationP;
        }
        TextureRegion currentFrame;
        currentFrame = currentAnimation.getKeyFrame(animationTimeP, false);
        if (currentAnimation.isAnimationFinished(animationTimeP)) {
            animationTimeP = 0f;
        }
        boolean flip = (player.isMovingRight());
        batch.draw(
                currentFrame,
                flip ? player.getX() + player.getWidth() : player.getX(),
                player.getY(),
                flip ? -player.getWidth() : player.getWidth(),
                player.getHeight()
        );
    }
    public void dispose() {
        batch.dispose();
        playerImage.dispose();
        playerSheet.dispose();
        wallImage.dispose();
        font.dispose();
    }
}
