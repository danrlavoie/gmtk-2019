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
        Gdx.gl.glClearColor(0.4f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        String message = "";
        if (GameScreen.score < 3) {
            message = String.join(
                    "\n",
                    "The veteran... did not have it in him.",
                    "The only memory of today will be the audience's",
                    "laughs at his pitiful performance."
            );
        } else if (GameScreen.score < 8) {
            message = String.join(
                    "\n",
                    "The veteran's fight was slightly disappointing,",
                    "for an audience that had seen many fights",
                    "over the years. It made them sad to see the",
                    "once-proud warrior fall to his knees."
            );
        } else if (GameScreen.score < 15) {
            message = String.join(
                    "\n",
                    "The veteran summoned his strength and delivered",
                    "a vintage performance for the crowd. Dodging and weaving,",
                    "he struck down many foes before succumbing at last, in the",
                    "heat of battle."
            );
        } else if (GameScreen.score < 25) {
            message = String.join(
                    "\n",
                    "It was a tremendous day for the veteran to die.",
                    "He was a flash of fury in the arena, taking down over",
                    "one hundred men before breathing his last. His name",
                    "will be enshrined for all history within this arena."
            );
        }
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
