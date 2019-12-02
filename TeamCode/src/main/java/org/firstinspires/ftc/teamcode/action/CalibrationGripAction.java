package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;
import org.firstinspires.ftc.teamcode.util.EncoderDrive;

public class CalibrationGripAction implements Action {

    @Override
    public void init(RobotHardware hardware) {

    }


    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            return ((SkyStoneRobotHardware) hardware).calibrateHorizontalGrip();
        }
        return true;
    }

    public Double progress() {
        return null;
    }

    public String progressString() {
        return null;
    }
}
