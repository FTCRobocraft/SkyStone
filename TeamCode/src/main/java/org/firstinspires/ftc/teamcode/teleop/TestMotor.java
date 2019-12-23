package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.PlatformHardware;

@TeleOp(name="TestMotor")
public class TestMotor extends PlatformHardware {


    @Override
    public void loop() {
        lift.setPower(gamepad1.left_stick_y);
        telemetry.addData("Lift Position: ", lift.getCurrentPosition());
    }

}
