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
        LOW, MIDDLE, HIGH;
    }

    liftStates liftState;

    int liftStart;
    int armStart;

    boolean grabberClosed;

    boolean atButton;

    @Override
    public void start() {
        //Get the starting positions and set the boundaries
        liftStart = lift.getCurrentPosition() + 100;
        armStart = arm.getCurrentPosition() - 100;

        liftBound = liftStart + 900;
        armBound = armStart - 400;

        liftState = liftStates.LOW;
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


        //Move the arm within the boundaries
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
            case HIGH:
                if (gamepad1.dpad_down) {
                    lift.setPower(-liftPower);
                } else {
                    lift.setPower(0);
                }
        }


        //Servo
        if (gamepad1.a) {
            if (grabberClosed) {
                grabber.setPosition(180);
                grabberClosed = false;
            } else {
                grabber.setPosition(0);
                grabberClosed = true;
            }
        }


        //Button logic
        if (button.isPressed()) {
            atButton = true;
        }
        if (atButton) {
            if (gamepad1.dpad_right) {
                arm.setPower(armPower);
                atButton = false;
            } else {
                arm.setPower(0);
            }
        } else if (arm.getCurrentPosition() >= armBound) {
            if (gamepad1.dpad_left) {
                arm.setPower(-armPower);
            } else {
                arm.setPower(0);
            }
        } else {
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
        }


        //Add data to the telemetry
        telemetry.addData("Lift Position: ", lift.getCurrentPosition());
        telemetry.addData("Lift Start: ", liftStart);
        telemetry.addData("Lift Max: ", liftBound);
        telemetry.addData("Arm Position: ", arm.getCurrentPosition());
        telemetry.addData("Arm Start: ", armStart);
        telemetry.addData("Arm Max: ", armBound);

        telemetry.addData("Servo Position: ", grabber.getPosition());
        telemetry.addData("Touch Sensor: ", button.isPressed());
    }

}