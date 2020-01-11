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
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;
import org.firstinspires.ftc.teamcode.util.SkystoneNavigation;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class SkyStoneRobotHardware extends RobotHardware {

    //region constants
    public static final float CAM_X_DISPLACEMENT = 0f;
    public static final float CAM_Y_DISPLACEMENT = 0f;
    public static final float CAM_Z_DISPLACEMENT = 0f;
    public static final float CAM_X_ROTATION = 0f;
    public static final float CAM_Y_ROTATION = -90f;
    public static final float CAM_Z_ROTATION = 0f;

    public static final double GRIP_TIME = 500;

    public static final int HORIZONTAL_GRIP_RANGE = 400;
    public static final int LIFT_RANGE = 1200;
    public static final int LIFT_RAISED = 400;
    //endregion

    //region hardware

    // Four main motors
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    // Grabber motors
    public DcMotor horizontalGripMotor;
    public Servo gripServo;

    // Lift motors
    public DcMotor liftMotor;

    // Cameras
    public WebcamName webcam;
    public SkystoneNavigation cameraNavigation;
    public Servo cameraServo;


    //Servos
    public Servo capStone;

    //endregion

    // region TFOD Models
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";
    //endregion

    // Variables
    boolean isTracking = false;

    public int horizontalGripStartingPos;
    public int liftMotorStartingPos;


    public SkyStoneRobotHardware(OpMode opMode) {
        super(opMode);
        this.COUNTS_PER_INCH = 94.02384D;
        this.COUNTS_PER_DEGREE = (COUNTS_PER_INCH * Math.PI * 21.77298) / 360D;
    }

    @Override
    public void initializeAutonomous() {
        this.initVuforia();
        this.initTFOD();
    }

    @Override
    public void initializeHardware() {
        webcam = initializeDevice(WebcamName.class, "Webcam 1");
        cameraServo = initializeDevice(Servo.class, "cameraServo");
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
        gripServo = initializeDevice(Servo.class, "gripServo");
        horizontalGripMotor = initializeDevice(DcMotor.class, "horizontalGrip");
        horizontalGripMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        omniDrive = new OmniDrive(frontLeft, frontRight, backLeft, backRight);

        capStone = initializeDevice(Servo.class, "capStone");

        setStartingPos();

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

    public void initTFOD() {
        int tfodMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    public void setStartingPos() {
        horizontalGripStartingPos = horizontalGripMotor.getCurrentPosition();
        liftMotorStartingPos = liftMotor.getCurrentPosition();
    }

    public void startTracking() {
        isTracking = true;
        if (cameraNavigation != null) {
            cameraNavigation.startTracking();
        }

        if (tfod != null) {
            tfod.activate();
        }
    }

    public void stopTracking() {
        isTracking = false;
        if (cameraNavigation != null) {
            cameraNavigation.stopTracking();
        }

        if (tfod != null) {
            tfod.deactivate();
        }
    }

    public void setHorizontalGripPower(double power) {
        int currentPos = horizontalGripMotor.getCurrentPosition();

        if (currentPos <= horizontalGripStartingPos) {
            horizontalGripMotor.setPower(Math.max(0, power));
        } else if (currentPos >= horizontalGripStartingPos + HORIZONTAL_GRIP_RANGE) {
            horizontalGripMotor.setPower(Math.min(0, power));
        } else {
            horizontalGripMotor.setPower(power);
        }
    }

    public void setLiftPower(double power) {
        int currentPos = liftMotor.getCurrentPosition();

        if (currentPos <= liftMotorStartingPos) {
            liftMotor.setPower(Math.max(0D, power));
            opMode.telemetry.addData("MAX", Math.max(0D, power));
        } else if (currentPos >= liftMotorStartingPos + LIFT_RANGE) {
            liftMotor.setPower(Math.min(0D, power));
            opMode.telemetry.addData("MIN", Math.min(0D, power));
        } else {
            liftMotor.setPower(power);
            opMode.telemetry.addData("POWER", power);
        }
    }

    @Override
    public void hardware_loop() {
        if (isTracking && cameraNavigation != null) {
            cameraNavigation.camera_loop();
        }

        opMode.telemetry.addData("isTracking", isTracking);
        opMode.telemetry.addData("lift mode", liftMotor.getMode());

        opMode.telemetry.addData("Lift Start Pos", liftMotorStartingPos);
        opMode.telemetry.addData("Grip Start Pos", horizontalGripStartingPos);
        opMode.telemetry.addData("Horizontal Grip", horizontalGripMotor.getCurrentPosition());
        opMode.telemetry.addData("Lift Motor", liftMotor.getCurrentPosition());
        opMode.telemetry.addData("Grip Servo", gripServo.getPosition());
        opMode.telemetry.addData("Camera Servo", cameraServo.getPosition());

    }
}
