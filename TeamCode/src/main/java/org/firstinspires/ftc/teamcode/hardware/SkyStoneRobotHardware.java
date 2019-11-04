package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class SkyStoneRobotHardware extends RobotHardware {

    // Four main motors
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    // Two spinner motors
    public DcMotor spinnerLeft;
    public DcMotor spinnerRight;

    // Lift motors
    public DcMotor liftMotor;



    public SkyStoneRobotHardware(OpMode opMode) {
        super(opMode);
    }

    @Override
    public void initializeHardware() {
        frontLeft = initializeDevice(DcMotor.class, "frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight = initializeDevice(DcMotor.class, "frontRight");
        backLeft = initializeDevice(DcMotor.class, "backLeft");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = initializeDevice(DcMotor.class, "backRight");
        spinnerLeft = initializeDevice(DcMotor.class, "spinnerLeft");
        spinnerRight = initializeDevice(DcMotor.class, "spinnerRight");
        liftMotor = initializeDevice(DcMotor.class, "liftMotor");

        spinnerRight.setDirection(DcMotorSimple.Direction.REVERSE);
        omniDrive = new OmniDrive(frontLeft, frontRight, backLeft, backRight);
    }
}
