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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GMTKGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerImage;
	Texture spearImage;
	Texture wallImage;

	Animation<TextureRegion> idleAnimation;
	Animation<TextureRegion> walkAnimation;
	Animation<TextureRegion> throwAnimation;
	Animation<TextureRegion> hurtAnimation;
	Animation<TextureRegion> dieAnimation;
	Animation<TextureRegion> currentAnimation;
	Texture playerSheet;

	float animationTime;

	Player player;
	GameController gameController;

	private Array<Spear> spears;
	private Array<Sprite> walls;

	final public static int CANVAS_WIDTH = 1600;
	final public static int CANVAS_HEIGHT = 900;

	public float leftXAxisValue, rightXAxisValue;
	public float leftYAxisValue, rightYAxisValue;

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
		playerSheet = new Texture(Gdx.files.internal("player.png"));

		TextureRegion[][] tmp = TextureRegion.split(playerSheet, 64, 64);
		TextureRegion[] idleFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			idleFrames[i] = tmp[0][i];
		}
		TextureRegion[] walkFrames = new TextureRegion[8];
		for (int i = 0; i < 8; i++) {
			walkFrames[i] = tmp[1][i];
		}
		TextureRegion[] throwFrames = new TextureRegion[7];
		for (int i = 0; i < 7; i++) {
			throwFrames[i] = tmp[2][i];
		}
		TextureRegion[] hurtFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			hurtFrames[i] = tmp[3][i];
		}
		TextureRegion[] dieFrames = new TextureRegion[7];
		for (int i = 0; i < 7; i++) {
			dieFrames[i] = tmp[4][i];
		}

		idleAnimation = new Animation<TextureRegion>(0.1f, idleFrames);
		walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);
		throwAnimation = new Animation<TextureRegion>(0.1f, throwFrames);
		hurtAnimation = new Animation<TextureRegion>(0.1f, hurtFrames);
		dieAnimation = new Animation<TextureRegion>(0.1f, dieFrames);
		currentAnimation = idleAnimation;
		animationTime = 0f;


		batch = new SpriteBatch();

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

	    player.setThrowing(false);
	    player.setChargingThrow(false);
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		animationTime += Gdx.graphics.getDeltaTime();

        drawSprites();

		handleThrowMechanics();
        movePlayer();
        moveSpears();
	}

	private void drawSprites() {
        batch.begin();
        for (Sprite w : walls) {
            w.draw(batch);
        }
        if (
        		(player.isChargingThrow() || player.isThrowing())
		) {
        	player.setCurrentState(Player.PlayerState.THROWING);
		}
        else if (
        		player.isMoving()
		) {
        	player.setCurrentState(Player.PlayerState.WALKING);
		}
        else {
        	player.setCurrentState(Player.PlayerState.IDLE);
		}
		switch(player.getCurrentState()) {
			case WALKING:
				currentAnimation = walkAnimation;
				break;
			case HURT:
				currentAnimation = hurtAnimation;
				break;
			case DYING:
				currentAnimation = dieAnimation;
				break;
			case THROWING:
				currentAnimation = throwAnimation;
				break;
			default:
				currentAnimation = idleAnimation;
		}
        TextureRegion currentFrame;
        if (player.isChargingThrow()) {
        	// Charging throw, hold on the first animation frame
			currentFrame = currentAnimation.getKeyFrames()[0];
			animationTime -= Gdx.graphics.getDeltaTime();
		}
        else {
			currentFrame = currentAnimation.getKeyFrame(animationTime, false);
		}
        if (currentAnimation.isAnimationFinished(animationTime)) {
        	animationTime = 0f;
		}
        boolean flip = (player.isMovingRight());
		batch.draw(
				currentFrame,
				flip ? player.getX() + player.getWidth() : player.getX(),
				player.getY(),
				flip ? -player.getWidth() : player.getWidth(),
				player.getHeight()
		);
        for (Spear s : spears) {
            s.draw(batch);
        }
        batch.end();
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
                    throwSpeed += 500 * Gdx.graphics.getDeltaTime();
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
	
	@Override
	public void dispose () {
		batch.dispose();
		playerImage.dispose();
		playerSheet.dispose();
		spearImage.dispose();
		wallImage.dispose();
	}
}
