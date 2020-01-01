package org.firstinspires.ftc.teamcode.hybridop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.action.LiftMotorAction;
import org.firstinspires.ftc.teamcode.action.ReleaseCapstoneAction;
import org.firstinspires.ftc.teamcode.action.ToggleGripAction;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.playmaker.GamepadController.GamepadType;
import org.firstinspires.ftc.teamcode.playmaker.GamepadController.GamepadButtons;
import org.firstinspires.ftc.teamcode.playmaker.GamepadListener;
import org.firstinspires.ftc.teamcode.playmaker.HybridOp;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

@TeleOp(name="TeleOp")
public class SkystoneHybridOp extends HybridOp {

    private static final float DPAD_POWER = 1f;
    boolean reverseMode = false;
    private SkyStoneRobotHardware skyStoneRobotHardware;

    @Override
    public void init() {
        super.init();

        //region Gamepad 1

        gamepadController.addGamepadListener(GamepadListener.createToggleListener(
                GamepadType.ONE, GamepadButtons.a,
                () -> {
                    reverseMode = true;
                }, () -> {
                    reverseMode = false;
                }));

        //endregion

        // region Gamepad 2

        // Toggle Grip
        gamepadController.addGamepadListener(GamepadListener.createAutoTrigger(
                GamepadType.TWO, GamepadButtons.a,
                this,
                new ActionSequence(new ToggleGripAction())
        ));

        // Lift Motor to Start
        gamepadController.addGamepadListener(GamepadListener.createAutoTrigger(
                GamepadType.TWO, GamepadButtons.x,
                this,
                new ActionSequence(new LiftMotorAction(0))
        ));

        // Lift Motor to Raised
        gamepadController.addGamepadListener(GamepadListener.createAutoTrigger(
                GamepadType.TWO, GamepadButtons.y,
                this,
                new ActionSequence(new LiftMotorAction(SkyStoneRobotHardware.LIFT_RAISED))
        ));

        // Move lift arm up
        gamepadController.addGamepadListener(GamepadListener.createHoldAndReleaseListener(
                GamepadType.TWO, GamepadButtons.dpad_up,
                () -> {
                    skyStoneRobotHardware.setLiftPower(1);
                }, () -> {
                    skyStoneRobotHardware.setLiftPower(0);
                }));

        // Move lift arm down
        gamepadController.addGamepadListener(GamepadListener.createHoldAndReleaseListener(
                GamepadType.TWO, GamepadButtons.dpad_down,
                () -> {
                    skyStoneRobotHardware.setLiftPower(-1);
                }, () -> {
                    skyStoneRobotHardware.setLiftPower(0);
                }));

        // Move horizontal grip inwards
        gamepadController.addGamepadListener(GamepadListener.createHoldAndReleaseListener(
                GamepadType.TWO, GamepadButtons.dpad_left,
                () -> {
                    skyStoneRobotHardware.setHorizontalGripPower(-1);
                }, () -> {
                    skyStoneRobotHardware.setHorizontalGripPower(0);
                }));

        // Move horizontal grip outwards
        gamepadController.addGamepadListener(GamepadListener.createHoldAndReleaseListener(
                GamepadType.TWO, GamepadButtons.dpad_right,
                () -> {
                    skyStoneRobotHardware.setHorizontalGripPower(1);
                }, () -> {
                    skyStoneRobotHardware.setHorizontalGripPower(0);
                }));

        // Release capstone
        gamepadController.addGamepadListener(GamepadListener.createAutoTrigger(
                GamepadType.TWO, GamepadButtons.back,
                this,
                new ActionSequence(new ReleaseCapstoneAction())
                ));

        //endregion

    }

    @Override
    public RobotHardware getHardware() {
        skyStoneRobotHardware = new SkyStoneRobotHardware(this);
        return skyStoneRobotHardware;
    }

    @Override
    public void hybrid_loop() {

    }

    @Override
    public void teleop_loop() {
        skyStoneRobotHardware.setHorizontalGripPower(gamepad1.right_stick_y);
        skyStoneRobotHardware.omniDrive.stopDrive();
        if (gamepad1.left_trigger != 0) {
            skyStoneRobotHardware.omniDrive.rotateLeft(gamepad1.left_trigger);
        } else if (gamepad1.right_trigger != 0) {
            skyStoneRobotHardware.omniDrive.rotateRight(gamepad1.right_trigger);
        } else {
            skyStoneRobotHardware.omniDrive.dpadMove(gamepad1, DPAD_POWER, reverseMode);
        }
    }

}