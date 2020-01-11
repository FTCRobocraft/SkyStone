package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.DirectionalMoveAction;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class TestSequence extends ActionSequence {

    public TestSequence() {
        //addAction(new MoveAction(OmniDrive.Direction.FORWARD, 12, 0.5f, 3000));
        //addAction(new BetterMoveAction(OmniDrive.Direction.FORWARD, 24, 0.75f));

        addAction(new DirectionalMoveAction(OmniDrive.Direction.FORWARD, 12, 0.4f, 8));
        addAction(new DirectionalMoveAction(OmniDrive.Direction.RIGHT, 12, 0.4f, 8));
        addAction(new DirectionalMoveAction(OmniDrive.Direction.BACKWARD, 12, 0.4f, 8));
        addAction(new DirectionalMoveAction(OmniDrive.Direction.LEFT, 12, 0.4f, 8));

        /*addAction(new RotateCameraAction(-9.2f));
        addAction(new SkystoneHorizontalAlignAction(SkystoneHorizontalAlignAction.ScanDirection.RIGHT, 10000));
        addAction(new BetterMoveAction(OmniDrive.Direction.RIGHT, 5.5, 0.6f));
        addAction(new LiftMotorAction(500));
        addAction(new GripAction(false));
        addAction(new BetterMoveAction(OmniDrive.Direction.FORWARD, 17.5, 0.8f));
        addAction(new LowerLiftAction());
        addAction(new GripAction(true));
        addAction(new LiftMotorAction(500));
        addAction(new BetterMoveAction(OmniDrive.Direction.BACKWARD, 17.5, 0.8f));*/

    }


}