package com.gmtk.game;

import com.badlogic.gdx.Gdx;
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

public class GameScreen implements Screen {
    final TheGame game;
    Renderer renderer;

    Player player;
    GameController gameController;

    private Sound reactOne;
    private Sound reactTwo;
    private Sound reactThree;
    private static Sound[] reactions;
    private Music crowdNoise;

    private static Sound spearWall;

    private static Sound spearEnemy;

    private Sound hurtOne;
    private Sound hurtTwo;
    private Sound hurtThree;
    private static Sound[] hurtSounds;
    private Array<Sprite> walls;
    private static int enemyCount = 0;
    public static int score = 1;
    private static int spearCount = 0;

    final public static int CANVAS_WIDTH = 1600;
    final public static int CANVAS_HEIGHT = 900;

    public float leftXAxisValue, rightXAxisValue;
    public float leftYAxisValue, rightYAxisValue;

    public Vector2 rightStick;
    public float throwSpeed;
    final public static int MAX_THROW_SPEED = 700;

    public float leftStickDebounce;
    public float rightStickDebounce;

    private boolean startingRound;
    private long roundStartTime;

    private Array<char[]> rounds;
    private int roundIndex;

    public GameScreen(final TheGame game) {
        this.game = game;
        renderer = new Renderer();

        crowdNoise = Gdx.audio.newMusic(Gdx.files.internal("crowd-noise.mp3"));
        reactOne = Gdx.audio.newSound(Gdx.files.internal("react1.wav"));
        reactTwo = Gdx.audio.newSound(Gdx.files.internal("react2.wav"));
        reactThree = Gdx.audio.newSound(Gdx.files.internal("react3.wav"));
        reactions = new Sound[]{ reactOne, reactTwo, reactThree };

        hurtOne = Gdx.audio.newSound(Gdx.files.internal("hurt1.wav"));
        hurtTwo = Gdx.audio.newSound(Gdx.files.internal("hurt2.wav"));
        hurtThree = Gdx.audio.newSound(Gdx.files.internal("hurt3.wav"));
        hurtSounds = new Sound[]{ hurtOne, hurtTwo, hurtThree };

        spearWall = Gdx.audio.newSound(Gdx.files.internal("spear-wall.wav"));
        spearEnemy = Gdx.audio.newSound(Gdx.files.internal("spear-armor.wav"));

        gameController = new GameController(this);
        Gdx.app.log("CONTROLLERS", Controllers.getControllers().toString());
        Controllers.addListener(gameController);
        leftXAxisValue = 0;
        leftYAxisValue = 0;
        rightXAxisValue = 0;
        rightYAxisValue = 0;
        rightStick = new Vector2(rightXAxisValue, rightYAxisValue);
        leftStickDebounce = 0;
        rightStickDebounce = 0;

        roundIndex = 0;
        rounds = new Array<char[]>();
        rounds.add(new char[]{'E'});

        player = new Player(renderer.playerImage);

        walls = new Array<Sprite>();
        spawnWalls();

    }

    @Override
    public void show() {
        // stuff that happens
        // when the screen is shown
        //spawnEnemy();
        startingRound = true;
        char[] c = rounds.get(roundIndex);
        roundStartTime = TimeUtils.nanoTime();
        crowdNoise.setLooping(true);
        crowdNoise.play();
    }

    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void hide() {
        crowdNoise.stop();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public static void playReactionSound() {
        int i = MathUtils.random(2);
        reactions[i].play();
    }

    public static void playHurtSound() {
        int i = MathUtils.random(2);
        hurtSounds[i].play();
    }

    public static void playSpearWallSound() {
        spearWall.play();
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

        if (!startingRound) {
            movePlayer();
        }
        else {
            if (TimeUtils.nanoTime() - roundStartTime > 2000000000) {
                startingRound = false;
            }
        }
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
        crowdNoise.dispose();
        reactOne.dispose();
        reactTwo.dispose();
        reactThree.dispose();
        spearWall.dispose();
    }
}
