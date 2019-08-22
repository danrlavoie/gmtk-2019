package com.gmtk.game.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.gmtk.game.Util;
import com.gmtk.game.overworld.OverworldManager;

public class OverworldKeyboardInputProcessor extends InputAdapter {
    private OverworldManager overworldManager;
    public OverworldKeyboardInputProcessor(OverworldManager om) {
        this.overworldManager = om;
    }
    public boolean keyDown (int keycode) {
        boolean handled = false;
        if (keycode == Input.Keys.W) {
            overworldManager.isUpKeyDown = true;
            handled = true;
        }
        if (keycode == Input.Keys.A) {
            overworldManager.isLeftKeyDown = true;
            handled = true;
        }
        if (keycode == Input.Keys.S) {
            overworldManager.isDownKeyDown = true;
            handled = true;
        }
        if (keycode == Input.Keys.D) {
            overworldManager.isRightKeyDown = true;
            handled = true;
        }
        return handled;
    }

    public boolean keyUp (int keycode) {
        boolean handled = false;
        if (keycode == Input.Keys.W) {
            overworldManager.isUpKeyDown = false;
            handled = true;
        }
        if (keycode == Input.Keys.A) {
            overworldManager.isLeftKeyDown = false;
            handled = true;
        }
        if (keycode == Input.Keys.S) {
            overworldManager.isDownKeyDown = false;
            handled = true;
        }
        if (keycode == Input.Keys.D) {
            overworldManager.isRightKeyDown = false;
            handled = true;
        }
        return handled;
    }
}
