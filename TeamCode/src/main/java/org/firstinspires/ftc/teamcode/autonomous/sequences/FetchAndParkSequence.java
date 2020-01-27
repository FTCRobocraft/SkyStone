package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.BulkExecuteAction;
import org.firstinspires.ftc.teamcode.action.DirectionalMoveAction;
import org.firstinspires.ftc.teamcode.action.LiftMotorAction;
import org.firstinspires.ftc.teamcode.action.LowerLiftAction;
import org.firstinspires.ftc.teamcode.action.MecanumRotationAction;
import org.firstinspires.ftc.teamcode.action.MoveAction;
import org.firstinspires.ftc.teamcode.action.SkystoneHorizontalAlignAction;
import org.firstinspires.ftc.teamcode.action.GripAction;
import org.firstinspires.ftc.teamcode.hardware.BaseHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

import java.util.ArrayList;

public class FetchAndParkSequence extends ActionSequence {

    // How long to wait for a robot to pass, if any.
    public final double PASSING_TIME = 0;
    public final double INIT_FORWARD_DISTANCE = 12;
    public final double INIT_STRAFE_DISTANCE = 6;
    public final double GRIP_ALIGN_DISTANCE = 10;
    public final double JANK_COMPENSATION = 3;

    public FetchAndParkSequence(BaseHardware.Team team, BaseHardware.StartingPosition startingPosition) {
        switch (team) {
            case RED:
                // Move to starting point for scanning
                addAction(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, INIT_FORWARD_DISTANCE, 0.8f, 4));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.LEFT, INIT_STRAFE_DISTANCE, 0.5f, 4));

                // Find a skystone
                addAction(new SkystoneHorizontalAlignAction(SkystoneHorizontalAlignAction.ScanDirection.LEFT, 10000));

                // Move to where we can grip
                addAction(new MoveAction(OmniDrive.Direction.RIGHT, GRIP_ALIGN_DISTANCE, 0.4f, 1000));

                // Rotate to counteract any veer when strafing
                //addAction(new MecanumRotationAction(-5, 0.5f));

                // Simultaneously lift, open grip, and move forward.
                ArrayList<Action> redBlockActions = new ArrayList<>();
                redBlockActions.add(new LiftMotorAction(500));
                redBlockActions.add(new GripAction(false));
                redBlockActions.add(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, 21, 0.4f, 8f));
                addAction(new BulkExecuteAction(redBlockActions));

                // Lower and grip
                addAction(new LiftMotorAction(0));
                addAction(new GripAction(true));

                // Move back and to building area, release stone
                addAction(new MoveAction(OmniDrive.Direction.BACKWARD, 8, 0.5f, 2000));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.RIGHT, 60, 1f, 12f));
                addAction(new GripAction(false));

                // Lower lift and park.
                addAction(new LiftMotorAction(200));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.LEFT, 6, 0.5f, 4));
                addAction(new LiftMotorAction(0));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.LEFT, 12, 0.8f, 8));
                break;
            case BLUE:
                // Move to starting point for scanning
                addAction(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, INIT_FORWARD_DISTANCE, 0.8f, 5));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.RIGHT, INIT_STRAFE_DISTANCE, 0.5f, 4));

                // Find a skystone
                addAction(new SkystoneHorizontalAlignAction(SkystoneHorizontalAlignAction.ScanDirection.RIGHT, 10000));

                // Move to where we can grip
                addAction(new MoveAction(OmniDrive.Direction.RIGHT, GRIP_ALIGN_DISTANCE - JANK_COMPENSATION, 0.4f, 1000));

                // Rotate to counteract any veer when strafing
                //addAction(new MecanumRotationAction(-5, 0.5f));

                // Simultaneously lift, open grip, and move forward.
                ArrayList<Action> blockActions = new ArrayList<>();
                blockActions.add(new LiftMotorAction(500));
                blockActions.add(new GripAction(false));
                blockActions.add(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, 21, 0.4f, 8f));
                addAction(new BulkExecuteAction(blockActions));

                // Lower and grip
                addAction(new LiftMotorAction(0));
                addAction(new GripAction(true));

                // Move back and to building area, release stone
                addAction(new MoveAction(OmniDrive.Direction.BACKWARD, 8, 0.5f, 2000));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.LEFT, 60, 1f, 12f));
                addAction(new GripAction(false));

                // Lower lift and park.
                addAction(new LiftMotorAction(200));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.RIGHT, 6, 0.5f, 4));
                addAction(new LiftMotorAction(0));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.RIGHT, 12, 0.8f, 8));
                break;
        }

    }
}
