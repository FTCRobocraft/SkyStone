package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

/**
 * Created by djfigs1 on 9/30/17. not really
 */

public class BetterEncoderDrive {

    static final double     COUNTS_PER_MOTOR_REV    = 28;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 40;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     ROBOT_DIAGONAL_LENGTH = 19.5;
    static final double     INCHES_PER_DEGREE = (ROBOT_DIAGONAL_LENGTH * Math.PI)/360;

    private OmniDrive omniDrive;
    public boolean isBusy = false;

    private double inchesToDrive;
    private OmniDrive.Direction direction;
    private DcMotor.RunMode previousRunMode;
    private double timeout;
    private ElapsedTime runTime;

    private int FL_targetPosition;
    private int FR_targetPosition;
    private int BL_targetPosition;
    private int BR_targetPosition;

    private float FL_speed;
    private float FR_speed;
    private float BL_speed;
    private float BR_speed;

    public final double SLOW_THRESHOLD = 100;
    public final float SLOW_SPEED = 0.2f;

    public BetterEncoderDrive(OmniDrive omniDrive) {
        this.omniDrive = omniDrive;
    }

    public void setDegreesToDrive(int degrees, float speed, double timeout) {
        double distance = INCHES_PER_DEGREE * degrees;
        if (distance < 0) {
            distance = -distance;
            setInchesToDrive(OmniDrive.Direction.ROTATE_LEFT, distance, speed);
        } else {
            setInchesToDrive(OmniDrive.Direction.ROTATE_RIGHT, distance, speed);
        }
    }

    /**
     * This function prepares the EncoderDrive to run
     * @param direction Which direction the robot will move
     * @param distance The distance (in centimeters) the robot will move
     * @param power How much power is applied to each motor (0 -> 1)
     */

    public void setInchesToDrive(OmniDrive.Direction direction, double distance, float power) {
        this.inchesToDrive = distance;
        this.isBusy = true;
        this.runTime = new ElapsedTime();
        this.runTime.reset();

        this.previousRunMode = omniDrive.frontLeft.getMode();

        switch (direction) {
            case FORWARD:
                FL_speed = power;
                FR_speed = power;
                BL_speed = power;
                BR_speed = power;
                break;

            case BACKWARD:
                FL_speed = -power;
                FR_speed = -power;
                BL_speed = -power;
                BR_speed = -power;
                break;

            case LEFT:
                FL_speed = -power;
                FR_speed = power;
                BL_speed = power;
                BR_speed = -power;
                break;

            case RIGHT:
                FL_speed = power;
                FR_speed = -power;
                BL_speed = -power;
                BR_speed = power;
                break;

            case FORWARD_LEFT:
                FL_speed = 0;
                FR_speed = power;
                BL_speed = power;
                BR_speed = 0;
                break;

            case FORWARD_RIGHT:
                FL_speed = power;
                FR_speed = 0;
                BL_speed = 0;
                BR_speed = power;
                break;

            case BACKWARD_LEFT:
                FL_speed = 0;
                FR_speed = -power;
                BL_speed = -power;
                BR_speed = 0;
                break;

            case BACKWARD_RIGHT:
                FL_speed = -power;
                FR_speed = 0;
                BL_speed = 0;
                BR_speed = -power;
                break;

            case ROTATE_LEFT:
                FL_speed = -power;
                FR_speed = power;
                BL_speed = -power;
                BR_speed = power;
                break;

            case ROTATE_RIGHT:
                FL_speed = power;
                FR_speed = -power;
                BL_speed = power;
                BR_speed = -power;
                break;

        }

        int FL_direction = (FL_speed > 0) ? 1 : (FL_speed < 0) ? -1 : 0;
        int FR_direction = (FR_speed > 0) ? 1 : (FR_speed < 0) ? -1 : 0;
        int BL_direction = (BL_speed > 0) ? 1 : (BL_speed < 0) ? -1 : 0;
        int BR_direction = (BR_speed > 0) ? 1 : (BR_speed < 0) ? -1 : 0;

        FL_targetPosition = omniDrive.frontLeft.getCurrentPosition() + FL_direction * (int)(COUNTS_PER_INCH * distance);
        FR_targetPosition = omniDrive.frontRight.getCurrentPosition() + FR_direction * (int)(COUNTS_PER_INCH * distance);
        BL_targetPosition = omniDrive.backLeft.getCurrentPosition() + BL_direction * (int)(COUNTS_PER_INCH * distance);
        BR_targetPosition = omniDrive.backRight.getCurrentPosition() + BR_direction * (int)(COUNTS_PER_INCH * distance);

        omniDrive.frontLeft.setTargetPosition(FL_targetPosition);
        omniDrive.frontRight.setTargetPosition(FR_targetPosition);
        omniDrive.backLeft.setTargetPosition(BL_targetPosition);
        omniDrive.backRight.setTargetPosition(BR_targetPosition);

        omniDrive.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        omniDrive.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        omniDrive.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        omniDrive.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void run(RobotHardware hardware) {
        if (this.isBusy) {
            boolean busy = omniDrive.frontLeft.isBusy() || omniDrive.frontRight.isBusy()
                    || omniDrive.backLeft.isBusy() || omniDrive.backRight.isBusy();

            if (busy) {
                double frontLeftPos = omniDrive.frontLeft.getCurrentPosition();
                double frontRightPos = omniDrive.frontRight.getCurrentPosition();
                double backLeftPos = omniDrive.backLeft.getCurrentPosition();
                double backRightPos = omniDrive.backRight.getCurrentPosition();

                if (FL_speed != 0) {
                    if (Math.abs(FL_targetPosition - frontLeftPos) <= SLOW_THRESHOLD) {
                        omniDrive.frontLeft.setPower(FL_speed < 0 ? -SLOW_SPEED : SLOW_SPEED);
                    } else {
                        omniDrive.frontLeft.setPower(0);
                    }
                }

                if (FR_speed != 0) {
                    if (Math.abs(FR_targetPosition - frontRightPos) <= SLOW_THRESHOLD) {
                        omniDrive.frontRight.setPower(FR_speed < 0 ? -SLOW_SPEED : SLOW_SPEED);
                    } else {
                        omniDrive.frontRight.setPower(0);
                    }
                }

                if (BL_speed != 0) {
                    if (Math.abs(BL_targetPosition - backLeftPos) <= SLOW_THRESHOLD) {
                        omniDrive.backLeft.setPower(BL_speed < 0 ? -SLOW_SPEED : SLOW_SPEED);
                    } else {
                        omniDrive.backLeft.setPower(0);
                    }
                }

                if (BR_speed != 0) {
                    if (Math.abs(BR_targetPosition - backRightPos) <= SLOW_THRESHOLD) {
                        omniDrive.backRight.setPower(BR_speed < 0 ? -SLOW_SPEED : SLOW_SPEED);
                    } else {
                        omniDrive.backRight.setPower(0);
                    }
                }

                hardware.opMode.telemetry.addData("FL", String.format("%d -> %d", omniDrive.frontLeft.getCurrentPosition(), omniDrive.frontLeft.getTargetPosition()));
                hardware.opMode.telemetry.addData("FR", String.format("%d -> %d", omniDrive.frontRight.getCurrentPosition(), omniDrive.frontRight.getTargetPosition()));
                hardware.opMode.telemetry.addData("BL", String.format("%d -> %d", omniDrive.backLeft.getCurrentPosition(), omniDrive.backLeft.getTargetPosition()));
                hardware.opMode.telemetry.addData("BR", String.format("%d -> %d", omniDrive.backRight.getCurrentPosition(), omniDrive.backRight.getTargetPosition()));
            } else {
                isBusy = false;
                omniDrive.stopDrive();

                omniDrive.frontLeft.setMode(previousRunMode);
                omniDrive.frontRight.setMode(previousRunMode);
                omniDrive.backLeft.setMode(previousRunMode);
                omniDrive.backRight.setMode(previousRunMode);
            }

        }
    }
}
