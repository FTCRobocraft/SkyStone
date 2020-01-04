package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.SkystoneHorizontalAlignAction;
import org.firstinspires.ftc.teamcode.action.BetterMoveAction;
import org.firstinspires.ftc.teamcode.action.GripAction;
import org.firstinspires.ftc.teamcode.action.AlignXAction;
import org.firstinspires.ftc.teamcode.action.RotateCameraAction;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class FetchSkystoneSequence extends ActionSequence {

    // How long to wait for a robot to pass, if any.
    public final double PASSING_TIME = 0;

    // Distances
    //public EncoderDrive.Distance

    public FetchSkystoneSequence() {
        addAction(new BetterMoveAction(OmniDrive.Direction.FORWARD, 12, 0.5f));
        addAction(new SkystoneHorizontalAlignAction(SkystoneHorizontalAlignAction.ScanDirection.RIGHT, 10000));
        addAction(new GripAction(true));
        addAction(new RotateCameraAction(-90));
        addAction(new AlignXAction(48, AlignXAction.AlignDirection.LEFT_RIGHT, 0.5f));

    }
}
