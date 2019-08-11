package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class VictoryScreen implements Screen {
    final TheGame game;

    public VictoryScreen(final TheGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        String message = String.join(
                "\n",
                "The veteran fought and fought, until the floor",
                "was stained red and the remaining opponents asked",
                "not to be forced into the arena. He showed his",
                "mastery of the spear, demonstrated his might",
                "and finally won his freedom, at last."
        );
        game.batch.begin();
        game.font.draw(
                game.batch,
                message,
                700,
                450
        );
        game.font.draw(
                game.batch,
                "(Press START to play again)",
                700,
                300
        );
        game.font.draw(
                game.batch,
                "Gladiator sprites originally by Elthen    https://elthen.itch.io/2d-pixel-art-gladiator-sprites",
                400,
                150
        );
        game.font.draw(
                game.batch,
                "Ambient crowd sounds originally by Signature Label    https://www.reddit.com/r/gamedev/comments/bo4koq/hi_gamedev_i_recorded_the_sounds_of_a_large_crowd/",
                400,
                125
        );
        game.font.draw(
                game.batch,
                "SFX originally by BBC Sound Effects    http://bbcsfx.acropolis.org.uk",
                400,
                100
        );
        game.font.draw(
                game.batch,
                "Game by Dan Lavoie, (c)2019",
                400,
                75
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
