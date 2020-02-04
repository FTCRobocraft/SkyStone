package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;
import org.firstinspires.ftc.teamcode.action.StoneStrafeAction.StrafeDirection;

public class FindSkystoneAction implements Action {

    public StoneStrafeAction strafeAction;
    StrafeDirection strafeDirection;
    DetectSkystoneAction detectSkystoneAction;
    private final static double DETECTION_TIME = 1000;

    enum FindSkystoneStages {
        DETECT,
        STRAFE
    }

    FindSkystoneStages currentStage = FindSkystoneStages.DETECT;
    boolean init = true;

    public FindSkystoneAction(StrafeDirection strafeDirection) {
        this.strafeDirection = strafeDirection;
    }

    @Override
    public void init(RobotHardware hardware) {
        strafeAction = new StoneStrafeAction(strafeDirection);

    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        switch (currentStage) {
            case DETECT:
                if (init) {
                    detectSkystoneAction = new DetectSkystoneAction(DETECTION_TIME);
                    detectSkystoneAction.init(hardware);
                    init = false;
                }

                if (strafeAction.getTimesStrafed() == 2) {
                    return true;
                } else if (detectSkystoneAction.doAction(hardware)) {
                    if (detectSkystoneAction.getDetectionResult()) {
                        return true;
                    } else {
                        currentStage = FindSkystoneStages.STRAFE;
                        init = true;
                    }
                }
                break;

            case STRAFE:
                if (init) {
                    strafeAction.init(hardware);
                    init = false;
                }

                if (strafeAction.doAction(hardware)) {
                    currentStage = FindSkystoneStages.DETECT;
                    init = true;
                }
                break;
        }
        return false;
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
