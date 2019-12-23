package org.firstinspires.ftc.teamcode.hybridop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.AdamHardware;
import org.firstinspires.ftc.teamcode.playmaker.HybridOp;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

@TeleOp(name="Arm")
public class ArmHybridOp extends HybridOp {

    AdamHardware hardware;

    @Override
    public RobotHardware getHardware() {
        hardware = new AdamHardware(this);
        return hardware;
    }

    boolean brake = true;

    @Override
    public void teleop_loop() {
        super.teleop_loop();

        if (gamepad1.left_stick_y == 0) {
            if (brake) {
                hardware.baseMotor.setTargetPosition(hardware.baseMotor.getCurrentPosition());
                hardware.baseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                hardware.baseMotor.setPower(1f);
                brake = false;
            }

        } else {
            hardware.baseMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            hardware.baseMotor.setPower(gamepad1.left_stick_y);
            brake = true;
        }

        hardware.armMotor.setPower(gamepad1.right_stick_y / 5.0f);
    }
}
