package com.gmtk.game.overworld;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.gmtk.game.InteractableSprite;
import com.gmtk.game.NPC;
import com.gmtk.game.Player;

public class OverworldManager {
    private OverworldRenderer overworldRenderer;
    private Player player;
    private NPC npc;

    private Array<InteractableSprite> collidables;

    public float leftXAxisValue, rightXAxisValue;
    public float leftYAxisValue, rightYAxisValue;
    public boolean isUpKeyDown, isDownKeyDown, isLeftKeyDown, isRightKeyDown;

    public boolean shouldShowActionIcon;

    public OverworldManager() {
        this.overworldRenderer = new OverworldRenderer();
        this.player = new Player(overworldRenderer.getPlayerImage());
        this.npc = new NPC(overworldRenderer.getNPCImage());
        this.collidables = new Array<InteractableSprite>();
        collidables.add(npc);
        leftXAxisValue = 0;
        leftYAxisValue = 0;
        rightXAxisValue = 0;
        rightYAxisValue = 0;
        isUpKeyDown = isDownKeyDown = isLeftKeyDown = isRightKeyDown = false;

        shouldShowActionIcon = false;
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
        overworldRenderer.renderActors(npc);
        overworldRenderer.renderPlayer(player.getX(), player.getY());
        overworldRenderer.renderHUD(shouldShowActionIcon);
        moveActors();
        movePlayer();
    }

    private void moveActors() {

    }

    private void movePlayer() {
        player.accelerateX(leftXAxisValue);
        player.accelerateY(leftYAxisValue);
        player.moveX(collidables);
        player.moveY(collidables);
        boolean anyActions = false;
        for (InteractableSprite c : collidables) {
            if (player.getBoundingRectangle().overlaps(c.getInteractionRectangle())) {
                anyActions = true;
            }
        }
        shouldShowActionIcon = anyActions;
    }

    public void dispose() {
        overworldRenderer.dispose();
    }
}
