package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class SkyStoneRobotHardware extends RobotHardware {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public SkyStoneRobotHardware(OpMode opMode) {
        super(opMode);
    }

    @Override
    public void initializeHardware() {
        frontLeft = initializeDevice(DcMotor.class, "frontLeft");
        frontRight = initializeDevice(DcMotor.class, "frontRight");
        backLeft = initializeDevice(DcMotor.class, "backLeft");
        backRight = initializeDevice(DcMotor.class, "backRight");
        omniDrive = new OmniDrive(frontLeft, frontRight, backLeft, backRight);
    }
}
