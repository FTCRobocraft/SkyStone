package org.firstinspires.ftc.teamcode.action;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class LiftMotorAction implements Action {

    final double LIFT_SPEED = 1f;
    int pos;
    double speed;
    int targetPos;
    static final double TIMEOUT = 3000;
    double endTime;

    public LiftMotorAction(int pos) {
        this.pos = pos;
    }

    @Override
    public void init(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            targetPos = ((SkyStoneRobotHardware) hardware).liftMotorStartingPos + pos;
            ((SkyStoneRobotHardware) hardware).liftMotor.setTargetPosition(targetPos);
            ((SkyStoneRobotHardware) hardware).liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (((SkyStoneRobotHardware) hardware).liftMotor.getCurrentPosition() > targetPos) {
                speed = -LIFT_SPEED;
            } else {
                speed = LIFT_SPEED;
            }

            endTime = System.currentTimeMillis() + TIMEOUT;
        }
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            boolean busy = ((SkyStoneRobotHardware) hardware).liftMotor.isBusy();
            if (busy) {
                ((SkyStoneRobotHardware) hardware).liftMotor.setPower(speed);
            } else {
                ((SkyStoneRobotHardware) hardware).liftMotor.setPower(0);
                ((SkyStoneRobotHardware) hardware).liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            hardware.opMode.telemetry.addData("target pos", targetPos);
            hardware.opMode.telemetry.addData("current pos", ((SkyStoneRobotHardware) hardware).liftMotor.getCurrentPosition());

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
