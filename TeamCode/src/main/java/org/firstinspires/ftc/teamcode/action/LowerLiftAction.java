package org.firstinspires.ftc.teamcode.action;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class LowerLiftAction implements Action {

    @Override
    public void init(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            SkyStoneRobotHardware skyStoneRobotHardware = (SkyStoneRobotHardware) hardware;
            skyStoneRobotHardware.liftMotor.setTargetPosition(skyStoneRobotHardware.liftMotorStartingPos);
            skyStoneRobotHardware.liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            SkyStoneRobotHardware skyStoneRobotHardware = (SkyStoneRobotHardware) hardware;
            boolean busy = skyStoneRobotHardware.liftMotor.isBusy();
            if (!busy) {
                skyStoneRobotHardware.liftMotor.setPower(0);
                skyStoneRobotHardware.liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                return true;
            } else {
                skyStoneRobotHardware.liftMotor.setPower(-1f);
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
