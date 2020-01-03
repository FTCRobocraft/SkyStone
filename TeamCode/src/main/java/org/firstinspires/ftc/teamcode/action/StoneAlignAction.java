package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

import java.util.List;

public class StoneAlignAction implements Action {

    public static final double HORIZONTAL_DEADZONE = 10;
    public static final double WIDTH_DEADZONE = 10;
    public static final float ALIGN_SPEED = 0;
    double desiredWidth;

    public StoneAlignAction(double desiredWidth) {
        this.desiredWidth = desiredWidth;
    }

    @Override
    public void init(RobotHardware hardware) {

    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        hardware.omniDrive.stopDrive();

        if (hardware.tfod != null) {
            List<Recognition> updatedRecognitions = hardware.tfod.getUpdatedRecognitions();
            Recognition closestStone = null;

            // Find closest stone by looking for widest block
            for (Recognition recognition : updatedRecognitions) {
                float stoneWidth = recognition.getWidth();

                if (closestStone != null) {
                    if (stoneWidth >= closestStone.getWidth()) {
                        closestStone = recognition;
                    }
                } else {
                    closestStone = recognition;
                }
            }

            if (closestStone != null) {
                double targetCenterDisplacement = 400.0 - (closestStone.getLeft() + (closestStone.getWidth() / 2.0));
                if (Math.abs(targetCenterDisplacement) > HORIZONTAL_DEADZONE) {
                    if (targetCenterDisplacement > 0) {
                        hardware.omniDrive.moveLeft(ALIGN_SPEED);
                    } else {
                        hardware.omniDrive.moveRight(ALIGN_SPEED);
                    }
                } else if (Math.abs(desiredWidth - closestStone.getWidth()) > WIDTH_DEADZONE) {
                    if (desiredWidth - closestStone.getWidth() > 0) {
                        hardware.omniDrive.moveBackward(ALIGN_SPEED);
                    } else {
                        hardware.omniDrive.moveForward(ALIGN_SPEED);
                    }
                } else {
                    // At this point we should be aligned?
                    hardware.omniDrive.stopDrive();
                    return true;
                }
            }


        }

        return false;
    }

    @Override
    public Double progress() {
        return null;
    }

    @Override
    public String progressString() {
        return null;
    }
}
