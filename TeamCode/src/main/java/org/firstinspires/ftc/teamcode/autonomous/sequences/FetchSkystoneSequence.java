package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.action.MecanumMoveAction;
import org.firstinspires.ftc.teamcode.action.WaitAction;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.EncoderDrive;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public class FetchSkystoneSequence extends ActionSequence {

    // How long to wait for a robot to pass, if any.
    public final double PASSING_TIME = 0;

    // Distances
    public EncoderDrive.Distance 

    public FetchSkystoneSequence() {

        // TODO: Finish Skystone Sequence
        // Potentially wait for passing robots
        addAction(new WaitAction(PASSING_TIME));

        // Navigate under bridge


        addAction(new MecanumMoveAction());

        // Search for Skystone #1
        // Grab skystone
        // Navigate under bridge to building site
        // Place skystone
        // Navigate under bridge
        // Search for Skystone #2
        // Grab skystone
        // Navigate under bridge to building site
        // Place skystone
        // Park under bridge
    }
}
