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
    private String messageInMemory;
    public TextRenderer() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(2,2);
        this.textBoxTexture = new Texture(Gdx.files.internal("text-box.png"));
        this.textBox = new Rectangle(0,0,textBoxTexture.getWidth(),textBoxTexture.getHeight());
        this.currentChar = 0;
        this.isFullyRendered = true;
        this.isRendering = false;
        this.lastCharTime = TimeUtils.nanoTime();
    }

    public boolean advanceToEndOfBlock() {
        if (!this.isFullyRendered) {
            this.currentChar = this.messageInMemory.length() -1;
            return false;
        }
        else {
            return true;
        }
    }
    public void renderBlockOfText(String message) {
        if (message == null) {
            return;
        }
        if (!this.isFullyRendered) {
            if (TimeUtils.nanoTime() - lastCharTime > TIME_BETWEEN_CHARACTERS) {
                this.currentChar++;
            }
        }
        if (messageInMemory != message) {
            this.messageInMemory = message;
            this.isFullyRendered = false;
            this.isRendering = true;
            this.currentChar = 0;
        }
        if (this.currentChar >= messageInMemory.length()) {
            this.isFullyRendered = true;
            this.isRendering = false;
        }



        batch.begin();
        batch.draw(textBoxTexture,textBox.x,textBox.y);
        font.draw(
            batch,
            messageInMemory.substring(0, currentChar),
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
