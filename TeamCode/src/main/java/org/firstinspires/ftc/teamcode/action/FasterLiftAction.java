package org.firstinspires.ftc.teamcode.action;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class FasterLiftAction implements Action {

    final double LIFT_SPEED = 0.5f;
    int distance;

    int leftTargetPos;
    int rightTargetPos;
    static final double TIMEOUT = 2000;
    static final double THRESHOLD = 25;
    double endTime;
    int startPos;

    public FasterLiftAction(double height) {
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

            startPos = skyStoneRobotHardware.leftLiftMotor.getCurrentPosition();
            endTime = System.currentTimeMillis() + TIMEOUT;
        }
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            SkyStoneRobotHardware skyStoneRobotHardware = (SkyStoneRobotHardware) hardware;
            boolean busy = skyStoneRobotHardware.leftLiftMotor.isBusy() && skyStoneRobotHardware.rightLiftMotor.isBusy();
            int pos = skyStoneRobotHardware.leftLiftMotor.getCurrentPosition();

            double speed = LIFT_SPEED;
            if (startPos <= leftTargetPos) {
                busy = !(leftTargetPos - pos <= THRESHOLD);
            } else {
                busy = !(pos - leftTargetPos <= THRESHOLD);
                speed = -LIFT_SPEED;
            }

            // Log telemetry data
            hardware.opMode.telemetry.addData("left target height", leftTargetPos);
            hardware.opMode.telemetry.addData("right target height", rightTargetPos);

            hardware.opMode.telemetry.addData("left height", skyStoneRobotHardware.leftLiftMotor.getCurrentPosition());
            hardware.opMode.telemetry.addData("right height", skyStoneRobotHardware.rightLiftMotor.getCurrentPosition());

            if (!busy || System.currentTimeMillis() >= endTime) {
                skyStoneRobotHardware.leftLiftMotor.setPower(0);
                skyStoneRobotHardware.rightLiftMotor.setPower(0);
                return true;
            } else {
                skyStoneRobotHardware.leftLiftMotor.setPower(speed);
                skyStoneRobotHardware.rightLiftMotor.setPower(speed);
                return false;
            }

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
