package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class StoneStrafeAction implements Action {

    MoveAction moveAction;
    private int timesStrafed = 0;
    public final static double STRAFE_DISTANCE = 10;
    StrafeDirection strafeDirection;
    private final static float SPEED = 0.75f;

    public enum StrafeDirection {
        LEFT,
        RIGHT
    }


    public StoneStrafeAction(StrafeDirection direction) {
        strafeDirection = direction;
    }



    @Override
    public void init(RobotHardware hardware) {
        timesStrafed += 1;
        moveAction = new MoveAction(strafeDirection == StrafeDirection.RIGHT ? OmniDrive.Direction.RIGHT : OmniDrive.Direction.LEFT,
            STRAFE_DISTANCE, SPEED, 10000);
        moveAction.init(hardware);
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        return moveAction.doAction(hardware);
    }

    @Override
    public Double progress() {
        return null;
    }

    @Override
    public String progressString() {
        return null;
    }

    public int getTimesStrafed() {
        return timesStrafed;
    }
}
