package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.DirectionalMoveAction;
import org.firstinspires.ftc.teamcode.hardware.BaseHardware;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class ParkSequence extends ActionSequence {

    public static final double FORWARD_DISTANCE = 24;
    public static final double RIGHT_STRAFE_DISTANCE = 12;
    public static final double LEFT_STRAFE_DISTANCE = 24;

    public ParkSequence(BaseHardware.Team team, BaseHardware.StartingPosition startingPosition) {
        switch (team) {
            case RED:
                switch (startingPosition) {
                    case LEFT:
                        initRedLeft();
                        break;
                    case RIGHT:
                        initRedRight();
                        break;
                }
                break;
            case BLUE:
                switch (startingPosition) {
                    case LEFT:
                        initBlueLeft();
                        break;
                    case RIGHT:
                        initBlueRight();
                        break;
                }
                break;
        }
    }

    public void initRedLeft() {
        addAction(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, FORWARD_DISTANCE, 1f, 8f));
        addAction(new DirectionalMoveAction(OmniDrive.Direction.RIGHT, LEFT_STRAFE_DISTANCE, 1f, 8f));
    }

    public void initRedRight() {
        addAction(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, FORWARD_DISTANCE, 1f, 8f));
        addAction(new DirectionalMoveAction(OmniDrive.Direction.RIGHT, RIGHT_STRAFE_DISTANCE, 1f, 8f));
    }

    public void initBlueLeft() {
        addAction(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, FORWARD_DISTANCE, 1f, 8f));
        addAction(new DirectionalMoveAction(OmniDrive.Direction.LEFT, LEFT_STRAFE_DISTANCE, 1f, 8f));
    }

    public void initBlueRight() {
        addAction(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, FORWARD_DISTANCE, 1f, 8f));
        addAction(new DirectionalMoveAction(OmniDrive.Direction.LEFT, RIGHT_STRAFE_DISTANCE, 1f, 8f));
    }

}
