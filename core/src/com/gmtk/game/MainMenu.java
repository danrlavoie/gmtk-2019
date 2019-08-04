package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;

public class MainMenu implements Screen {
    final TheGame game;

    public MainMenu(final TheGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "PRESS START TO PLAY", 800, 450);
        game.batch.end();

        Controllers.addListener(new ControllerAdapter() {
            @Override
            public boolean buttonUp(Controller controller, int buttonCode) {
                Gdx.app.log("Controller: " + controller.getName(), "button pressed: " + buttonCode);
                if (buttonCode == 7 && !(game.getScreen() instanceof GameScreen)) {
                    game.setScreen(new GameScreen(game));
                    dispose();
                }
                return false;
            }
        });
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    @Override
    public void dispose() {
    }
}