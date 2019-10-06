package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.RobotHardware;

/**
 * Created by djfig on 12/12/2016.
 */
public class WaitAction implements Action {
    double time;
    double endTime;

    /**
     *
     * @param time Time in milliseconds for the action to wait.
     */
    public WaitAction(double time){
        this.time = time;
    }

    @Override
    public void init(RobotHardware hardware) {
        endTime = System.currentTimeMillis() + time;
    }

    @Override
    public boolean doAction(RobotHardware hardware){
        return (System.currentTimeMillis() >= endTime);
    }
}
