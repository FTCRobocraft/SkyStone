package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class ToggleGripAction implements Action {

    double endTime;

    @Override
    public void init(RobotHardware hardware) {
        endTime = System.currentTimeMillis() + SkyStoneRobotHardware.GRIP_TIME;
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            double currentPos = ((SkyStoneRobotHardware) hardware).gripServo.getPosition();
            ((SkyStoneRobotHardware) hardware).gripServo.setPosition(currentPos == 0 ? 1 : 0);
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

