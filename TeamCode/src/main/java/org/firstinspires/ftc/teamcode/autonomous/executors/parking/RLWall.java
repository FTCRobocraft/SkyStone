package org.firstinspires.ftc.teamcode.autonomous.executors.parking;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.sequences.ParkingSequence;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.playmaker.AutonomousExecutor;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

@Autonomous(name="RL -> Wall", group="Parking")
public class RLWall extends AutonomousExecutor {
    @Override
    public RobotHardware getHardware() {
        return new SkyStoneRobotHardware(this);
    }

    @Override
    public ActionSequence getActionSequence() {
        return new ParkingSequence(SkyStoneRobotHardware.SkystoneStartingPosition.RIGHT_LEFT, ParkingSequence.ParkingDestination.WALL);
    }
}