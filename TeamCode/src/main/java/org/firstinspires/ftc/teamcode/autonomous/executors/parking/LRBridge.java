package org.firstinspires.ftc.teamcode.autonomous.executors.parking;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.sequences.ParkingSequence;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.playmaker.AutonomousExecutor;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

@Autonomous(name="LR -> Bridge", group="Parking")
public class LRBridge extends AutonomousExecutor {
    @Override
    public RobotHardware getHardware() {
        return new SkyStoneRobotHardware(this);
    }

    @Override
    public ActionSequence getActionSequence() {
        return new ParkingSequence(SkyStoneRobotHardware.SkystoneStartingPosition.LEFT_RIGHT, ParkingSequence.ParkingDestination.BRIDGE);
    }
}