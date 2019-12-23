package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;
import org.firstinspires.ftc.teamcode.util.EncoderDrive;

public class MarkerNavigation implements Action {

    public enum Axis {
        X,
        Y
    }

    public enum AlignDirection {
        FRONT_BACK,
        LEFT_RIGHT
    }

    float pos;
    Axis axis;
    AlignDirection alignDirection;
    double speed;

    final double SLOW_THRESHOLD = 200;
    final double SLOW_SPEED = 0.2f;
    final float DEADZONE = 5;

    public MarkerNavigation(float pos, Axis axis, AlignDirection alignDirection, double speed) {
        this.pos = pos;
        this.axis = axis;
        this.alignDirection = alignDirection;
        this.speed = speed;
    }


    @Override
    public void init(RobotHardware hardware) {

    }


    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware instanceof SkyStoneRobotHardware) {
            VectorF currentPosition = ((SkyStoneRobotHardware) hardware).cameraNavigation.getRobotPosition();
            float distanceToTarget;
            boolean moveInPosDirection;
            float currentX = currentPosition.get(0);
            float currentY = currentPosition.get(1);

            switch (this.axis) {
                case X:
                    distanceToTarget = this.pos - currentX;
                    moveInPosDirection = this.pos > currentX;
                    if (Math.abs(distanceToTarget) <= DEADZONE) {
                        return true;
                    } else {
                        switch (alignDirection) {
                            case FRONT_BACK:
                                hardware.omniDrive.moveForward(moveInPosDirection ? speed : -speed);
                                break;
                            case LEFT_RIGHT:
                                hardware.omniDrive.moveRight(moveInPosDirection ? speed : -speed);
                                break;
                        }
                    }
                    break;
                case Y:
                    distanceToTarget = this.pos - currentY;
                    moveInPosDirection = this.pos > currentY;
                    if (Math.abs(distanceToTarget) <= DEADZONE) {
                        return true;
                    } else {
                        switch (alignDirection) {
                            case FRONT_BACK:
                                hardware.omniDrive.moveForward(moveInPosDirection ? speed : -speed);
                                break;
                            case LEFT_RIGHT:
                                hardware.omniDrive.moveRight(moveInPosDirection ? speed : -speed);
                                break;
                        }
                    }
                    break;
            }

            return false;
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
