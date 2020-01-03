package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.PlatformHardware;

@TeleOp(name="TestArm")
public class TestArm extends PlatformHardware {


    int liftBound;
    int armBound;

    final int liftPower = 1;
    final int armPower = -1;

    enum liftStates {
        LOW, MIDDLE, HIGH
    }

    enum armStates {
        BUTTON, MIDDLE, FAR
    }

    liftStates liftState;

    armStates armState;

    int liftStart;
    int armStart;


    @Override
    public void start() {
        // Get the starting positions and set the boundaries
        liftStart = lift.getCurrentPosition() + 100;
        armStart = arm.getCurrentPosition() - 100;

        liftBound = liftStart + 900;
        armBound = armStart - 400;

        liftState = liftStates.LOW;
        armState = armStates.BUTTON;
    }


    @Override
    public void loop() {
        if (lift.getCurrentPosition() <= liftStart) {
            liftState = liftStates.LOW;
        } else if(lift.getCurrentPosition() >= liftBound) {
            liftState = liftStates.HIGH;
        } else {
            liftState = liftStates.MIDDLE;
        }


        // Move the arm within the boundaries
        switch(liftState) {
            case LOW:
                if (gamepad1.dpad_up) {
                    lift.setPower(liftPower);
                } else {
                    lift.setPower(0);
                }
                break;
            case MIDDLE:
                if (gamepad1.dpad_up) {
                    lift.setPower(liftPower);
                } else if (gamepad1.dpad_down) {
                    lift.setPower(-liftPower);
                } else {
                    lift.setPower(0);
                }
                break;
            case HIGH:
                if (gamepad1.dpad_down) {
                    lift.setPower(-liftPower);
                    liftState = liftStates.MIDDLE;
                } else {
                    lift.setPower(0);
                }
                break;
        }


        // Servo
        if (gamepad1.a) {
            // ???
        }


        // Button logic
        if (button.isPressed()) {
            armState = armStates.BUTTON;
        } else if (arm.getCurrentPosition() >= armBound) {
            armState = armStates.FAR;
        } else {
            armState = armStates.MIDDLE;
        }

        switch (armState) {
            case BUTTON:
                if (gamepad1.dpad_right) {
                    arm.setPower(armPower);
                    armState = armStates.MIDDLE;
                } else {
                    arm.setPower(0);
                }
                break;
            case MIDDLE:
                if (gamepad1.dpad_left) {
                    arm.setPower(-armPower);
                } else {
                    arm.setPower(0);
                }
                if (gamepad1.dpad_right) {
                    arm.setPower(armPower);
                } else {
                    arm.setPower(0);
                }
                break;
            case FAR:
                if (gamepad1.dpad_left) {
                    arm.setPower(-armPower);
                    armState = armStates.MIDDLE;
                } else {
                    arm.setPower(0);
                }
                break;
        }


        // Add data to the telemetry
        telemetry.addData("Lift Position: ", lift.getCurrentPosition());
        telemetry.addData("Lift Start: ", liftStart);
        telemetry.addData("Lift Max: ", liftBound);
        telemetry.addData("Arm Position: ", arm.getCurrentPosition());
        telemetry.addData("Arm Start: ", armStart);
        telemetry.addData("Arm Max: ", armBound);
        telemetry.addData("Touch Sensor: ", button.isPressed());
    }

}