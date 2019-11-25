package org.firstinspires.ftc.teamcode.hybridop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.action.WaitAction;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.hardware.TestHardware;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.playmaker.GamepadController;
import org.firstinspires.ftc.teamcode.playmaker.GamepadListener;
import org.firstinspires.ftc.teamcode.playmaker.HybridOp;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

@TeleOp(name="ManualDrive", group="HybridOp")
public class TestHybridOp extends HybridOp {

    @Override
    public RobotHardware getHardware() {
        return new SkyStoneRobotHardware(this);
    }


    @Override
    public void init() {
        super.init();
        SkyStoneRobotHardware skyStoneRobotHardware = (SkyStoneRobotHardware) hardware;
        skyStoneRobotHardware.initVuforia();
        skyStoneRobotHardware.startTracking();

    }

    @Override
    public void loop() {
        super.loop();
        SkyStoneRobotHardware skyStoneRobotHardware = (SkyStoneRobotHardware) hardware;

        if (skyStoneRobotHardware.cameraNavigation.isTargetVisible()) {
            VectorF position = skyStoneRobotHardware.cameraNavigation.getRobotPosition();
            telemetry.addData("Pos", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    position.get(0), position.get(1), position.get(2));

            // express the rotation of the robot in degrees.
            Orientation rotation = skyStoneRobotHardware.cameraNavigation.getRobotRotation();
            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
        }
    }
}
