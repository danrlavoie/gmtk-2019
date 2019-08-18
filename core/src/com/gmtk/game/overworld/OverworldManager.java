package com.gmtk.game.overworld;

public class OverworldManager {
    private OverworldRenderer overworldRenderer;


    public OverworldManager() {
        this.overworldRenderer = new OverworldRenderer();
    }

    public void render() {
        overworldRenderer.renderBackground();
        overworldRenderer.renderObjects();
        overworldRenderer.renderActors();
        overworldRenderer.renderPlayer();
        moveActors();
    }

    private void moveActors() {

    }

    public void dispose() {
        overworldRenderer.dispose();
    }
}
