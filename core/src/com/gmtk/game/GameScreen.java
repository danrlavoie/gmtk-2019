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
    private Array<Spear> spears;
    private Array<Sprite> walls;
    private Array<Enemy> enemies;
    private static int enemyCount = 0;
    public static int score = 0;
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
        rounds.add(new char[]{'E', 'E'});
        rounds.add(new char[]{'E', 'E', 'E'});
        rounds.add(new char[]{'E', 'E', 'E', 'S'});
        rounds.add(new char[]{'E', 'E', 'E', 'E', 'E'});
        rounds.add(new char[]{'E', 'E', 'E', 'E', 'E', 'S'});
        rounds.add(new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'});
        rounds.add(new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'});

        spears = new Array<Spear>();
        spawnSpear(800, 450, spearCount++);
        player = new Player(renderer.playerImage, null);

        walls = new Array<Sprite>();
        spawnWalls();

        enemies = new Array<Enemy>();

    }

    @Override
    public void show() {
        // stuff that happens
        // when the screen is shown
        //spawnEnemy();
        startingRound = true;
        char[] c = rounds.get(roundIndex);
        for (int i = 0; i < c.length; i++) {
            spawnEnemy(c[i]);
        }
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

    private void spawnSpear(float x, float y, int id) {
        Spear spear = new Spear(renderer.spearImage, x, y, id);
        spears.add(spear);
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

    public void spawnEnemy(char c) {
        // playerImage is a placeholder for a 64x64 sprite
        enemyCount++;
        ActorClass a = ActorClass.ENEMY;
        if (c == 'E') {
            a = ActorClass.ENEMY;
        } else if (c == 'S') {
            a = ActorClass.SPEAR_ENEMY;
        } else if (c == 'L') {
            a = ActorClass.LION;
        }
        Enemy e = new Enemy(renderer.playerImage, a, null, enemyCount);
        boolean canPlace = false;
        while (!canPlace) {
            float x = MathUtils.random(300, 1250);
            float y = MathUtils.random(64, 780);
            e.setPosition(x, y);
            boolean farEnough = (Vector2.dst(x, y, player.getX(), player.getY()) > 150);
            boolean notOverlapping = true;
            for (Enemy other : enemies) {
                if (other.getBoundingRectangle().overlaps(e.getBoundingRectangle())) {
                    notOverlapping = false;
                }
            }
            if (farEnough && notOverlapping) {
                canPlace = true;
            }
        }
        enemies.add(e);
    }

    public void resetSpear() {
        Spear s = spears.first();
        s.setPosition(player.getX(), player.getY());
        s.setWasThrown(false);
        s.setHeld(true);
        player.setSpear(s);

        player.setThrowing(false);
        player.setChargingThrow(false);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        renderer.drawSprites(player, spears, walls, enemies);

        if (!startingRound) {
            handleThrowMechanics();
            movePlayer();
            moveSpears();
            moveEnemies();
        }
        else {
            if (TimeUtils.nanoTime() - roundStartTime > 2000000000) {
                startingRound = false;
            }
        }

        if (player.isDead()) {
            game.setScreen(new GameOver(game));
        }
        for (Enemy e : enemies) {
            if (e.isDead()) {
                enemies.removeValue(e, false);
                score++;
                if(e.getCurrentClass() == ActorClass.SPEAR_ENEMY) {
                    spawnSpear(e.getX(), e.getY(), spearCount++);
                }
            }
        }
        if (enemies.size == 0) {
            startingRound = true;
            roundStartTime = TimeUtils.nanoTime();
            roundIndex++;
            if (roundIndex < rounds.size) {
                char[] c = rounds.get(roundIndex);
                for (int i = 0; i < c.length; i++) {
                    spawnEnemy(c[i]);
                }
            }
            else {
                // Go to endgame screen
            }
        }
    }



    private void handleThrowMechanics() {
        rightStick.x = rightXAxisValue;
        rightStick.y = -rightYAxisValue;

        if (player.hasASpear()) {
            if (!player.isChargingThrow()) {
                if (!(rightStick.x == 0 && rightStick.y == 0)) {
                    player.setChargingThrow(true);
                }
            } else {
                if (!(rightStick.x == 0 && rightStick.y == 0)) {
                    throwSpeed += 600 * Gdx.graphics.getDeltaTime();
                    if (throwSpeed > MAX_THROW_SPEED) {
                        throwSpeed = MAX_THROW_SPEED;
                    }
                } else {
                    player.setThrowing(true);
                    player.setChargingThrow(false);
                    player.setSpear(null);
                }
            }
        }

    }

    private void movePlayer() {
        if (player.isChargingThrow() || player.isThrowing()) {
            player.stopMoving();
        }
        else {
            player.accelerateX(leftXAxisValue);
            player.accelerateY(leftYAxisValue);
            player.moveX(walls);
            player.moveY(walls);
        }
    }

    private void moveSpears() {
        for (Spear s : spears) {
            if (player.hasSpear(s) && player.isChargingThrow()) {
                s.setRotation(rightStick.angle() + 180);
            }
            else {
                if (player.isThrowing() && s.isHeld()) {
                    s.setSpeed(throwSpeed);
                    s.setWasThrown(true);
                    s.setHeld(false);
                    s.setRotation(s.getRotation());
                    player.setThrowing(false);
                    throwSpeed = 50;
                }
            }
            if (s.getWasThrown()) {
                if (s.isMoving()) {
                    s.move(walls);
//                    Gdx.app.log("HIT: ", Boolean.toString(s.didHitAnEnemy()));
                    if (!s.didHitAnEnemy()) {
                        for (Enemy e : enemies) {
                            if (
                                e.getBoundingRectangle().overlaps(s.getBoundingRectangle()) &&
                                !e.isDying() &&
                                !e.isDead()
                            ) {
                                e.hurt();
                                playHurtSound();
                                playReactionSound();
                                spearEnemy.play();
                                e.setCurrentState(ActorState.HURT);
                                s.setSpeed(s.getSpeed() / 4);
                                s.setHitAnEnemy(true);
                            }
                        }
                    }
                }
                else {
                    if (s.getWasThrown()) {
                        s.setWasThrown(false);
                        s.resetTimeInFlight();
                        s.setHitAnEnemy(false);
                    }
                }
            }
            if (!s.getWasThrown() && player.hasSpear(s)) {
                // Make the spear move to where the player is
                if (player.isChargingThrow()) {
                    s.setPosition(
                            (player.getX() - s.getWidth() / 3),
                            (player.getY() + s.getHeight() * 5 / 3)
                    );
                }
                else {
                    float widthPadding = (player.isMovingRight() ?
                            s.getWidth() / 3 :
                            - (s.getWidth() / 3)
                    );
                    s.setPosition(
                            (player.getX() + widthPadding),
                            (player.getY() + s.getHeight())
                    );
                    s.setRotation(player.isMovingRight() ? 180 : 0);
                }
            }
            if (!s.getWasThrown() && !player.hasASpear()) {
                if (s.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                    player.setSpear(s);
                    s.setHeld(true);
                }
            }
        }
    }

    private void moveEnemies() {
//		Gdx.app.log("ENEMIES: ",enemies.toString());
        for (int i = 0; i < enemies.size; i++) {
            Enemy e = enemies.get(i);
            Vector2 eToPlayer = new Vector2(
                    player.getX() - e.getX(), player.getY() - e.getY()
            );
            float distanceToPlayer = eToPlayer.len();
            if (!e.isThrowing() && distanceToPlayer > e.getWidth() / 2) {
                //Gdx.app.log(Integer.toString(e.getId()), "DISTANCE > width");
                float xAxisValue = (float)Math.cos(eToPlayer.angleRad());
                float yAxisValue = -(float)Math.sin(eToPlayer.angleRad());
                e.accelerateX(xAxisValue);
                e.accelerateY(yAxisValue);
                e.moveX(walls, enemies);
                e.moveY(walls, enemies);
            }
            else if (!e.isThrowing()) {
                // We're close, attack!
                e.setThrowing(true);
                e.stopMoving();
            }
        }
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
