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
		player = new Player(playerImage);

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
	    for (Spear s : spears) {
	        s.setPosition(player.getX(), player.getY());
	        s.setWasThrown(false);
        }
	    throwing = false;
	    chargingThrow = false;
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(playerImage, player.getX(), player.getY());

		for (Spear s : spears) {
		    s.draw(batch);
        }
		for (Sprite w : walls) {
			w.draw(batch);
		}
		batch.end();

		player.accelerateX(leftXAxisValue);
		player.accelerateY(leftYAxisValue);
		player.moveX();
		player.moveY();
		rightStick.x = rightXAxisValue;
		rightStick.y = -rightYAxisValue;

		if (!chargingThrow) {
		    if (!(rightStick.x == 0 && rightStick.y == 0)) {
		        chargingThrow = true;
            }
        }
		else {
            if (!(rightStick.x == 0 && rightStick.y == 0)) {
                throwSpeed += 500 * Gdx.graphics.getDeltaTime();
                if (throwSpeed > MAX_THROW_SPEED) {
                    throwSpeed = MAX_THROW_SPEED;
                }
            }
            else {
                throwing = true;
                chargingThrow = false;
            }
        }

		for (Spear s : spears) {
		    if (!(rightStick.x == 0 && rightStick.y == 0)) {
                s.setRotation(rightStick.angle());
            }
		    else {
		        if (throwing) {
		            s.setSpeed(throwSpeed);
		            s.setWasThrown(true);
		            s.setRotation(s.getRotation() + 180);
		            throwing = false;
		            throwSpeed = 0;
                }
            }
		    if (s.getWasThrown()) {
		        s.move();
            }
		    else {
                s.setPosition(player.getX(), player.getY());
            }
//		    Gdx.app.log("SPEAR ANGLE: ", Float.toString(s.getRotation()));
        }

		for (Sprite w : walls) {
			if (player.getBoundingRectangle().overlaps(w.getBoundingRectangle())) {
				Gdx.app.log("COLLISION: ", "wall");
			}
		}
//		Gdx.app.log("THROW SPEED: ", Float.toString(throwSpeed));
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerImage.dispose();
		spearImage.dispose();
		wallImage.dispose();
	}
}
