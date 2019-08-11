package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class TextRenderer {
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture textBoxTexture;
    private Rectangle textBox;
    private long lastCharTime;
    private final long TIME_BETWEEN_CHARACTERS = 1000000000;
    private boolean isFullyRendered;
    private boolean isRendering;
    private int currentChar;
    public TextRenderer() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(2,2);
        this.textBoxTexture = new Texture(Gdx.files.internal("text-box.png"));
        this.textBox = new Rectangle(0,0,textBoxTexture.getWidth(),textBoxTexture.getHeight());
        this.currentChar = 0;
        this.isFullyRendered = false;
        this.isRendering = false;
        this.lastCharTime = TimeUtils.nanoTime();
    }
    public void renderBlockOfText() {
        String message = String.join(
                "\n",
                "The veteran... did not have it in him.",
                "The only memory of today will be the audience's",
                "laughs at his pitiful performance."
        );
        if (this.currentChar >= message.length()) {
            this.isFullyRendered = true;
            this.isRendering = false;
        }
        if (!this.isFullyRendered) {
            if (TimeUtils.nanoTime() - lastCharTime > TIME_BETWEEN_CHARACTERS) {
                this.currentChar++;
            }
        }



        batch.begin();
        batch.draw(textBoxTexture,textBox.x,textBox.y);
        font.draw(
            batch,
            message.substring(0, currentChar),
            20,
            348
        );
        batch.end();
    }

    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
        this.textBoxTexture.dispose();
    }
}
