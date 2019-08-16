package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.gmtk.game.input.DialogKeyboardInputProcessor;
import com.gmtk.game.input.MapKeyboardInputProcessor;
import com.gmtk.game.input.MenuKeyboardInputProcessor;
import com.gmtk.game.input.OverworldKeyboardInputProcessor;

public class GameScreen implements Screen {
    final TheGame game;
    Renderer renderer;

    Player player;
    GameController gameController;

    private Array<Sprite> walls;

    final public static int CANVAS_WIDTH = 1600;
    final public static int CANVAS_HEIGHT = 900;

    public float leftXAxisValue, rightXAxisValue;
    public float leftYAxisValue, rightYAxisValue;

    public Vector2 rightStick;

    public float leftStickDebounce;
    public float rightStickDebounce;

    public GameScreen(final TheGame game) {
        this.game = game;
        renderer = new Renderer();


//        gameController = new GameController(this);
//        Gdx.app.log("CONTROLLERS", Controllers.getControllers().toString());
//        Controllers.addListener(gameController);
        leftXAxisValue = 0;
        leftYAxisValue = 0;
        rightXAxisValue = 0;
        rightYAxisValue = 0;
        rightStick = new Vector2(rightXAxisValue, rightYAxisValue);
        leftStickDebounce = 0;
        rightStickDebounce = 0;

        player = new Player(renderer.playerImage);

        walls = new Array<Sprite>();
        spawnWalls();

    }

    @Override
    public void show() {
        // stuff that happens
        // when the screen is shown
    }

    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void hide() { }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    private void spawnWalls() {
        int[][] coordinates = {
                {300, -64},
                {876, 400},
                {-276, 400},
                {300, 844},
        };
        int[] rotation = {
                0,
                90,
                270,
                180,
        };
        for (int i = 0; i < coordinates.length; i++ ) {
            Sprite wall = new Sprite(renderer.wallImage);
            wall.setRotation(rotation[i]);
            wall.setPosition(coordinates[i][0], coordinates[i][1]);
            walls.add(wall);
        }
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        renderer.drawSprites(player, walls);

        movePlayer();
    }

    private void movePlayer() {
        player.accelerateX(leftXAxisValue);
        player.accelerateY(leftYAxisValue);
        player.moveX(walls);
        player.moveY(walls);
    }


    @Override
    public void dispose () {
        renderer.dispose();
    }
}
