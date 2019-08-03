package com.gmtk.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GMTKGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerImage;
	Texture spearImage;
	Texture wallImage;

	Player player;
	OrthographicCamera camera;
	GameController gameController;

	private Array<Spear> spears;
	private Array<Sprite> walls;

	final public static int CANVAS_WIDTH = 1600;
	final public static int CANVAS_HEIGHT = 900;

	public float leftXAxisValue, rightXAxisValue;
	public float leftYAxisValue, rightYAxisValue;

	public boolean chargingThrow;
	public boolean throwing;
	public Vector2 rightStick;
	public float throwSpeed;
	final public static int MAX_THROW_SPEED = 500;

	public float leftStickDebounce;
	public float rightStickDebounce;
	
	@Override
	public void create () {
		playerImage = new Texture(Gdx.files.internal("bucket.png"));
		spearImage = new Texture(Gdx.files.internal("spear.png"));
		wallImage = new Texture(Gdx.files.internal("wall.png"));
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CANVAS_WIDTH, CANVAS_HEIGHT);

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

		chargingThrow = false;
		throwing = false;
		spears = new Array<Spear>();
		spawnSpear();

		walls = new Array<Sprite>();
		spawnWalls();
		player = new Player(playerImage, spears.first());
	}

	private void spawnSpear() {
	    Spear spear = new Spear(spearImage);
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
			Sprite wall = new Sprite(wallImage);
			wall.setRotation(rotation[i]);
			wall.setPosition(coordinates[i][0], coordinates[i][1]);
			walls.add(wall);
		}
	}

    public void resetSpear() {
	    Spear s = spears.first();
	    s.setPosition(player.getX(), player.getY());
	    s.setWasThrown(false);
	    player.setSpear(s);

	    throwing = false;
	    chargingThrow = false;
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

        drawSprites();

		handleThrowMechanics();
        movePlayer();
        moveSpears();
	}

	private void drawSprites() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Sprite w : walls) {
            w.draw(batch);
        }
        batch.draw(playerImage, player.getX(), player.getY());

        for (Spear s : spears) {
            s.draw(batch);
        }
        batch.end();
    }

	private void handleThrowMechanics() {
        rightStick.x = rightXAxisValue;
        rightStick.y = -rightYAxisValue;

        if (player.hasASpear()) {
            if (!chargingThrow) {
                if (!(rightStick.x == 0 && rightStick.y == 0)) {
                    chargingThrow = true;
                }
            } else {
                if (!(rightStick.x == 0 && rightStick.y == 0)) {
                    throwSpeed += 500 * Gdx.graphics.getDeltaTime();
                    if (throwSpeed > MAX_THROW_SPEED) {
                        throwSpeed = MAX_THROW_SPEED;
                    }
                } else {
                    throwing = true;
                    chargingThrow = false;
                    player.setSpear(null);
                }
            }
        }

    }

	private void movePlayer() {
        player.accelerateX(leftXAxisValue);
        player.accelerateY(leftYAxisValue);
        player.moveX(walls);
        player.moveY(walls);
    }

    private void moveSpears() {
        for (Spear s : spears) {
            if (player.hasSpear(s) && chargingThrow) {
                s.setRotation(rightStick.angle() + 180);
            }
            else {
                if (throwing) {
                    s.setSpeed(throwSpeed);
                    s.setWasThrown(true);
                    s.setRotation(s.getRotation());
                    throwing = false;
                    throwSpeed = 50;
                }
            }
            if (s.getWasThrown()) {
                s.move(walls);
            }
            if (!s.getWasThrown() && player.hasSpear(s)) {
                s.setPosition(
                        (player.getX() ),
                        (player.getY() + s.getHeight())
                );
            }
            if (!s.getWasThrown() && !player.hasASpear()) {
                if (s.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                    player.setSpear(s);
                }
            }
        }
    }
	
	@Override
	public void dispose () {
		batch.dispose();
		playerImage.dispose();
		spearImage.dispose();
		wallImage.dispose();
	}
}
