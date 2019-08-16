package com.gmtk.game.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.gmtk.game.Util;

public class MapRenderer {
    private SpriteBatch batch;
    private BitmapFont font;

    private Array<String> locations;
    private String currentLocation;
    private String highlightedLocation;

    public MapRenderer() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(2,2);
        this.locations = new Array<String>();
        this.currentLocation = "";
        this.highlightedLocation = "";
        this.initialize();
    }
    private void initialize() {
        locations.addAll("School", "Dorm", "Mall", "Station", "Village", "Forest");
        currentLocation = "Dorm";
        highlightedLocation = "Dorm";
    }

    public void shiftHighlightedLocation(Util.Direction d) {
        int index = locations.indexOf(highlightedLocation, false);
        if (d == Util.Direction.DOWN) {
            index += 1;
        }
        else {
            index -= 1;
        }
        if (index < 0) {
            // Wrap around to the end
            index = locations.size - 1;
        }
        else if (index >= locations.size) {
            index = 0;
        }

        highlightedLocation = locations.get(index);
    }

    public void renderLocationList() {
        batch.begin();
        int x = 200;
        int y = 1080 - 200;
        for (String s : locations) {
            if(s.equals(highlightedLocation)) {
                font.setColor(Color.GOLDENROD);
            }
            else {
                font.setColor(Color.YELLOW);
            }
            y -= 50;
            font.draw(batch, s, x, y);
        }
        batch.end();

    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }


}
