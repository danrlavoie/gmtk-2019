package com.gmtk.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.gmtk.game.Util;
import com.gmtk.game.map.MapManager;

public class MapKeyboardInputProcessor extends InputAdapter {
    private MapManager mapManager;
    public MapKeyboardInputProcessor(MapManager mm) {
        super();
        mapManager = mm;
    }
    public boolean keyDown (int keycode) {
        return false;
    }

    public boolean keyUp (int keycode) {
        if (keycode == Input.Keys.UP) {
            mapManager.shiftHighlightedLocation(Util.Direction.UP);
            return true;
        }
        else if (keycode == Input.Keys.DOWN) {
            mapManager.shiftHighlightedLocation(Util.Direction.DOWN);
            return true;
        }
        else if (keycode == Input.Keys.SPACE) {
            if (mapManager.isActive()) {
                mapManager.select();
                return true;
            }
        }
        return false;
    }
}
