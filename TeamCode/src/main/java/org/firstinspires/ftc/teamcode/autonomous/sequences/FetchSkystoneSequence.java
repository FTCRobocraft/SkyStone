package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.BulkExecuteAction;
import org.firstinspires.ftc.teamcode.action.DirectionalMoveAction;
import org.firstinspires.ftc.teamcode.action.LiftMotorAction;
import org.firstinspires.ftc.teamcode.action.LowerLiftAction;
import org.firstinspires.ftc.teamcode.action.MecanumRotationAction;
import org.firstinspires.ftc.teamcode.action.SkystoneHorizontalAlignAction;
import org.firstinspires.ftc.teamcode.action.GripAction;
import org.firstinspires.ftc.teamcode.hardware.BaseHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

import java.util.ArrayList;

public class FetchSkystoneSequence extends ActionSequence {

    // How long to wait for a robot to pass, if any.
    public final double PASSING_TIME = 0;

    public FetchSkystoneSequence(BaseHardware.Team team) {
        switch (team) {

            case RED:
                break;
            case BLUE:
                addAction(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, 9.5, 0.5f, 4));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.RIGHT, 6, 0.5f, 4));
                addAction(new SkystoneHorizontalAlignAction(SkystoneHorizontalAlignAction.ScanDirection.RIGHT, 10000));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.RIGHT, 5, 0.6f, 4));
                addAction(new MecanumRotationAction(-5, 0.5f));
                ArrayList<Action> blockActions = new ArrayList<>();
                blockActions.add(new LiftMotorAction(500));
                blockActions.add(new GripAction(false));
                blockActions.add(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, 22, 0.6f, 8f));
                addAction(new BulkExecuteAction(blockActions));
                addAction(new LiftMotorAction(0));
                addAction(new GripAction(true));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.BACKWARD, 22, 0.8f, 8f));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.LEFT, 60, 1f, 12f));
                addAction(new GripAction(false));
                addAction(new LiftMotorAction(200));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.RIGHT, 6, 0.5f, 4));
                addAction(new LiftMotorAction(0));
                addAction(new DirectionalMoveAction(OmniDrive.Direction.RIGHT, 12, 0.8f, 8));
                break;
        }

    }
}
