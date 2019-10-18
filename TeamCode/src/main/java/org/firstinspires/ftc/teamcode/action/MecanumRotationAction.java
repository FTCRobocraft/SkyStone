package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;
import org.firstinspires.ftc.teamcode.util.EncoderDrive;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

/**
 * Created by djfigs1 on 10/1/17.
 */

public class MecanumRotationAction implements Action {

    private int degrees;
    private float speed;
    EncoderDrive encoderDrive;

    public final double INCHES_PER_DEGREE = 66.1/360;


    public MecanumRotationAction(int degrees, float speed) {
        this.degrees = degrees;
        this.speed = speed;
    }

    @Override
    public void init(RobotHardware hardware) {
        double distance = INCHES_PER_DEGREE * degrees;
        encoderDrive = new EncoderDrive(hardware.omniDrive);
        if (distance < 0) {
            distance = -distance;
            encoderDrive.setInchesToDrive(OmniDrive.Direction.ROTATE_LEFT, distance, speed, 1500);
        } else {
            encoderDrive.setInchesToDrive(OmniDrive.Direction.ROTATE_RIGHT, distance, speed, 1500);
        }
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        encoderDrive.run(hardware);
        return !encoderDrive.isBusy;
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
