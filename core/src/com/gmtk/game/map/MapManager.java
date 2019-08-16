package com.gmtk.game.map;

import com.gmtk.game.Util;

public class MapManager {
    private MapRenderer mapRenderer;
    public MapManager() {
        this.mapRenderer = new MapRenderer();
    }
    public void render() {
        mapRenderer.renderLocationList();
    }

    public void shiftHighlightedLocation(Util.Direction d) {
        mapRenderer.shiftHighlightedLocation(d);
    }
    public void dispose() { mapRenderer.dispose(); }
}
