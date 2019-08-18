package com.gmtk.game.overworld;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OverworldRenderer {
    private SpriteBatch batch;
    private BitmapFont font;

    public OverworldRenderer() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(2,2);
    }

    public void renderBackground() {

    }

    public void renderObjects() {

    }

    public void renderActors() {

    }

    public void renderPlayer() {

    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
