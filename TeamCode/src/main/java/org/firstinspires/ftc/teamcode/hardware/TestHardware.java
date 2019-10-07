package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class TestHardware extends RobotHardware {

    public DcMotor leftMotor;
    public DcMotor rightMotor;

    public TestHardware(OpMode opMode) {
        super(opMode);
    }

    @Override
    public void initializeHardware() {
        leftMotor = initializeDevice(DcMotor.class, "leftMotor");
        rightMotor = initializeDevice(DcMotor.class, "rightMotor");
    }
}
