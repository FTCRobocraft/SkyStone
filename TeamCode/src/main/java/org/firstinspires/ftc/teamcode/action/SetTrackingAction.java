package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class SetTrackingAction implements Action {

    boolean enabled;

    public SetTrackingAction(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void init(RobotHardware hardware) {

    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            if (enabled) {
                ((SkyStoneRobotHardware) hardware).startTracking();
            } else {
                ((SkyStoneRobotHardware) hardware).stopTracking();
            }
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
