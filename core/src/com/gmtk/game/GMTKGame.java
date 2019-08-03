package com.gmtk.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GMTKGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture playerImage;
	Player player;
	OrthographicCamera camera;
	GameController gameController;

	final public static int CANVAS_WIDTH = 1600;
	final public static int CANVAS_HEIGHT = 900;

	public float xAxisValue;
	public float yAxisValue;
	
	@Override
	public void create () {
		playerImage = new Texture(Gdx.files.internal("bucket.png"));
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CANVAS_WIDTH, CANVAS_HEIGHT);
		createPlayer();

		gameController = new GameController(this);
		Gdx.app.log("CONTROLLERS", Controllers.getControllers().toString());
		Controllers.addListener(gameController);
		xAxisValue = 0;
		yAxisValue = 0;
	}

	private void createPlayer() {
		player = new Player();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(playerImage, player.getX(), player.getY());
		batch.end();

		player.accelerateX(xAxisValue);
		player.accelerateY(yAxisValue);
		player.moveX();
		player.moveY();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
