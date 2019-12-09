package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class AdamHardware extends RobotHardware {

    public DcMotor baseMotor;
    public DcMotor armMotor;

    public AdamHardware(OpMode opmode) {
        super(opmode);
    }

    @Override
    public void initializeHardware() {
        baseMotor = initializeDevice(DcMotor.class, "baseMotor");
        baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        baseMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor = initializeDevice(DcMotor.class, "armMotor");
    }
}
