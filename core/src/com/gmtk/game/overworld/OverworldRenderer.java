package com.gmtk.game.overworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OverworldRenderer {
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture playerImage;

    public OverworldRenderer() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(2,2);
        playerImage = new Texture(Gdx.files.internal("bucket.png"));
    }

    public Texture getPlayerImage() { return playerImage; }

    public void renderBackground() {

    }

    public void renderObjects() {

    }

    public void renderActors() {
        batch.begin();
        font.draw(batch, "HELLO", 300, 300);
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

    public void renderStart() {

    }
    public void renderEnd() {

    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
