package org.firstinspires.ftc.teamcode.hybridop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.action.LiftMotorAction;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.playmaker.GamepadController.GamepadButtons;
import org.firstinspires.ftc.teamcode.playmaker.GamepadController.GamepadType;
import org.firstinspires.ftc.teamcode.playmaker.GamepadListener;
import org.firstinspires.ftc.teamcode.playmaker.HybridOp;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

@TeleOp(name="TeleOp")
public class SkystoneHybridOp extends HybridOp {

    private static final float DPAD_POWER = 1f;
    private static final float SLOW_DPAD_POWER = 0.4f;
    private static final float LIFT_POWER = 0.75f;
    boolean reverseMode = false;
    boolean slowMode = true;
    private SkyStoneRobotHardware skyStoneRobotHardware;

    @Override
    public void init() {
        super.init();

        //region Gamepad 1

        gamepadController.addGamepadListener(GamepadListener.createHoldAndReleaseListener(
                GamepadType.ONE, GamepadButtons.b,
                () -> {
                    slowMode = false;
                }, () -> {
                    slowMode = true;
                }
        ));

        gamepadController.addGamepadListener(GamepadListener.createToggleListener(
                GamepadType.ONE, GamepadButtons.x,
                () -> {
                    skyStoneRobotHardware.raisePlatformGrip();
                }, () -> {
                    skyStoneRobotHardware.lowerPlatformGrip();
                }
        ));

        //endregion

        // region Gamepad 2

        // Toggle Grip
        gamepadController.addGamepadListener(GamepadListener.createToggleListener(
                GamepadType.TWO, GamepadButtons.a,
                () ->  {
                    skyStoneRobotHardware.gripServo.setPosition(1);
                }, () -> {
                    skyStoneRobotHardware.gripServo.setPosition(0);
                }
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
                    skyStoneRobotHardware.setLiftPower(LIFT_POWER);
                }, () -> {
                    skyStoneRobotHardware.setLiftPower(skyStoneRobotHardware.LIFT_STANDBY_POWER);
                }));

        // Move lift arm down
        gamepadController.addGamepadListener(GamepadListener.createHoldAndReleaseListener(
                GamepadType.TWO, GamepadButtons.dpad_down,
                () -> {
                    skyStoneRobotHardware.setLiftPower(-LIFT_POWER);
                }, () -> {
                    skyStoneRobotHardware.setLiftPower(skyStoneRobotHardware.LIFT_STANDBY_POWER);
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
        gamepadController.addGamepadListener(GamepadListener.createHoldAndReleaseListener(
                GamepadType.TWO, GamepadButtons.back,
                () ->  {
                    skyStoneRobotHardware.capGrip = true;
                }, () -> {
                    skyStoneRobotHardware.capGrip = false;
                }
        ));

        gamepadController.addGamepadListener(GamepadListener.createHoldAndReleaseListener(
                GamepadType.TWO, GamepadButtons.start,
                () ->  {
                    skyStoneRobotHardware.useLimits = false;
                }, () -> {
                    skyStoneRobotHardware.useLimits = true;
                }
        ));

        gamepadController.addGamepadListener(GamepadListener.createPressListener(GamepadType.TWO, GamepadButtons.right_stick_button,
                () -> {
                    skyStoneRobotHardware.setStartingPos();
                }));

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
        if (gamepad1.left_trigger != 0) {
            skyStoneRobotHardware.omniDrive.rotateLeft(gamepad1.left_trigger);
        } else if (gamepad1.right_trigger != 0) {
            skyStoneRobotHardware.omniDrive.rotateRight(gamepad1.right_trigger);
        } else {
            skyStoneRobotHardware.omniDrive.dpadMove(gamepad1, slowMode ? SLOW_DPAD_POWER : DPAD_POWER, reverseMode);
        }

    }

}