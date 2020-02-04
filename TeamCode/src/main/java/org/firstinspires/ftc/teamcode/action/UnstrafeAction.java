package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class UnstrafeAction implements Action {

    MoveAction moveAction;
    FindSkystoneAction findSkystoneAction;

    public UnstrafeAction(FindSkystoneAction findSkystoneAction) {
        this.findSkystoneAction = findSkystoneAction;
    }

    @Override
    public void init(RobotHardware hardware) {
        moveAction = new MoveAction(findSkystoneAction.strafeDirection == StoneStrafeAction.StrafeDirection.RIGHT ? OmniDrive.Direction.LEFT : OmniDrive.Direction.RIGHT,
                findSkystoneAction.strafeAction.STRAFE_DISTANCE * findSkystoneAction.strafeAction.getTimesStrafed(),
                0.75f,
                10000);
        moveAction.init(hardware);

    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        return moveAction.doAction(hardware);
    }

    @Override
    public Double progress() {
        return null;
    }

    @Override
    public String progressString() {
        return null;
    }
}
