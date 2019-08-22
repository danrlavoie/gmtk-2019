package com.gmtk.game.overworld;

import com.gmtk.game.Player;

public class OverworldManager {
    private OverworldRenderer overworldRenderer;
    private Player player;

    public float leftXAxisValue, rightXAxisValue;
    public float leftYAxisValue, rightYAxisValue;
    public boolean isUpKeyDown, isDownKeyDown, isLeftKeyDown, isRightKeyDown;

    public OverworldManager() {
        this.overworldRenderer = new OverworldRenderer();
        this.player = new Player(overworldRenderer.getPlayerImage());
        leftXAxisValue = 0;
        leftYAxisValue = 0;
        rightXAxisValue = 0;
        rightYAxisValue = 0;
        isUpKeyDown = isDownKeyDown = isLeftKeyDown = isRightKeyDown = false;
    }



    private void adjustAxisControls() {
        leftXAxisValue = 0;
        leftYAxisValue = 0;
        if (isUpKeyDown) leftYAxisValue -= 1;
        if (isDownKeyDown) leftYAxisValue += 1;
        if (isLeftKeyDown) leftXAxisValue -= 1;
        if (isRightKeyDown) leftXAxisValue += 1;
    }

    public void render() {
        adjustAxisControls();
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
