package org.firstinspires.ftc.teamcode.playmaker;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_GOLD_MINERAL;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_SILVER_MINERAL;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.TFOD_MODEL_ASSET;

/**
 * Created by djfigs1 on 11/18/16.
 */

public class ActionExecutor {

    public ActionSequence actionSequence;
    public boolean initVuforia = false;
    public boolean initTFOD = false;
    public boolean enableFlashlight = true;
    private Action action = null;
    private RobotHardware hardware;

    public ActionExecutor(RobotHardware hardware, ActionSequence actionSequence) {
        this.hardware = hardware;
        this.actionSequence = actionSequence;
    }

    public void init() {
        this.actionSequence.initializeSequence();

        if (initVuforia) {
            VuforiaLocalizer.Parameters parameters;
            if (initTFOD) {
                parameters = new VuforiaLocalizer.Parameters();
            } else {
                int cameraMonitorViewId = hardware.opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardware.opMode.hardwareMap.appContext.getPackageName());
                parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
            }

            parameters.vuforiaLicenseKey = hardware.vuforiaKey;
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
            hardware.vuforia = ClassFactory.getInstance().createVuforia(parameters);
            com.vuforia.CameraDevice.getInstance().setFlashTorchMode(enableFlashlight);
        }

        if (initTFOD) {
            int tfodMonitorViewId = hardware.opMode.hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", hardware.opMode.hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            hardware.tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, hardware.vuforia);
            hardware.tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
        }

    }

    public void start() {
        if (initTFOD) {
            hardware.tfod.activate();
        }

    }

    public void stop() {
        if (initVuforia) {
            com.vuforia.CameraDevice.getInstance().setFlashTorchMode(false);
        }
        if (initTFOD) {
            hardware.tfod.deactivate();
        }
    }

    int actionNumber = 1;
    boolean didInit = false;

    public boolean loop() {
        action = actionSequence.getCurrentAction();

        if (action != null) {
            if (!didInit) {
                action.init(hardware);
                didInit = true;
            }

            if (action.doAction(hardware)) {
                actionSequence.currentActionComplete();
                actionNumber++;
                didInit = false;
            } else {
                hardware.opMode.telemetry.addData("Sequence Progress", "%d/%d, %d%%", actionNumber, actionSequence.numberOfActions(),
                        (int) ((double) actionNumber / (double) actionSequence.numberOfActions() * 100.0));
                hardware.opMode.telemetry.addData("Current Action", action.getClass().getSimpleName());
                if (action.progress() != null) {
                    hardware.opMode.telemetry.addData("Action %", "%.2f%%", action.progress()*100.0);
                }

                if (action.progressString() != null) {
                    hardware.opMode.telemetry.addData("Action Progress", action.progressString());
                }
            }
            return false;
        } else {
            return true;
        }
    }
}

