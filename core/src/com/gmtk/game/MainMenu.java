package com.gmtk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.gmtk.game.dialog.DialogManager;
import com.gmtk.game.input.DialogKeyboardInputProcessor;
import com.gmtk.game.input.MapKeyboardInputProcessor;
import com.gmtk.game.input.MenuKeyboardInputProcessor;
import com.gmtk.game.input.OverworldKeyboardInputProcessor;
import com.gmtk.game.map.MapManager;
import com.gmtk.game.overworld.OverworldManager;

public class MainMenu implements Screen {
    final TheGame game;
    private Texture menuImage;
    private DialogManager dialogManager;
    private MapManager mapManager;
    private OverworldManager overworldManager;

    public MainMenu(final TheGame game) {
        dialogManager = new DialogManager();
        dialogManager.loadBlockOfText();
        mapManager = new MapManager();
        overworldManager = new OverworldManager();
        menuImage = new Texture(Gdx.files.internal("titlescreen.png"));
        this.game = game;
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new DialogKeyboardInputProcessor(dialogManager));
        multiplexer.addProcessor(new MapKeyboardInputProcessor(mapManager));
        multiplexer.addProcessor(new MenuKeyboardInputProcessor());
        multiplexer.addProcessor(new OverworldKeyboardInputProcessor(overworldManager));
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        overworldManager.render();
//        mapManager.render();
//        dialogManager.render();
//        game.batch.begin();
//        game.batch.draw(menuImage, 0, 0);
        //game.font.draw(game.batch, "PRESS START TO PLAY", 800, 450);
//        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (!dialogManager.anyDialogToRender()) {
                Util.betterLog("SWITCHING", "SCREEN");
                game.setScreen(new GameScreen(game));
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
        dialogManager.dispose();
    }
}
