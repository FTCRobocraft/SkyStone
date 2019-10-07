package org.firstinspires.ftc.teamcode.playmaker;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.lang.reflect.Field;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GamepadController {

    private Gamepad gamepad1;
    private Gamepad gamepad2;

    private Map<GamepadButtons, GamepadListener> gamepad1Listeners = new HashMap<>();
    private Map<GamepadButtons, GamepadListener> gamepad2Listeners = new HashMap<>();
    private Map<GamepadButtons, Boolean> gamepad1Pressed = new HashMap<>();
    private Map<GamepadButtons, Boolean> gamepad2Pressed = new HashMap<>();
    private Map<GamepadButtons, Boolean> gamepad1Toggled = new HashMap<>();
    private Map<GamepadButtons, Boolean> gamepad2Toggled = new HashMap<>();

    public enum GamepadButtons {
            a,
            b,
            x,
            y,
            dpad_up,
            dpad_down,
            dpad_left,
            dpad_right,
            start,
            back,
            left_bumper,
            right_bumper,
            left_stick_button,
            right_stick_button
    };

    public GamepadController(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        for (int i=0;i<GamepadButtons.values().length;i++) {
            GamepadButtons button = GamepadButtons.values()[i];
            gamepad1Pressed.put(button, false);
            gamepad2Pressed.put(button, false);
            gamepad1Toggled.put(button, false);
            gamepad1Toggled.put(button, false);
        }
    }

    public enum GamepadType {
        ONE,
        TWO
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


    public void controllerLoop(Boolean autonomousMode) {
        for (int i=0; i<GamepadButtons.values().length; i++) {
            GamepadButtons button = GamepadButtons.values()[i];
            try {
                Field buttonField = Gamepad.class.getField(button.toString());
                boolean one = buttonField.getBoolean(gamepad1);
                boolean two = buttonField.getBoolean(gamepad2);
                boolean isOneFirstPress = one && !gamepad1Pressed.get(button);
                boolean isTwoFirstPress = two && !gamepad2Pressed.get(button);
                gamepad1Pressed.put(button, one);
                gamepad2Pressed.put(button, two);
                GamepadListener listenerOne = gamepad1Listeners.get(button);
                GamepadListener listenerTwo = gamepad2Listeners.get(button);

                if (listenerOne != null) {
                    switch (listenerOne.getListenerType()) {
                        case TOGGLE:
                            if (!autonomousMode && isOneFirstPress) {
                                if (!gamepad1Toggled.get(button)) {
                                    listenerOne.activateInterface.execute();
                                } else {
                                    listenerOne.deactivateInterface.execute();
                                }
                                gamepad1Toggled.put(button, !gamepad1Toggled.get(button));
                            }
                            break;
                        case PRESS:
                            if (!autonomousMode && isOneFirstPress) listenerOne.activateInterface.execute();
                            break;
                        case AUTO_TRIGGER:
                            if (one) listenerOne.activateInterface.execute();
                            break;
                        case HOLD_RELEASE:
                            if (!autonomousMode && one) {
                                listenerOne.activateInterface.execute();
                            } else if (!autonomousMode && !one) {
                                listenerOne.deactivateInterface.execute();
                            }
                            break;
                    }
                }

                if (listenerTwo != null) {
                    switch (listenerTwo.getListenerType()) {
                        case TOGGLE:
                            if (!autonomousMode && isTwoFirstPress) {
                                if (!gamepad2Toggled.get(button)) {
                                    listenerTwo.activateInterface.execute();
                                } else {
                                    listenerTwo.deactivateInterface.execute();
                                }
                                gamepad2Toggled.put(button, !gamepad2Toggled.get(button));
                            }
                            break;
                        case PRESS:
                            if (!autonomousMode && isTwoFirstPress) listenerTwo.activateInterface.execute();
                            break;
                        case AUTO_TRIGGER:
                            if (two) listenerTwo.activateInterface.execute();
                            break;
                        case HOLD_RELEASE:
                            if (!autonomousMode && two) {
                                listenerTwo.activateInterface.execute();
                            } else if (!autonomousMode && !two) {
                                listenerTwo.deactivateInterface.execute();
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
