package org.firstinspires.ftc.teamcode.autonomous.executors;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.sequences.HailMarySequence;
import org.firstinspires.ftc.teamcode.hardware.BaseHardware;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.playmaker.AutonomousExecutor;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

@Autonomous(name="HAIL MARY BUT RED BRIDGE", group="hm")
public class HailMaryRedBridgeExecutor extends AutonomousExecutor {
    @Override
    public RobotHardware getHardware() {
        return new SkyStoneRobotHardware(this);
    }

    @Override
    public ActionSequence getActionSequence() {
        return new HailMarySequence(BaseHardware.Team.RED, true, true);
    }

    @Override
    public void init() {
        super.init();
        ((SkyStoneRobotHardware) hardware).startTracking();
    }
}
