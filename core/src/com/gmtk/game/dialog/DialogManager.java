package com.gmtk.game.dialog;

import com.badlogic.gdx.utils.Array;

public class DialogManager {
    private String textToRender;
    private Array<String> screensOfText;
    private int textBlockPointer;
    private DialogRenderer dialogRenderer;

    public DialogManager() {
        this.dialogRenderer = new DialogRenderer();
    }

    public void loadBlockOfText() {
        screensOfText = new Array<String>();
        screensOfText.add(String.join(
                "\n",
                "The veteran... did not have it in him.",
                "The only memory of today will be the audience's",
                "laughs at his pitiful performance."
        ));
        screensOfText.add(String.join(
                "\n",
                "It was the best of times, it was the worst of times.",
                "It was the age of wisdom, it was the age of foolishness.",
                "It was the epoch of belief, it was the epoch of incredulity.",
                "It was the season of Light, it was the season of Darkness.",
                "It was the spring of hope, it was the winter of despair.",
                "We had everything before us, we had nothing before us.",
                "We were all going direct to heaven, we were all going direct the other way - in short, the period was so far like the present period, that some of its",
                "noisiest authorities insisted on its being received,",
                "for good or for evil, in the superlative degree of comparison only."
        ));
        textBlockPointer = 0;
        textToRender = screensOfText.get(textBlockPointer);
    }

    public void render() {
        dialogRenderer.renderBlockOfText(textToRender);
    }

    public void dispose() {
        dialogRenderer.dispose();
    }

    public void handleDialogAdvanceInput() {
        if (dialogRenderer.advanceToEndOfBlock()) {
            textBlockPointer++;
            if (screensOfText.size > textBlockPointer)
                textToRender = screensOfText.get(textBlockPointer);
            else {
                textToRender = null;
            }
        }
    }

    public boolean anyDialogToRender() {
        return textToRender != null;
    }
}
