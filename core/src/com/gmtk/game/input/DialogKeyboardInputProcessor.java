package com.gmtk.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.gmtk.game.dialog.DialogManager;

public class DialogKeyboardInputProcessor extends InputAdapter {
    private DialogManager dialogManager;
    public DialogKeyboardInputProcessor(DialogManager dm) {
        super();
        dialogManager = dm;
    }
    public boolean keyDown (int keycode) {
        return false;
    }

    public boolean keyUp (int keycode) {
        if (dialogManager.anyDialogToRender() && keycode == Input.Keys.SPACE) {
            dialogManager.handleDialogAdvanceInput();
            return true;
        }
        return false;
    }

    public boolean keyTyped (char character) {
        return false;
    }
}
