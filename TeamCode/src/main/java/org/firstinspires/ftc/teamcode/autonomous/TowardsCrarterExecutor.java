package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.autonomous.sequences.RoverRuckusSequence;
import org.firstinspires.ftc.teamcode.autonomous.sequences.TestSequence;
import org.firstinspires.ftc.teamcode.hardware.RoverRuckusHardware;
import org.firstinspires.ftc.teamcode.util.ActionExecutor;

/**
 * Created by djfigs1 on 1/20/18.
 */

@Disabled
@Autonomous(name = "Towards Crater")
public class TowardsCrarterExecutor extends ActionExecutor {

    RoverRuckusSequence roverRuckusSequence;

    @Override
    public void init() {
        this.initVuforia = true;
        this.initTFOD = true;
        this.enableFlashlight = false;
        super.init();
        roverRuckusSequence = new RoverRuckusSequence(RoverRuckusStartingPosition.TOWARDS_CRATER);
        this.actionSequence = roverRuckusSequence;
    }
}
