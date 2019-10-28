package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class TestHardware extends RobotHardware {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public TestHardware(OpMode opMode) {
        super(opMode);
    }

    @Override
    public void initializeHardware() {
        frontLeft = initializeDevice(DcMotor.class, "frontLeft");
        frontRight = initializeDevice(DcMotor.class, "frontRight");
        backLeft = initializeDevice(DcMotor.class, "backLeft");
        backRight = initializeDevice(DcMotor.class, "frontRight");

    }
}
