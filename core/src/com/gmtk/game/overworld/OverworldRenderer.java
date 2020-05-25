package com.gmtk.game.overworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gmtk.game.NPC;

public class OverworldRenderer {
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture playerImage;
    private Texture npcImage;

    private Texture actionButtonSheet;
    private Animation<TextureRegion> actionButtonAnimation;
    private float actionButtonAnimationTime;
    private boolean showingActionIcon;

    public OverworldRenderer() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(2,2);
        playerImage = new Texture(Gdx.files.internal("bucket.png"));
        npcImage = new Texture(Gdx.files.internal("heart.png"));
        actionButtonSheet = new Texture(Gdx.files.internal("ui/action-button.png"));
        TextureRegion[][] actionButton = TextureRegion.split(actionButtonSheet, 64, 64);
        actionButtonAnimation = new Animation<TextureRegion>(0.025f, actionButton[0]);
        actionButtonAnimationTime = 0f;
        showingActionIcon = false;
    }

    public Texture getPlayerImage() { return playerImage; }
    public Texture getNPCImage() { return playerImage; }

    public void renderBackground() {

    }

    public void renderObjects() {

    }

    public void renderActors(NPC npc) {
        batch.begin();
        batch.draw(
                npc.getTexture(),
                npc.getX(),
                npc.getY(),
                npc.getWidth(),
                npc.getHeight()
        );
        batch.end();

    }

    public void renderPlayer(float x, float y) {
        batch.begin();
        batch.draw(
            playerImage,
            x,
            y,
            playerImage.getWidth(),
            playerImage.getHeight()
        );
        batch.end();
    }

    public void renderHUD(boolean shouldShowActionIcon) {
        batch.begin();
        // Render the action button animation if there is an action that can be taken.
        if (shouldShowActionIcon) {
            if (!showingActionIcon) {
                showingActionIcon = true;
                actionButtonAnimationTime = 0f;
            }
            actionButtonAnimationTime += Gdx.graphics.getDeltaTime();
            Animation<TextureRegion> currentAnimation = this.actionButtonAnimation;

            TextureRegion currentFrame = currentAnimation.getKeyFrame(actionButtonAnimationTime, false);
            batch.draw(
                    currentFrame,
                    100,
                    100
            );
        }
        else {
            showingActionIcon = false;
        }
        batch.end();
    }

    public void renderStart() {

    }
    public void renderEnd() {

    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        playerImage.dispose();
        npcImage.dispose();
        actionButtonSheet.dispose();
    }
}
