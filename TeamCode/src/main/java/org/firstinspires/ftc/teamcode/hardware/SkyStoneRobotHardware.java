package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;
import org.firstinspires.ftc.teamcode.util.SkystoneNavigation;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class SkyStoneRobotHardware extends RobotHardware {

    public static final float CAM_X_DISPLACEMENT = 0f;
    public static final float CAM_Y_DISPLACEMENT = 0f;
    public static final float CAM_Z_DISPLACEMENT = 0f;

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

    // Cameras
    public WebcamName webcam;
    public SkystoneNavigation cameraNavigation;



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
        webcam = initializeDevice(WebcamName.class, "Webcam 1");

        spinnerRight.setDirection(DcMotorSimple.Direction.REVERSE);
        omniDrive = new OmniDrive(frontLeft, frontRight, backLeft, backRight);
    }

    public void initVuforia() {
        // USB Camera Setup
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = RobotHardware.vuforiaKey;
        parameters.cameraName = webcam;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        cameraNavigation = new SkystoneNavigation(this, CAM_X_DISPLACEMENT, CAM_Y_DISPLACEMENT, CAM_Z_DISPLACEMENT);
    }
}
