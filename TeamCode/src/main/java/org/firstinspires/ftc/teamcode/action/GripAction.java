package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class GripAction implements Action {

    double endTime;
    boolean grip;

    public GripAction(boolean grip) {
        this.grip = grip;
    }

    @Override
    public void init(RobotHardware hardware) {
        endTime = System.currentTimeMillis() + SkyStoneRobotHardware.GRIP_TIME;
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            ((SkyStoneRobotHardware) hardware).gripServo.setPosition(grip ? 1 : 0);
            return System.currentTimeMillis() >= endTime;
        }
        return true;
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

