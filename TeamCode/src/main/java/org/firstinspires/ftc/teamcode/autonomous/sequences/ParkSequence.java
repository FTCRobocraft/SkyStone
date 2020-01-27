package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.DirectionalMoveAction;
import org.firstinspires.ftc.teamcode.action.MoveAction;
import org.firstinspires.ftc.teamcode.action.WaitAction;
import org.firstinspires.ftc.teamcode.hardware.BaseHardware;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class ParkSequence extends ActionSequence {

    public static final double RIGHT_FORWARD_DISTANCE = 28;
    public static final double LEFT_FORWARD_DISTANCE = 4;
    public static final double SHORT_STRAFE_DISTANCE = 12;
    public static final double LONG_STRAFE_DISTANCE = 36;

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
        addAction(new WaitAction(20000));
        addAction(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, LEFT_FORWARD_DISTANCE, 0.4f, 4f));
        addAction(new DirectionalMoveAction(OmniDrive.Direction.RIGHT, LONG_STRAFE_DISTANCE, 1f, 8f));
    }

    public void initRedRight() {
        //addAction(new WaitAction(20000));
        //addAction(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, RIGHT_FORWARD_DISTANCE, 1f, 8f));
        addAction(new MoveAction(OmniDrive.Direction.RIGHT, SHORT_STRAFE_DISTANCE, 0.5f, 3000));
    }

    public void initBlueLeft() {
        //addAction(new WaitAction(20000));
        //addAction(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, LEFT_FORWARD_DISTANCE, 0.4f, 4f));
        addAction(new MoveAction(OmniDrive.Direction.LEFT, SHORT_STRAFE_DISTANCE, 0.5f, 3000));
    }

    public void initBlueRight() {
        addAction(new WaitAction(20000));
        addAction(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, RIGHT_FORWARD_DISTANCE, 1f, 8f));
        addAction(new DirectionalMoveAction(OmniDrive.Direction.LEFT, LONG_STRAFE_DISTANCE, 1f, 8f));
    }

}
