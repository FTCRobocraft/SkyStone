package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

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
    public static final float CAM_X_ROTATION = 0f;
    public static final float CAM_Y_ROTATION = -90f;
    public static final float CAM_Z_ROTATION = 0f;

    // Four main motors
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    // Grabber motors
    public DcMotor horizontalGripMotor;
    public TouchSensor horizontalTouchSensor;
    public int horizontalGripStartingPosition = -99999;
    public final int horizontalGripStartPadding = 20;
    public final int horizontalGripDistance = 550;
    public int horizontalGripLimit = 99999;

    // Lift motors
    public DcMotor liftMotor;

    // Cameras
    public WebcamName webcam;
    public SkystoneNavigation cameraNavigation;
    public Servo cameraServo;
    boolean isTracking = false;



    public SkyStoneRobotHardware(OpMode opMode) {
        super(opMode);
    }

    @Override
    public void initializeHardware() {
        webcam = initializeDevice(WebcamName.class, "Webcam 1");
        frontLeft = initializeDevice(DcMotor.class, "frontLeft");
        if (frontLeft != null) {
            frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        frontRight = initializeDevice(DcMotor.class, "frontRight");
        backLeft = initializeDevice(DcMotor.class, "backLeft");
        if (backLeft != null) {
            backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        backRight = initializeDevice(DcMotor.class, "backRight");
        liftMotor = initializeDevice(DcMotor.class, "liftMotor");
        horizontalGripMotor = initializeDevice(DcMotor.class, "horizontalGrip");
        horizontalTouchSensor = initializeDevice(TouchSensor.class, "horizontalTouch");
        omniDrive = new OmniDrive(frontLeft, frontRight, backLeft, backRight);

    }

    public void initVuforia() {
        // USB Camera Setup
        if (webcam != null) {
            int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
            vuforiaParameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

            vuforiaParameters.vuforiaLicenseKey = RobotHardware.vuforiaKey;
            vuforiaParameters.cameraName = webcam;
            vuforia = ClassFactory.getInstance().createVuforia(vuforiaParameters);
            cameraNavigation = new SkystoneNavigation(this,
                    CAM_X_DISPLACEMENT, CAM_Y_DISPLACEMENT, CAM_Z_DISPLACEMENT,
                    CAM_X_ROTATION, CAM_Y_ROTATION, CAM_Z_ROTATION);
            cameraNavigation.init();
        } else {
            opMode.telemetry.addLine("Error: can't find camera!");
        }
    }

    public void startTracking() {
        if (cameraNavigation != null) {
            isTracking = true;
            cameraNavigation.startTracking();
        }
    }

    public void stopTracking() {
        if (cameraNavigation != null) {
            isTracking = false;
            cameraNavigation.stopTracking();
        }
    }

    public void setHorizontalGripPower(double power) {
        if (horizontalTouchSensor.isPressed() || horizontalGripMotor.getCurrentPosition() <= horizontalGripStartingPosition) {
            power = Math.max(power, 0);
        } else if (horizontalGripMotor.getCurrentPosition() >= horizontalGripLimit) {
            power = Math.min(power, 0);
        }
        horizontalGripMotor.setPower(power);
    }

    public boolean calibrateHorizontalGrip() {
        horizontalGripMotor.setPower(-0.4);
        if (horizontalTouchSensor.isPressed()) {
            horizontalGripMotor.setPower(0);
            horizontalGripStartingPosition = horizontalGripMotor.getCurrentPosition() + horizontalGripStartPadding;
            horizontalGripLimit = horizontalGripStartingPosition + horizontalGripDistance;
        }
        return horizontalTouchSensor.isPressed();
    }

    @Override
    public void hardware_loop() {
        if (isTracking && cameraNavigation != null) {
            cameraNavigation.camera_loop();
        }


        opMode.telemetry.addData("touch", horizontalTouchSensor.isPressed());
        opMode.telemetry.addData("h. grip", horizontalGripMotor.getCurrentPosition());
        opMode.telemetry.addData("h. grip start", horizontalGripStartingPosition);
        opMode.telemetry.addData("h. grip limit", horizontalGripLimit);
        if (horizontalTouchSensor.isPressed()) {
            horizontalGripStartingPosition = horizontalGripMotor.getCurrentPosition();
        }
    }
}
