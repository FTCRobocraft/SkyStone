package org.firstinspires.ftc.teamcode.action;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.BaseHardware;

public class EncoderToPositionAction implements Action {

    DcMotor motor;
    ElapsedTime runTime;
    DcMotor.RunMode previousRunMode;

    String name;
    int position;
    double speed;
    double timeout;

    public EncoderToPositionAction(String name, int position, double speed, double timeout) {
        this.name = name;
        this.position = position;
        this.speed = speed;
        this.timeout = timeout;
    }

    /**
     * Function called when the action is first executed
     *
     * @param hardware
     */
    @Override
    public void init(BaseHardware hardware) {
        motor = hardware.hardwareMap.dcMotor.get(this.name);
        previousRunMode = motor.getMode();
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setTargetPosition(motor.getCurrentPosition() + position);
        runTime = new ElapsedTime();
        runTime.reset();
    }

    /**
     * Function that is called for every iteration of the OpMode loop
     *
     * @param hardware
     * @return Return true when the action is complete.
     */
    @Override
    public boolean doAction(BaseHardware hardware) {
        if (motor.isBusy() && runTime.milliseconds() < timeout) {
            motor.setPower(speed);
            hardware.telemetry.addData("ENCODER", String.format("%d -> %d",
                    motor.getCurrentPosition(),
                    motor.getTargetPosition()));
        } else {
            return true;
        }
        return false;
    }
}
