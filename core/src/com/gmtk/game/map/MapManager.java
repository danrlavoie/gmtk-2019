package com.gmtk.game.map;

import com.gmtk.game.Util;

public class MapManager {
    private MapRenderer mapRenderer;
    public MapManager() {
        this.mapRenderer = new MapRenderer();
    }

    public void shiftHighlightedLocation(Util.Direction d) {
        mapRenderer.shiftHighlightedLocation(d);
    }

    public boolean isActive() {
        return true;
    }

    public void select() {
        // Do nothing for now
    }

    public void render() {
        mapRenderer.renderLocationList();
    }
    public void dispose() { mapRenderer.dispose(); }
}
