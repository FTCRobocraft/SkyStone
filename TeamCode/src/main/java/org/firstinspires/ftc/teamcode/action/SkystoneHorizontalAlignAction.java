package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

import java.util.List;

public class SkystoneHorizontalAlignAction implements Action {

    public enum ScanDirection {
        LEFT,
        RIGHT
    }

    static final double SCAN_SPEED = 0.3f;
    static final double TOLERANCE = 10;


    ScanDirection scanDirection;
    double timeout;
    double endTime;

    public SkystoneHorizontalAlignAction(ScanDirection direction, double timeout) {
        this.scanDirection = direction;
        this.timeout = timeout;
    }


    @Override
    public void init(RobotHardware hardware) {
        endTime = System.currentTimeMillis() + timeout;
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware.tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.

            if (scanDirection == ScanDirection.RIGHT) {
                hardware.omniDrive.moveRight(SCAN_SPEED);
            } else {
                hardware.omniDrive.moveLeft(SCAN_SPEED);
            }

            List<Recognition> updatedRecognitions = hardware.tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                hardware.opMode.telemetry.addData("# Object Detected", updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.
                Recognition skystone = null;

                // Find a skystone closest to the robot's starting point.
                for (Recognition recognition : updatedRecognitions) {
                    if (recognition.getLabel() == "Skystone") {
                        if (skystone == null) {
                            skystone = recognition;
                        } else {
                            if (scanDirection == ScanDirection.RIGHT) {
                                skystone = recognition.getLeft() < skystone.getLeft() ? recognition : skystone;
                            } else {
                                skystone = recognition.getRight() > skystone.getRight() ? recognition : skystone;
                            }
                        }
                        break;
                    }
                }



                if (skystone != null) {
                    float centerX = skystone.getLeft() + (skystone.getWidth() / 2);
                    hardware.opMode.telemetry.addData("centerX", centerX);
                    float centerDisplacement = (skystone.getImageWidth() / 2) - centerX;
                    hardware.opMode.telemetry.addData("center displacement", centerDisplacement);
                    if (Math.abs(centerDisplacement) <= TOLERANCE) {
                        hardware.omniDrive.stopDrive();
                        return true;
                    }
                }
            }
        }


        return System.currentTimeMillis() >= endTime;
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
