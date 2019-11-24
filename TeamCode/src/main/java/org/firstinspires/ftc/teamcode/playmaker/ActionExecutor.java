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
    private Action action = null;
    private RobotHardware hardware;

    public ActionExecutor(RobotHardware hardware, ActionSequence actionSequence) {
        this.hardware = hardware;
        this.actionSequence = actionSequence;
    }

    public void init() {
        this.actionSequence.initializeSequence();
    }


    public void stop() {

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

