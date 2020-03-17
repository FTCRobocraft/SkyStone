package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.MoveAction;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware.SkystoneStartingPosition;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class ParkingSequence extends ActionSequence {

    public enum ParkingDestination {
        WALL,
        BRIDGE
    }

    private static final double SHORT_STRAFE_DISTANCE = 12;
    private static final double LONG_STRAFE_DISTANCE = 30;
    private static final double DISTANCE_TO_BRIDGE = 24;
    private static final float SPEED = 0.75f;
    private static final double TIMEOUT = 3000;
    private static  double  boomer = 20;


    public ParkingSequence(SkystoneStartingPosition startingPosition, ParkingDestination parkingDestination) {
        switch (parkingDestination) {

            case WALL:
                strafe(startingPosition);
                break;
            case BRIDGE:
                addAction(new MoveAction(OmniDrive.Direction.FORWARD, DISTANCE_TO_BRIDGE, SPEED, TIMEOUT));
                strafe(startingPosition);
                boomer = 40;
                break;
                //the whole robot


        }
    }

    public void strafe(SkystoneStartingPosition startingPosition) {
        switch (startingPosition) {
            case LEFT_LEFT:
                addAction(new MoveAction(
                        OmniDrive.Direction.RIGHT,
                        LONG_STRAFE_DISTANCE,
                        SPEED,
                        TIMEOUT));
                break;
            case LEFT_RIGHT:
                addAction(new MoveAction(
                        OmniDrive.Direction.RIGHT,
                        SHORT_STRAFE_DISTANCE,
                        SPEED,
                        TIMEOUT));
                break;
            case RIGHT_LEFT:
                addAction(new MoveAction(
                        OmniDrive.Direction.LEFT,
                        SHORT_STRAFE_DISTANCE,
                        SPEED,
                        TIMEOUT));
                break;
            case RIGHT_RIGHT:
                addAction(new MoveAction(
                        OmniDrive.Direction.LEFT,
                        LONG_STRAFE_DISTANCE,
                        SPEED,
                        TIMEOUT));
                break;
        }
    }

}
