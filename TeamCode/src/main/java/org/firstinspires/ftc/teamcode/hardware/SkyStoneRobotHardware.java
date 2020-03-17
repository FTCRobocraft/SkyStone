package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
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

    public static final int HORIZONTAL_GRIP_RANGE = 230;
    public static final int LIFT_RANGE = 950;
    public static final int LIFT_RAISED = 400;
    public static final float LIFT_STANDBY_POWER = 0f;
    public static final float LIFT_NO_LIMIT_SPEED = 0.3f;
    public static final double LIFT_NO_DRAG_HEIGHT = 1.2;
    public static final float COUNTS_PER_LIFT_IN = LIFT_RANGE/16.625f;

    public static final float LEFT_PLATFORM_DOWN = 0.47f;
    public static final float RIGHT_PLATFORM_DOWN = 0.53f;
    public static final float LEFT_PLATFORM_UP = 1f;
    public static final float RIGHT_PLATFORM_UP = 0f;

    public boolean isPlatformDown = false;

    public enum SkystoneStartingPosition {
        LEFT_LEFT,
        LEFT_RIGHT,
        RIGHT_LEFT,
        RIGHT_RIGHT
    }

    public boolean useLimits = true;
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
    public DcMotor leftLiftMotor;
    public DcMotor rightLiftMotor;

    // Cameras
    public WebcamName webcam;
    public SkystoneNavigation cameraNavigation;
    public Servo cameraServo;


    //Servos
    public CRServo capStone;
    public boolean capGrip = false;

    public Servo leftPlatform;
    public Servo rightPlatform;

    public ModernRoboticsI2cRangeSensor distanceSensor;

    //endregion

    // region TFOD Models
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";
    //endregion

    // Variables
    boolean isTracking = false;

    public int horizontalGripStartingPos;
    public int leftLiftMotorStartingPos;
    public int rightLiftMotorStartingPos;


    public SkyStoneRobotHardware(OpMode opMode) {
        super(opMode);
        //this.COUNTS_PER_INCH = 94.02384D;
        this.COUNTS_PER_INCH = 92.10499D;
        this.COUNTS_PER_LAT_INCH = 105.26285D;
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
        distanceSensor = initializeDevice(ModernRoboticsI2cRangeSensor.class, "distance");
        distanceSensor.setI2cAddress(I2cAddr.create8bit(100));
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
        leftLiftMotor = initializeDevice(DcMotor.class, "leftLiftMotor");
        rightLiftMotor = initializeDevice(DcMotor.class, "rightLiftMotor");
        gripServo = initializeDevice(Servo.class, "gripServo");
        leftPlatform = initializeDevice(Servo.class, "leftPlatform");
        rightPlatform = initializeDevice(Servo.class, "rightPlatform");
        horizontalGripMotor = initializeDevice(DcMotor.class, "horizontalGrip");
        horizontalGripMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        omniDrive = new OmniDrive(frontLeft, frontRight, backLeft, backRight);

        capStone = initializeDevice(CRServo.class, "capStone");

        setStartingPos();
        raisePlatformGrip();

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
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    public void setStartingPos() {
        horizontalGripStartingPos = horizontalGripMotor.getCurrentPosition();
        leftLiftMotorStartingPos = leftLiftMotor.getCurrentPosition();
        rightLiftMotorStartingPos = rightLiftMotor.getCurrentPosition();
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

        if (!useLimits) {
            horizontalGripMotor.setPower(power);
        } else if (currentPos <= horizontalGripStartingPos) {
            horizontalGripMotor.setPower(Math.max(0, power));
        } else if (currentPos >= horizontalGripStartingPos + HORIZONTAL_GRIP_RANGE) {
            horizontalGripMotor.setPower(Math.min(0, power));
        } else {
            horizontalGripMotor.setPower(power);
        }
    }

    public void setLiftPower(double power) {
        int currentPos = leftLiftMotor.getCurrentPosition();
        leftLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if (!useLimits) {
            leftLiftMotor.setPower(power > 0 ? LIFT_NO_LIMIT_SPEED : -LIFT_NO_LIMIT_SPEED);
            rightLiftMotor.setPower(power > 0 ? LIFT_NO_LIMIT_SPEED : -LIFT_NO_LIMIT_SPEED);
        } else if (currentPos <= leftLiftMotorStartingPos) {
            leftLiftMotor.setPower(Math.max(0, power));
            rightLiftMotor.setPower(Math.max(0, power));
            opMode.telemetry.addData("MAX", Math.max(0, power));
        } else if (currentPos >= leftLiftMotorStartingPos + LIFT_RANGE) {
            leftLiftMotor.setPower(Math.min(0, power));
            rightLiftMotor.setPower(Math.min(0, power));
            opMode.telemetry.addData("MIN", Math.min(0, power));
        } else {
            leftLiftMotor.setPower(power);
            rightLiftMotor.setPower(power);
            opMode.telemetry.addData("POWER", power);
        }
    }

    public void lowerPlatformGrip() {
        leftPlatform.setPosition(LEFT_PLATFORM_DOWN);
        rightPlatform.setPosition(RIGHT_PLATFORM_DOWN);
        isPlatformDown = true;
    }

    public void raisePlatformGrip() {
        leftPlatform.setPosition(LEFT_PLATFORM_UP);
        rightPlatform.setPosition(RIGHT_PLATFORM_UP);
        isPlatformDown = false;
    }

    @Override
    public void hardware_loop() {
        if (isTracking && cameraNavigation != null) {
            cameraNavigation.camera_loop();
        }

        capStone.setPower(capGrip ? 1 : -0.08);


        opMode.telemetry.addData("isTracking", isTracking);
        opMode.telemetry.addData("limits enabled", useLimits);
        opMode.telemetry.addData("cap grip", capGrip);

        opMode.telemetry.addData("LL Start Pos", leftLiftMotorStartingPos);
        opMode.telemetry.addData("RL Start Pos", rightLiftMotorStartingPos);
        opMode.telemetry.addData("Grip Start Pos", horizontalGripStartingPos);
        opMode.telemetry.addData("Horizontal Grip", horizontalGripMotor.getCurrentPosition());
        opMode.telemetry.addData("Left Lift", leftLiftMotor.getCurrentPosition());
        opMode.telemetry.addData("Right Lift", rightLiftMotor.getCurrentPosition());
        opMode.telemetry.addData("Grip Servo", gripServo.getPosition());
        opMode.telemetry.addData("Camera Servo", cameraServo.getPosition());
        opMode.telemetry.addData("Block Ult", distanceSensor.cmUltrasonic());
        opMode.telemetry.addData("Block Opt", distanceSensor.cmOptical());

    }
}
