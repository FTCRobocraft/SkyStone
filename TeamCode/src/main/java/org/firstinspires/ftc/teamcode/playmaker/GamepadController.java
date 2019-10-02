package org.firstinspires.ftc.teamcode.playmaker;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.HashMap;
import java.util.Map;

public class GamepadController {

    private Gamepad gamepad1;
    private Gamepad gamepad2;
    private Map<GamepadButtons, GamepadListener> gamepad1Listeners = new HashMap<>();
    private Map<GamepadButtons, GamepadListener> gamepad2Listeners = new HashMap<>();

    public GamepadController(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }

    public enum GamepadType {
        ONE,
        TWO
    }

    public enum GamepadButtons {
        A,
        B,
        X,
        Y,
        LEFT_STICK,
        LEFT_BUMPER,
        RIGHT_STICK,
        RIGHT_BUMPER,
        DPAD_UP,
        DPAD_LEFT,
        DPAD_RIGHT,
        DPAD_DOWN,
        GUIDE,
        START
    }

    public void addGamepadListener(GamepadListener listener) {
        switch (listener.type) {
            case ONE:
                gamepad1Listeners.put(listener.button, listener);
                break;
            case TWO:
                gamepad2Listeners.put(listener.button, listener);
                break;
        }
    }

    public void clearAllListeners() {

    }

    public void controllerLoop(Boolean autonomousMode) {
        if (gamepad1.a) {

        }

        if (gamepad1.b) {

        }

        if (gamepad1.x) {

        }

        if (gamepad1.y) {

        }

        if (gamepad1.left_bumper) {

        }
    }
}
