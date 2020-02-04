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
            skyStoneRobotHardware.leftLiftMotor.setTargetPosition(skyStoneRobotHardware.leftLiftMotorStartingPos);
            skyStoneRobotHardware.leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            SkyStoneRobotHardware skyStoneRobotHardware = (SkyStoneRobotHardware) hardware;
            boolean busy = skyStoneRobotHardware.leftLiftMotor.isBusy();
            if (!busy) {
                skyStoneRobotHardware.leftLiftMotor.setPower(0);
                skyStoneRobotHardware.leftLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                return true;
            } else {
                skyStoneRobotHardware.leftLiftMotor.setPower(-1f);
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
