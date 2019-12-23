package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.PlatformHardware;

@TeleOp(name="TestArm")
public class TestArm extends PlatformHardware {


    int liftBound;
    int armBound;

    final int liftPower = 1;
    final int armPower = -1;

    int liftStart;
    int armStart;

    boolean atButton;

    @Override
    public void start() {
        //Get the starting positions and set the boundaries
        liftStart = lift.getCurrentPosition() + 100;
        armStart = arm.getCurrentPosition() - 100;

        liftBound = liftStart + 900;
        armBound = armStart - 400;
    }


    @Override
    public void loop() {

        //Move the arm within the boundaries
        if (lift.getCurrentPosition() <= liftStart) {
            if (gamepad1.dpad_up) {
                lift.setPower(liftPower);
            } else {
                lift.setPower(0);
            }
        } else if (lift.getCurrentPosition() >= liftBound) {
            if (gamepad1.dpad_down) {
                lift.setPower(-liftPower);
            } else {
                lift.setPower(0);
            }
        } else {
            if (gamepad1.dpad_up) {
                lift.setPower(liftPower);
            } else {
                lift.setPower(0);
            }
            if (gamepad1.dpad_down) {
                lift.setPower(-liftPower);
            } else {
                lift.setPower(0);
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
        telemetry.addData("Touch Sensor: ", button.isPressed());
    }

}