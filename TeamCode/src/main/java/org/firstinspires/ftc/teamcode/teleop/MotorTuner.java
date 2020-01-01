package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.BaseHardware;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djfig on 1/7/2018.
 */


@TeleOp(name = "Motor Tuner")
public class MotorTuner extends BaseHardware {

    List<DcMotor> motors;
    int currentIndex = 0;

    DcMotor activeMotor;
    boolean dpadLeftPressed = false;
    boolean dpadRightPressed = false;

    final float DPAD_SPEED = 0.2f;

    @Override
    public void init() {
        motors = new ArrayList<DcMotor>();
        for (HardwareDevice device : hardwareMap.getAll(DcMotor.class)) {
            DcMotor motor = (DcMotor) device;
            motors.add(motor);
        }
        if (motors.size() > 0) {
            activeMotor = motors.get(currentIndex);
        }
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_left) {
            if (!dpadLeftPressed) {
                currentIndex -= 1;
                if (currentIndex < 0) {
                    currentIndex = motors.size() - 1;
                }
                activeMotor = motors.get(currentIndex);
                dpadLeftPressed = true;

            }
        } else {
            dpadLeftPressed = false;
        }

        if (gamepad1.dpad_right) {
            if (!dpadRightPressed) {
                currentIndex += 1;
                if (currentIndex >= motors.size() - 1) {
                    currentIndex = 0;
                }
                activeMotor = motors.get(currentIndex);
                dpadRightPressed = true;
            }
        } else {
            dpadRightPressed = false;
        }

        if (activeMotor != null) {
            if (gamepad1.right_stick_y != 0) {
                activeMotor.setPower(gamepad1.right_stick_y);
            } else if (gamepad1.dpad_up) {
                activeMotor.setPower(DPAD_SPEED);
            } else if (gamepad1.dpad_down) {
                activeMotor.setPower(-DPAD_SPEED);
            } else {
                activeMotor.setPower(0);
            }

            telemetry.addData("info", activeMotor.getConnectionInfo());
            telemetry.addData("Port", activeMotor.getPortNumber());
            telemetry.addData("Position", activeMotor.getCurrentPosition());
        }
    }
}
