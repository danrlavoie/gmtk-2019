package com.gmtk.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class GameController implements ControllerListener {
    private GameScreen game;
    public GameController(GameScreen game) {
        super();
        this.game = game;
    }
    @Override
    public void connected(Controller controller) {
        Gdx.app.log("Controller connected", controller.getName());
    }

    @Override
    public void disconnected(Controller controller) {
        Gdx.app.log("Controller disconnected", controller.getName());
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
//        Gdx.app.log("Controller: " + controller.getName(), "button pressed: " + buttonCode);
        if (buttonCode == 0) {
//            game.resetSpear();
        }
        if (buttonCode == 1) {
//            game.spawnEnemy('E');
        }
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {

        if (Math.abs(value) < 0.18) {
            value = 0;
        }
        if (axisCode == 0) {
            game.leftYAxisValue = value;
        }
        else if (axisCode == 1) {
            game.leftXAxisValue = value;
        }
        else if (axisCode == 3) {
            game.rightXAxisValue = value;
        }
        else if (axisCode == 2) {
            game.rightYAxisValue = value;
        }
//        Gdx.app.log("CONTROLLER: ", "axis moved");
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}