package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.BulkExecuteAction;
import org.firstinspires.ftc.teamcode.action.DirectionalMoveAction;
import org.firstinspires.ftc.teamcode.action.FasterLiftAction;
import org.firstinspires.ftc.teamcode.action.FindSkystoneAction;
import org.firstinspires.ftc.teamcode.action.LowerPlatformGripAction;
import org.firstinspires.ftc.teamcode.action.MoveAction;
import org.firstinspires.ftc.teamcode.action.RaisePlatformGripAction;
import org.firstinspires.ftc.teamcode.action.SensorMoveAction;
import org.firstinspires.ftc.teamcode.action.GripAction;
import org.firstinspires.ftc.teamcode.action.StoneStrafeAction;
import org.firstinspires.ftc.teamcode.action.UnstrafeAction;
import org.firstinspires.ftc.teamcode.hardware.BaseHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

import java.util.ArrayList;

public class FetchAndParkSequence extends ActionSequence {

    FindSkystoneAction findSkystoneAction;

    // How long to wait for a robot to pass, if any.
    public final double PASSING_TIME = 0;
    public final double INIT_FORWARD_DISTANCE = 18;
    public final double INIT_STRAFE_DISTANCE = 8.5;
    public final double GRIP_ALIGN_DISTANCE = 4.5;

    public FetchAndParkSequence(BaseHardware.Team team, BaseHardware.StartingPosition startingPosition) {
        switch (team) {
            case RED:
                // Move to starting point for scanning
                addAction(new MoveAction(OmniDrive.Direction.FORWARD, INIT_FORWARD_DISTANCE, 0.8f, 10000));
                addAction(new MoveAction(OmniDrive.Direction.LEFT, INIT_STRAFE_DISTANCE, 0.5f, 10000));

                // Find a skystone
                findSkystoneAction = new FindSkystoneAction(StoneStrafeAction.StrafeDirection.RIGHT);
                addAction(findSkystoneAction);

                // Move to where we can grip
                addAction(new MoveAction(OmniDrive.Direction.RIGHT, GRIP_ALIGN_DISTANCE, 0.4f, 1000));

                // Simultaneously lift, open grip, and move forward.
                ArrayList<Action> redBlockActions = new ArrayList<>();
                redBlockActions.add(new FasterLiftAction(5));
                redBlockActions.add(new GripAction(false));
                redBlockActions.add(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, 21, 0.4f, 8f));
                addAction(new BulkExecuteAction(redBlockActions));

                // Lower and grip
                addAction(new FasterLiftAction(0));
                addAction(new GripAction(true));

                // Move back and to building area, release stone
                addAction(new MoveAction(OmniDrive.Direction.BACKWARD, 8, 0.5f, 2000));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.RIGHT, 60, 1f, 12f));
                addAction(new GripAction(false));

                // Lower lift and park.
                addAction(new FasterLiftAction(1.5));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.LEFT, 6, 0.5f, 4));
                addAction(new FasterLiftAction(0));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.LEFT, 12, 0.8f, 8));
                break;
            case BLUE:
                // Move to starting point for scanning
                ArrayList<Action> blockActions = new ArrayList<>();
                blockActions.add(new RaisePlatformGripAction());
                blockActions.add(new FasterLiftAction(5));
                blockActions.add(new GripAction(false));
                blockActions.add(new MoveAction(OmniDrive.Direction.FORWARD, INIT_FORWARD_DISTANCE, 0.5f));
                addAction(new BulkExecuteAction(blockActions));
                addAction(new MoveAction(OmniDrive.Direction.RIGHT, INIT_STRAFE_DISTANCE, 0.5f));

                // Find a skystone
                findSkystoneAction = new FindSkystoneAction(StoneStrafeAction.StrafeDirection.RIGHT);
                addAction(findSkystoneAction);

                // Move to where we can grip
                addAction(new MoveAction(OmniDrive.Direction.RIGHT, GRIP_ALIGN_DISTANCE, 0.5f));

                // Simultaneously lift, open grip, and move forward.

                addAction(new MoveAction(OmniDrive.Direction.FORWARD, 14, 0.5f));
                addAction(new SensorMoveAction(4, false));

                // Lower and grip
                addAction(new FasterLiftAction(0));
                addAction(new GripAction(true));
                addAction(new FasterLiftAction(1.25));

                // Move back and to building area, release stone
                addAction(new MoveAction(OmniDrive.Direction.BACKWARD, 10, 0.5f));
                addAction(new UnstrafeAction(findSkystoneAction));
                addAction(new MoveAction(OmniDrive.Direction.LEFT, 70.5, 1f));
                addAction(new FasterLiftAction(8));
                addAction(new MoveAction(OmniDrive.Direction.FORWARD, 12, 0.5f));
                addAction(new GripAction(false));
                addAction(new LowerPlatformGripAction());
                addAction(new MoveAction(OmniDrive.Direction.BACKWARD, 12, 0.5f));

                break;
        }

    }
}
