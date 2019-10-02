package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.TestHardware;
import org.firstinspires.ftc.teamcode.playmaker.GamepadController;

@TeleOp(name="TestMotor")
public class TestMotor extends TestHardware {

    GamepadController gamepadController = new GamepadController(gamepad1, gamepad2);

    @Override
    public void init() {
        gamepadController.addButtonToggleListener(GamepadController.GamepadType.ONE, GamepadController.GamepadButtons.A, () -> {
            transferMotor.setPower(1);
        });
    }

    @Override
    public void loop() {
        gamepadController.controllerLoop();
    }
}