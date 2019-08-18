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
            overworldManager.setLeftYAxisValue(-1);
            handled = true;
        }
        if (keycode == Input.Keys.A) {
            overworldManager.setLeftXAxisValue(-1);
            handled = true;
        }
        if (keycode == Input.Keys.S) {
            overworldManager.setLeftYAxisValue(1);
            handled = true;
        }
        if (keycode == Input.Keys.D) {
            overworldManager.setLeftXAxisValue(1);
            handled = true;
        }
        return handled;
    }

    public boolean keyUp (int keycode) {
        return false;
    }
}
