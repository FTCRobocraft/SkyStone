package org.firstinspires.ftc.teamcode.hybridop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.HybridOp;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

@TeleOp(name="TeleOp")
public class SkystoneHybridOp extends HybridOp {

    private static final float DPAD_POWER = 1f;
    private static final float SPINNER_SPEED = 1f;

    @Override
    public RobotHardware getHardware() {
        return new SkyStoneRobotHardware(this);
    }

    @Override
    public void hybrid_loop() {
        SkyStoneRobotHardware skystoneHardware = (SkyStoneRobotHardware) this.hardware;
        skystoneHardware.spinnerLeft.setPower(SPINNER_SPEED);
        skystoneHardware.spinnerRight.setPower(SPINNER_SPEED);
    }

    @Override
    public void teleop_loop() {
        this.hardware.omniDrive.dpadMove(gamepad1, DPAD_POWER, false);
        this.hardware.omniDrive.circleMove(gamepad1.right_stick_x, gamepad1.right_stick_y);
    }
}
