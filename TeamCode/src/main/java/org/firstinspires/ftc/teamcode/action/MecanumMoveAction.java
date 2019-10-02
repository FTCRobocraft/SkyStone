package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.hardware.BaseHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;
import org.firstinspires.ftc.teamcode.util.EncoderDrive;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

/**
 * Created by djfigs1 on 9/30/17.
 */

public class MecanumMoveAction implements Action {

    OmniDrive.Direction direction;
    double distance;
    float speed;
    double timeout;

    private EncoderDrive driver;

    /**
     * This action allows you to move the robot in any of the eight directions for a set distance.
     *
     * @param direction Direction for the robot to move.
     * @param distance The distance (in centimeters) for the robot to move..
     * @param speed How much power is given to each motor.
     * @param timeout Timeout in case the motors act up.
     */
    public MecanumMoveAction(OmniDrive.Direction direction, double distance, float speed, double timeout) {
        this.direction = direction;
        this.distance = distance;
        this.speed = speed;
        this.timeout = timeout;
    }

    public void init(RobotHardware hardware) {
        driver = new EncoderDrive(hardware.omniDrive);
        driver.setInchesToDrive(direction, distance, speed, timeout);
    }

    public boolean doAction(RobotHardware hardware) {
        driver.run(hardware);
        return !driver.isBusy;
    }
}
