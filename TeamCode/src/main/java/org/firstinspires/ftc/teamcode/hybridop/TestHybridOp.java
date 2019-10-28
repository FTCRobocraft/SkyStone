package org.firstinspires.ftc.teamcode.hybridop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.action.WaitAction;
import org.firstinspires.ftc.teamcode.hardware.TestHardware;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.playmaker.GamepadController;
import org.firstinspires.ftc.teamcode.playmaker.GamepadListener;
import org.firstinspires.ftc.teamcode.playmaker.HybridOp;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

@TeleOp(name="Spinner", group="HybridOp")
public class TestHybridOp extends HybridOp {

    OmniDrive omniDrive;

    public TestHybridOp() {
        super();
        this.hardware = new TestHardware(this);
    }


    @Override
    public void init() {
        super.init();
        TestHardware testHardware = (TestHardware) this.hardware;
        omniDrive = (OmniDrive) this.omniDrive;

        /*
        gamepadController.addGamepadListener(GamepadListener.createHoldAndReleaseListener(GamepadController.GamepadType.ONE, GamepadController.GamepadButtons.a, () -> {
            testHardware.leftMotor.setPower(1);
        }, ()-> {
            testHardware.leftMotor.setPower(0);
        }));

        gamepadController.addGamepadListener(GamepadListener.createHoldAndReleaseListener(GamepadController.GamepadType.ONE, GamepadController.GamepadButtons.b, () -> {
            testHardware.leftMotor.setPower(-1);
        }, ()-> {
            testHardware.leftMotor.setPower(0);
        }));
        */

    }

    @Override
    public void loop() {

        omniDrive.circleMove(gamepad1.left_stick_x, gamepad1.left_stick_y);

    }
}
