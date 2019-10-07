package org.firstinspires.ftc.teamcode.hybridop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.TestHardware;
import org.firstinspires.ftc.teamcode.playmaker.GamepadController;
import org.firstinspires.ftc.teamcode.playmaker.GamepadListener;
import org.firstinspires.ftc.teamcode.playmaker.HybridOp;

@TeleOp(name="Spinner", group="HybridOp")
public class TestHybridOp extends HybridOp {

    public TestHybridOp() {
        super();
        this.hardware = new TestHardware(this);
    }

    @Override
    public void init() {
        super.init();
        TestHardware testHardware = (TestHardware) this.hardware;
        gamepadController.addGamepadListener(GamepadListener.createToggleListener(GamepadController.GamepadType.ONE, GamepadController.GamepadButtons.a, () -> {
            testHardware.leftMotor.setPower(-1);
            testHardware.rightMotor.setPower(1);
        }, () -> {
            testHardware.leftMotor.setPower(0);
            testHardware.rightMotor.setPower(0);
        }));
    }
}
