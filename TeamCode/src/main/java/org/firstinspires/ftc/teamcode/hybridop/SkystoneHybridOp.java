package org.firstinspires.ftc.teamcode.hybridop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.HybridOp;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

@TeleOp(name="TeleOp")
public class SkystoneHybridOp extends HybridOp {

    private static final float DPAD_POWER = 1f;
    private static final float SPINNER_SPEED = 1f;
    private SkyStoneRobotHardware skyStoneRobotHardware;

    @Override
    public RobotHardware getHardware() {
        skyStoneRobotHardware = new SkyStoneRobotHardware(this);
        return skyStoneRobotHardware;
    }

    @Override
    public void hybrid_loop() {
        skyStoneRobotHardware.spinnerLeft.setPower(SPINNER_SPEED);
        skyStoneRobotHardware.spinnerRight.setPower(SPINNER_SPEED);
    }

    @Override
    public void teleop_loop() {
        this.hardware.omniDrive.dpadMove(gamepad1, DPAD_POWER, false);
    }

}