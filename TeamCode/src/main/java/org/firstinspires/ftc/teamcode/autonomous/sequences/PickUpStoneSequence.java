package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.FasterLiftAction;
import org.firstinspires.ftc.teamcode.action.GripAction;
import org.firstinspires.ftc.teamcode.action.SensorMoveAction;
import org.firstinspires.ftc.teamcode.action.SetHorizontalArmAction;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;

public class PickUpStoneSequence extends ActionSequence {



    public PickUpStoneSequence() {
        addAction(new SetHorizontalArmAction(0));
        addAction(new GripAction(false));
        addAction(new FasterLiftAction(5));
        //addAction(new MoveAction(OmniDrive.Direction.FORWARD, 4, 0.5f));
        addAction(new SensorMoveAction(4, false));
        addAction(new FasterLiftAction(0));
        addAction(new GripAction(true));
        addAction(new FasterLiftAction(SkyStoneRobotHardware.LIFT_NO_DRAG_HEIGHT));
        //move

    }

}
