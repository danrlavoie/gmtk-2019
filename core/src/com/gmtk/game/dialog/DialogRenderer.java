package com.gmtk.game.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class DialogRenderer {
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture textBoxTexture;
    private Rectangle textBox, decisionBox;
    private long lastCharTime;
    private final long TIME_BETWEEN_CHARACTERS = 10000000;
    private boolean isFullyRendered;
    private int currentChar;
    private String messageInMemory;


    public DialogRenderer() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(2,2);
        this.textBoxTexture = new Texture(Gdx.files.internal("text-box.png"));
        this.textBox = new Rectangle(0,0,textBoxTexture.getWidth(),textBoxTexture.getHeight());
        this.decisionBox = new Rectangle(
            textBoxTexture.getWidth() / 2,
            textBoxTexture.getHeight() / 2,
            textBoxTexture.getWidth(),
            textBoxTexture.getHeight()
        );
        this.currentChar = 0;
        this.isFullyRendered = true;
        this.lastCharTime = TimeUtils.nanoTime();
    }

    /**
     * A control function to advance to the end of the current block of text.
     *
     * @return True if the block was already at the end, therefore nothing happened.
     *         False if the block was not at the end, but has now been advanced.
     */
    public boolean advanceToEndOfBlock() {
        if (!this.isFullyRendered) {
            this.currentChar = this.messageInMemory.length() -1;
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Given a message, assigns the message to memory and renders the message bit by bit.
     * If the message changes from the one in memory, starts over from the beginning with
     * this new message
     * @param message the string of text to render. Pays no heed to line length or anything.
     */
    public void renderBlockOfText(String message) {
        if (message == null) {
            return;
        }
        if (!this.isFullyRendered) {
            if (TimeUtils.nanoTime() - lastCharTime > TIME_BETWEEN_CHARACTERS) {
                this.lastCharTime = TimeUtils.nanoTime();
                this.currentChar++;
            }
        }
        if (messageInMemory != message) {
            this.messageInMemory = message;
            this.isFullyRendered = false;
            this.currentChar = 0;
        }
        if (this.currentChar >= messageInMemory.length()) {
            this.isFullyRendered = true;
        }
        batch.begin();
        batch.draw(textBoxTexture,textBox.x,textBox.y);
        font.draw(
            batch,
            messageInMemory.substring(0, currentChar),
            20,
            348
        );
        renderDecision(new Array<String>());
        batch.end();
    }

    public void renderDecision(Array<String> answers) {
        batch.draw(textBoxTexture, decisionBox.x, decisionBox.y);
        font.draw(
            batch,
            "Test answer",
            (20 + decisionBox.x),
            (348 + decisionBox.y)
        );
    }

    /**
     * A cleanup function. Make sure to call this when the app using this class is disposed.
     */
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
        this.textBoxTexture.dispose();
    }
}
