package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class MainMenu implements Screen {
    final TheGame game;
    private Texture menuImage;
    private TextRenderer textRenderer;
    private String textToRender;
    private Array<String> blockOfText;
    private int textBlockPointer;

    public MainMenu(final TheGame game) {
        textBlockPointer = 0;
        blockOfText = new Array<String>();
        blockOfText.add(String.join(
                "\n",
                "The veteran... did not have it in him.",
                "The only memory of today will be the audience's",
                "laughs at his pitiful performance."
        ));
        blockOfText.add(String.join(
                "\n",
                "It was the best of times, it was the worst of times.",
                "It was the age of wisdom, it was the age of foolishness.",
                "It was the epoch of belief, it was the epoch of incredulity.",
                "It was the season of Light, it was the season of Darkness.",
                "It was the spring of hope, it was the winter of despair.",
                "We had everything before us, we had nothing before us.",
                "We were all going direct to heaven, we were all going direct the other way - in short, the period was so far like the present period, that some of its noisiest authorities insisted on its being received, for good or for evil, in the superlative degree of comparison only."
        ));
        textToRender = blockOfText.get(textBlockPointer);
        menuImage = new Texture(Gdx.files.internal("titlescreen.png"));
        this.game = game;
        this.textRenderer = new TextRenderer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        textRenderer.renderBlockOfText(textToRender);
//        game.batch.begin();
//        game.batch.draw(menuImage, 0, 0);
        //game.font.draw(game.batch, "PRESS START TO PLAY", 800, 450);
//        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (textRenderer.advanceToEndOfBlock()) {
                textBlockPointer++;
                if (blockOfText.size > textBlockPointer)
                    textToRender = blockOfText.get(textBlockPointer);
                else
                    textToRender = null;
            }

        }
//        Controllers.addListener(new ControllerAdapter() {
//            @Override
//            public boolean buttonUp(Controller controller, int buttonCode) {
////                Gdx.app.log("Controller: " + controller.getName(), "button pressed: " + buttonCode);
//                if (buttonCode == 7 && !(game.getScreen() instanceof GameScreen)) {
////                    game.setScreen(new GameScreen(game));
////                    GameScreen.score = 0;
////                    dispose();
//                }
//                if (buttonCode == 1) {
//                    textToRender = null;
//                }
//                return false;
//            }
//        });
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
        menuImage.dispose();
        textRenderer.dispose();
    }
}
