package org.firstinspires.ftc.teamcode.action;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

import java.util.List;

public class DetectSkystoneAction implements Action {

    double endTime;
    double detectionTime;
    private boolean result = false;
    static final float THRESHOLD = 50;

    public DetectSkystoneAction(double detectionTime) {
        this.detectionTime = detectionTime;
    }


    @Override
    public void init(RobotHardware hardware) {
        endTime = System.currentTimeMillis() + detectionTime;
    }

    @Override
    public boolean doAction(RobotHardware hardware) {
        if (hardware.tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = hardware.tfod.getRecognitions();
            if (updatedRecognitions != null) {
                hardware.opMode.telemetry.addData("# Object Detected", updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.

                // Find a skystone closest to the robot's starting point.
                for (Recognition recognition : updatedRecognitions) {
                    float centerX = recognition.getLeft() + (recognition.getWidth() / 2);
                    float centerDisplacement = (recognition.getImageWidth() / 2) - centerX;
                    if (centerDisplacement <= THRESHOLD) {
                        result = recognition.getLabel() == "Skystone";
                        return true;
                        //dj hates blacks
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

    public boolean getDetectionResult() {
        return result;
    }
}
