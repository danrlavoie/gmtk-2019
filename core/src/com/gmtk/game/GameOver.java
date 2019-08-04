package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;

public class GameOver implements Screen {
    final TheGame game;

    public GameOver(final TheGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(
            game.batch,
            "PRESS START TO PLAY AGAIN",
            700,
            450
        );
        game.font.draw(
            game.batch,
            "Gladiator sprites originally by Elthen    https://elthen.itch.io/2d-pixel-art-gladiator-sprites",
            400,
            250
        );
        game.font.draw(
                game.batch,
                "Ambient crowd sounds originally by Signature Label    https://www.reddit.com/r/gamedev/comments/bo4koq/hi_gamedev_i_recorded_the_sounds_of_a_large_crowd/",
                400,
                225
        );
        game.font.draw(
                game.batch,
                "SFX originally by BBC Sound Effects    http://bbcsfx.acropolis.org.uk",
                400,
                200
        );
        game.batch.end();
        GameScreen.score = 0;
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
