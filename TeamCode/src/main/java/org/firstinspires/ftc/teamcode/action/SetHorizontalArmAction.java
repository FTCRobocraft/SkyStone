package org.firstinspires.ftc.teamcode.action;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class SetHorizontalArmAction implements Action {

    int position;
    int targetPosition;

    public SetHorizontalArmAction(int position) {
        this.position = position;
    }

    //120 counts per inch forward

    @Override
    public void init(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            targetPosition = ((SkyStoneRobotHardware) hardware).horizontalGripStartingPos + position;
            ((SkyStoneRobotHardware) hardware).horizontalGripMotor.setTargetPosition(targetPosition);
            ((SkyStoneRobotHardware) hardware).horizontalGripMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            boolean busy = ((SkyStoneRobotHardware) hardware).horizontalGripMotor.isBusy();
            if (busy) {
                ((SkyStoneRobotHardware) hardware).horizontalGripMotor.setPower(0.5);
            } else {
                ((SkyStoneRobotHardware) hardware).horizontalGripMotor.setPower(0);
                ((SkyStoneRobotHardware) hardware).horizontalGripMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            return !busy;
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
