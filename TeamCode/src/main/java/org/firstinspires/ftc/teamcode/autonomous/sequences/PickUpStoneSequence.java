package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.LowerLiftAction;
import org.firstinspires.ftc.teamcode.action.RotateCameraAction;
import org.firstinspires.ftc.teamcode.action.SetTrackingAction;
import org.firstinspires.ftc.teamcode.action.StoneAlignAction;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;

public class PickUpStoneSequence extends ActionSequence {



    public PickUpStoneSequence() {
        addAction(new RotateCameraAction(0));
        addAction(new LowerLiftAction());
        addAction(new StoneAlignAction(400));
        //move

    }

}
