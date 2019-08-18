package com.gmtk.game.overworld;

import com.gmtk.game.Player;

public class OverworldManager {
    private OverworldRenderer overworldRenderer;
    private Player player;

    public float leftXAxisValue, rightXAxisValue;
    public float leftYAxisValue, rightYAxisValue;

    public OverworldManager() {
        this.overworldRenderer = new OverworldRenderer();
        this.player = new Player(overworldRenderer.getPlayerImage());
        leftXAxisValue = 0;
        leftYAxisValue = 0;
        rightXAxisValue = 0;
        rightYAxisValue = 0;
    }

    public void setLeftXAxisValue(float f) {
        leftXAxisValue = f;
    }

    public void setLeftYAxisValue(float f) {
        leftYAxisValue = f;
    }

    public void render() {
        overworldRenderer.renderBackground();
        overworldRenderer.renderObjects();
        overworldRenderer.renderActors();
        overworldRenderer.renderPlayer(player.getX(), player.getY());
        moveActors();
        movePlayer();
    }

    private void moveActors() {

    }

    private void movePlayer() {
        player.accelerateX(leftXAxisValue);
        player.accelerateY(leftYAxisValue);
        player.moveX();
        player.moveY();
    }

    public void dispose() {
        overworldRenderer.dispose();
    }
}
