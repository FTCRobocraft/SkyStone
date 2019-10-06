package org.firstinspires.ftc.teamcode.playmaker;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class HybridOp extends OpMode {

    public GamepadController gamepadController;
    public RobotHardware hardware;
    private ActionExecutor actionExecutor;
    private boolean isAutonomous = false;

    @Override
    public void init() {
        hardware.initializeHardware();
        gamepadController = new GamepadController(gamepad1, gamepad2);
    }

    @Override
    public void loop() {
        hardware.hardware_loop();
        gamepadController.controllerLoop(isAutonomous);

        if (isAutonomous) {
            this.autonomous_loop();
        } else {
            this.teleop_loop();
        }
    }

    public boolean isAutonomous() {
        return isAutonomous;
    }

    public void executeActionSequence(ActionSequence sequence) {
        this.actionExecutor = new ActionExecutor(false, false, sequence);
        this.isAutonomous = true;
    }

    public void stopAutonomous() {
        this.isAutonomous = false;
        this.actionExecutor = null;
    }

    public void autonomous_loop() {
        // The Action Executor will return true once it's done. Therefore, when it eventually
        // is true, it'll set the isAutonomous variable to false, which will exit out of
        // autonomous.
        isAutonomous = !this.actionExecutor.loop();

    }

    public void teleop_loop() { }


}
