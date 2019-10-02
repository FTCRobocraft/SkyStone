package org.firstinspires.ftc.teamcode.playmaker;

public class GamepadListener {

    enum GamepadListenerType {
        TOGGLE,
        PRESS,
        AUTO_TRIGGER
    }

    public GamepadController.GamepadType type;
    public GamepadController.GamepadButtons button;
    private GamepadListenerType listenerType;

    public GamepadInterface onActivateInterface;
    public GamepadInterface onDeactivateInterface;

    private GamepadListener(GamepadListenerType type, GamepadController.GamepadType gamepadType, GamepadController.GamepadButtons button) {
        this.listenerType = type;
        this.type = gamepadType;
        this.button = button;
    }

    public static GamepadListener createPressListener(GamepadController.GamepadType gamepadType, GamepadController.GamepadButtons button, GamepadInterface onActivateInterface, GamepadInterface onDeactivateInterface) {
        GamepadListener listener = new GamepadListener(GamepadListenerType.PRESS, gamepadType, button);
        listener.onActivateInterface = onActivateInterface;
        listener.onDeactivateInterface = onDeactivateInterface;
        return listener;
    }

    public static GamepadListener createToggleListener(GamepadController.GamepadType gamepadType, GamepadController.GamepadButtons button, GamepadInterface onActivateInterface, GamepadInterface onDeactivateInterface) {
        GamepadListener listener = new GamepadListener(GamepadListenerType.TOGGLE, gamepadType, button);
        listener.onActivateInterface = onActivateInterface;
        listener.onDeactivateInterface = onDeactivateInterface;
        return listener;
    }

    public static GamepadListener createAutoTrigger(GamepadController.GamepadType gamepadType, GamepadController.GamepadButtons button, HybridOp hybridOp, ActionSequence sequence) {
        GamepadListener listener = new GamepadListener(GamepadListenerType.AUTO_TRIGGER, gamepadType, button);
        listener.onActivateInterface = () -> hybridOp.executeActionSequence(sequence);
        listener.onDeactivateInterface = () -> hybridOp.stopAutonomous();
        return listener;
    }

    public GamepadListenerType getListenerType() {
        return listenerType;
    }

}
