package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class PlatformHardware extends BaseHardware {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public DcMotor lift;
    public DcMotor arm;

    public CRServo grabber;

    public TouchSensor button;

    public OmniDrive omniDrive;

    @Override
    public void init() {
        super.init(); //This does nothing

        try {
            frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        } catch (Exception e) {
            telemetry.addData("Not Found:", e.getMessage());
        }

        try {
            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        } catch (Exception e) {
            telemetry.addData("Not Found:", e.getMessage());
        }

        try {
            backLeft = hardwareMap.get(DcMotor.class, "backLeft");
            backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        } catch (Exception e) {
            telemetry.addData("Not Found:", e.getMessage());
        }

        try {
            backRight = hardwareMap.get(DcMotor.class, "backRight");
        } catch (Exception e) {
            telemetry.addData("Not Found:", e.getMessage());
        }

        try {
            lift = hardwareMap.get(DcMotor.class, "lift");
        } catch (Exception e) {
            telemetry.addData("Not Found:", e.getMessage());
        }

        try {
            arm = hardwareMap.get(DcMotor.class, "arm");
        } catch (Exception e) {
            telemetry.addData("Not Found:", e.getMessage());
        }

        try {
            grabber = hardwareMap.get(CRServo.class, "grabber");
        } catch (Exception e) {
            telemetry.addData("Not Found:", e.getMessage());
        }

        try {
            button = hardwareMap.get(TouchSensor.class, "button");
        } catch (Exception e) {
            telemetry.addData("Not Found:", e.getMessage());
        }

        omniDrive = new OmniDrive(frontLeft, frontRight, backLeft, backRight);
    }
}
