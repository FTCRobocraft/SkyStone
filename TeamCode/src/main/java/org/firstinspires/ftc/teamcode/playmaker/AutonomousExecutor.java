package org.firstinspires.ftc.teamcode.playmaker;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public abstract class AutonomousExecutor extends OpMode {

    private RobotHardware hardware;
    private ActionSequence actionSequence;
    private ActionExecutor actionExecutor;

    public abstract RobotHardware getHardware();

    public abstract ActionSequence getActionSequence();

    @Override
    public void init() {
        hardware = getHardware();
        hardware.initializeHardware();
        hardware.initializeAutonomous();
        actionExecutor = new ActionExecutor(hardware, actionSequence);
    }

    @Override
    public void start() {
        actionExecutor.start();
    }

    @Override
    public void loop() {
        actionExecutor.loop();
    }

    @Override
    public void stop() {
        actionExecutor.stop();
    }
}
