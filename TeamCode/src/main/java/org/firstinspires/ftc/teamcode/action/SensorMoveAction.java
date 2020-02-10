package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

public class SensorMoveAction implements Action {

    double avgDistanceTravelled = 0;
    static final double THRESHOLD = 0.1;
    static final float SPEED = 0.11f;

    private double FL_start;
    private double FR_start;
    private double BL_start;
    private double BR_start;

    double desiredDistance;
    boolean useOptical = true;

    public SensorMoveAction(double distance, boolean useOptical) {
        desiredDistance = distance;
        this.useOptical = useOptical;
    }


    @Override
    public void init(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            FL_start = hardware.omniDrive.frontLeft.getCurrentPosition();
            FR_start = hardware.omniDrive.frontRight.getCurrentPosition();
            BL_start = hardware.omniDrive.backLeft.getCurrentPosition();
            BR_start = hardware.omniDrive.backRight.getCurrentPosition();
        }
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            SkyStoneRobotHardware skyStoneRobotHardware = (SkyStoneRobotHardware) hardware;
            double distance = useOptical ? skyStoneRobotHardware.distanceSensor.cmOptical() : skyStoneRobotHardware.distanceSensor.cmUltrasonic();
            hardware.opMode.telemetry.addData("distance", distance);
            if (Math.abs(distance - desiredDistance) >= THRESHOLD || Double.isNaN(distance)) {
                hardware.omniDrive.moveForward(SPEED);
            } else {
                hardware.omniDrive.stopDrive();
                return true;
            }
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
