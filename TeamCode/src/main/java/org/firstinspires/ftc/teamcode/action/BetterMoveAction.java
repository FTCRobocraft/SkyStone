package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;
import org.firstinspires.ftc.teamcode.util.BetterEncoderDrive;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

/**
 * Created by djfigs1 on 9/30/17.
 */

public class BetterMoveAction implements Action {

    OmniDrive.Direction direction;
    double distance;
    float speed;
    double timeout;

    private BetterEncoderDrive driver;



    /**
     * This action allows you to move the robot in any of the eight directions for a set distance.
     *
     * @param direction Direction for the robot to move.
     * @param distance The distance (in centimeters) for the robot to move..
     * @param speed How much power is given to each motor.
     */
    public BetterMoveAction(OmniDrive.Direction direction, double distance, float speed) {
        this.direction = direction;
        this.distance = distance;
        this.speed = speed;

    }

    public void init(RobotHardware hardware) {
        driver = new BetterEncoderDrive(hardware.omniDrive);
        driver.setInchesToDrive(direction, distance, speed);
    }

    public boolean doAction(RobotHardware hardware) {
        driver.run(hardware);
        return !driver.isBusy;
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
