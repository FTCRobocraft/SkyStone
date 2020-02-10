package org.firstinspires.ftc.teamcode.action;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class LiftMotorAction implements Action {

    final double LIFT_SPEED = 1f;
    int distance;

    int leftTargetPos;
    int rightTargetPos;
    static final double TIMEOUT = 2000;
    double endTime;

    public LiftMotorAction(double height) {
        this.distance = (int) (height * SkyStoneRobotHardware.COUNTS_PER_LIFT_IN);
    }

    @Override
    public void init(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            // Cast RobotHardware to SkyStoneRobotHardware
            SkyStoneRobotHardware skyStoneRobotHardware = (SkyStoneRobotHardware) hardware;

            // Calculate target positions
            leftTargetPos = skyStoneRobotHardware.leftLiftMotorStartingPos + distance;
            rightTargetPos = skyStoneRobotHardware.rightLiftMotorStartingPos + distance;

            // Set motor target positions
            skyStoneRobotHardware.leftLiftMotor.setTargetPosition(leftTargetPos);
            skyStoneRobotHardware.rightLiftMotor.setTargetPosition(rightTargetPos);

            // Set motors to RUN_TO_POSITION
            skyStoneRobotHardware.leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            skyStoneRobotHardware.rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            endTime = System.currentTimeMillis() + TIMEOUT;
        }
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            SkyStoneRobotHardware skyStoneRobotHardware = (SkyStoneRobotHardware) hardware;
            boolean busy = skyStoneRobotHardware.leftLiftMotor.isBusy() && skyStoneRobotHardware.rightLiftMotor.isBusy();
            if (busy) {
                // Set power to lifts
                skyStoneRobotHardware.leftLiftMotor.setPower(LIFT_SPEED);
                skyStoneRobotHardware.rightLiftMotor.setPower(LIFT_SPEED);
            } else {
                // Stop lifts and reset their modes back to RUN_WITHOUT_ENCODER
                skyStoneRobotHardware.leftLiftMotor.setPower(0);
                skyStoneRobotHardware.rightLiftMotor.setPower(0);
                skyStoneRobotHardware.leftLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                skyStoneRobotHardware.rightLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            // Log telemetry data
            hardware.opMode.telemetry.addData("left target height", leftTargetPos);
            hardware.opMode.telemetry.addData("right target height", rightTargetPos);

            hardware.opMode.telemetry.addData("left height", skyStoneRobotHardware.leftLiftMotor.getCurrentPosition());
            hardware.opMode.telemetry.addData("right height", skyStoneRobotHardware.rightLiftMotor.getCurrentPosition());

            return !busy || System.currentTimeMillis() >= endTime;
        } else {
            return true;
        }
    }

    @Override
    public Double progress() {
        return null;
    }

    @Override
    public String progressString() {
        return null;
    }
}
