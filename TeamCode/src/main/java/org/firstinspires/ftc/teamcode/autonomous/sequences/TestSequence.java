package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.GripAction;
import org.firstinspires.ftc.teamcode.action.LiftMotorAction;
import org.firstinspires.ftc.teamcode.action.LowerLiftAction;
import org.firstinspires.ftc.teamcode.action.MoveAction;
import org.firstinspires.ftc.teamcode.action.RotateCameraAction;
import org.firstinspires.ftc.teamcode.action.SetHorizontalArmAction;
import org.firstinspires.ftc.teamcode.action.SkystoneHorizontalAlignAction;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class TestSequence extends ActionSequence {

    public TestSequence() {
        //addAction(new MoveAction(OmniDrive.Direction.FORWARD, 12, 0.5f, 3000));
        addAction(new RotateCameraAction(0));
        addAction(new SkystoneHorizontalAlignAction(SkystoneHorizontalAlignAction.ScanDirection.RIGHT, 10000));
        addAction(new MoveAction(OmniDrive.Direction.RIGHT, 8, 0.4f, 1000));
        addAction(new LiftMotorAction(500));
        addAction(new GripAction(false));
        addAction(new MoveAction(OmniDrive.Direction.FORWARD, 16, 0.4f, 3000));
        addAction(new SetHorizontalArmAction(170));
        addAction(new LowerLiftAction());
        addAction(new GripAction(true));
        addAction(new LiftMotorAction(500));
        addAction(new SetHorizontalArmAction(0));
        addAction(new MoveAction(OmniDrive.Direction.BACKWARD, 16, 0.4f, 3000));

    }


}