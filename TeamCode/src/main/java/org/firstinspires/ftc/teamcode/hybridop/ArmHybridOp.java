package org.firstinspires.ftc.teamcode.hybridop;

import org.firstinspires.ftc.teamcode.hardware.AdamHardware;
import org.firstinspires.ftc.teamcode.playmaker.HybridOp;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class ArmHybridOp extends HybridOp {

    AdamHardware hardware;

    @Override
    public RobotHardware getHardware() {
        hardware = new AdamHardware(this);
        return hardware;
    }

    @Override
    public void teleop_loop() {
        super.teleop_loop();
        hardware.baseMotor.setPower(gamepad1.left_stick_y);
        hardware.armMotor.setPower(gamepad2.right_stick_y);
    }
}
