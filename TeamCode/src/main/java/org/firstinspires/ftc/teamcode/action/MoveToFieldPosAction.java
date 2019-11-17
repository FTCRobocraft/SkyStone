package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class MoveToFieldPosAction implements Action {

    public MoveToFieldPosAction(float X, float Y, float Z, float heading) {

    }

    @Override
    public void init(RobotHardware hardware) {

    }


    @Override
    public boolean doAction(RobotHardware hardware) {
        return true;
    }

    public Double progress() {
        return null;
    }

    public String progressString() {
        return null;
    }
}
