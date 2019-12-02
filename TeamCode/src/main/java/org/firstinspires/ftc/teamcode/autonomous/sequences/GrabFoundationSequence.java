package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.MecanumMoveAction;
import org.firstinspires.ftc.teamcode.action.WaitAction;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class GrabFoundationSequence extends ActionSequence {

    static final float SPEED = 0.5f;

    static final double PASS_DELAY = 0;
    static final double DIST_FOUNDATION = 0;

    static final double TIMEOUT_FOUNDATION = 4000;

    public GrabFoundationSequence() {
        // Potentially wait for passing robots
        addAction(new WaitAction(PASS_DELAY));

        // Move forward to foundation
        addAction(new MecanumMoveAction(OmniDrive.Direction.FORWARD, DIST_FOUNDATION, SPEED, TIMEOUT_FOUNDATION));
        // Grab foundation
        // Move foundation to building site
        // Release foundation
        // Park under skybridge if possible
    }
}
