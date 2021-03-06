package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class ToggleGripAction implements Action {

    double endTime;

    @Override
    public void init(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            double currentPosition = ((SkyStoneRobotHardware) hardware).gripServo.getPosition();
            ((SkyStoneRobotHardware) hardware).gripServo.setPosition(currentPosition == 0 ? 1 : 0);
        }

    }

    @Override
    public boolean doAction(RobotHardware hardware) {
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

