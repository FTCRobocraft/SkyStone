package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class RotateCameraAction implements Action {


    float degrees;
    double targetPosition;

    public RotateCameraAction(float degrees) {
        this.degrees = degrees;
        this.targetPosition = (degrees + 90) / 180;
    }


    @Override
    public void init(RobotHardware hardware) {
        ((SkyStoneRobotHardware) hardware).cameraNavigation.setCameraRotation(this.degrees);
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            ((SkyStoneRobotHardware) hardware).cameraServo.setPosition(targetPosition);
            return ((SkyStoneRobotHardware) hardware).cameraServo.getPosition() == targetPosition;
        }
        return false;
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
