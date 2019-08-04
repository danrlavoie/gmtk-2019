package com.gmtk.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GMTKGame extends ApplicationAdapter {
    Renderer renderer;

	Player player;
	GameController gameController;

	private Array<Spear> spears;
	private Array<Sprite> walls;
	private Array<Enemy> enemies;
	private static int enemyCount = 0;

	final public static int CANVAS_WIDTH = 1600;
	final public static int CANVAS_HEIGHT = 900;

	public float leftXAxisValue, rightXAxisValue;
	public float leftYAxisValue, rightYAxisValue;

	public Vector2 rightStick;
	public float throwSpeed;
	final public static int MAX_THROW_SPEED = 700;

	public float leftStickDebounce;
	public float rightStickDebounce;
	
	@Override
	public void create () {
	    renderer = new Renderer();


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

		spears = new Array<Spear>();
		spawnSpear();
        player = new Player(renderer.playerImage, spears.first());

		walls = new Array<Sprite>();
		spawnWalls();

		enemies = new Array<Enemy>();
		spawnEnemy();
	}

	private void spawnSpear() {
	    Spear spear = new Spear(renderer.spearImage);
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

	public void spawnEnemy() {
	    // playerImage is a placeholder for a 64x64 sprite
		enemyCount++;
	    Enemy e = new Enemy(renderer.playerImage, ActorClass.ENEMY, null, enemyCount);
        float x = player.getX();
        float y = player.getY();
        while (Vector2.dst(x, y, player.getX(), player.getY()) < 200) {
            x = MathUtils.random(300, 1250);
            y = MathUtils.random(64, 780);
        }
        e.setPosition(x, y);
        enemies.add(e);
    }

    public void resetSpear() {
	    Spear s = spears.first();
	    s.setPosition(player.getX(), player.getY());
	    s.setWasThrown(false);
	    player.setSpear(s);

	    player.setThrowing(false);
	    player.setChargingThrow(false);
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        renderer.drawSprites(player, spears, walls, enemies);

		handleThrowMechanics();
        movePlayer();
        moveSpears();
        moveEnemies();
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
                if (player.isThrowing()) {
                    s.setSpeed(throwSpeed);
                    s.setWasThrown(true);
                    s.setRotation(s.getRotation());
                    player.setThrowing(false);
                    throwSpeed = 50;
                }
            }
            if (s.getWasThrown()) {
                s.move(walls);
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
                }
            }
        }
    }

    private void moveEnemies() {
//		Gdx.app.log("ENEMIES: ",enemies.toString());
		for (int i = 0; i < enemies.size; i++) {
			Enemy e = enemies.get(i);
			Gdx.app.log(Integer.toString(e.getId()), "Movan");

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
	}
}
