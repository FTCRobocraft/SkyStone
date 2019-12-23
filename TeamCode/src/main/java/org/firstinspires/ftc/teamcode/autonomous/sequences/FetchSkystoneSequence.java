package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.AlignWithSkystoneAction;
import org.firstinspires.ftc.teamcode.action.BetterMoveAction;
import org.firstinspires.ftc.teamcode.action.GrabStoneAction;
import org.firstinspires.ftc.teamcode.action.MarkerNavigation;
import org.firstinspires.ftc.teamcode.action.MoveAction;
import org.firstinspires.ftc.teamcode.action.RotateCameraAction;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class FetchSkystoneSequence extends ActionSequence {

    public FetchSkystoneSequence() {
        addAction(new BetterMoveAction(OmniDrive.Direction.FORWARD, 12, 0.5f));
        addAction(new AlignWithSkystoneAction(AlignWithSkystoneAction.ScanDirection.RIGHT, 10000));
        addAction(new GrabStoneAction());
        addAction(new RotateCameraAction(-90));
        addAction(new MarkerNavigation(48, MarkerNavigation.Axis.X, MarkerNavigation.AlignDirection.LEFT_RIGHT, 0.5f));

    }
}
