package org.firstinspires.ftc.teamcode.hybridop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.SkystoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.GamepadListener;
import org.firstinspires.ftc.teamcode.playmaker.HybridOp;
import org.firstinspires.ftc.teamcode.playmaker.GamepadController.GamepadType;
import org.firstinspires.ftc.teamcode.playmaker.GamepadController.GamepadButtons;

@TeleOp(name="SkyStone", group="HybridOp")
public class SkyStoneHybridOp extends HybridOp {
    public SkyStoneHybridOp() {
        super();
        this.hardware = new SkystoneRobotHardware(this);
    }
}
