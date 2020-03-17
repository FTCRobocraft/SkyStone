package org.firstinspires.ftc.teamcode.util;

import org.firstinspires.ftc.teamcode.hardware.BaseHardware;

import static org.firstinspires.ftc.teamcode.hardware.BaseHardware.Team.RED;

public class TeamMeasurements {

    BaseHardware.Team team;
    BaseHardware.Team baseTeam = BaseHardware.Team.BLUE;

    public TeamMeasurements(BaseHardware.Team team) {
        this.team = team;
    }

    public void setBaseTeam(BaseHardware.Team baseTeam) {
        this.baseTeam = baseTeam;
    }

    public OmniDrive.Direction latDirection(OmniDrive.Direction direction) {
        if (team == baseTeam) {
            return direction;
        } else {
            switch (direction) {
                case LEFT:
                    return OmniDrive.Direction.RIGHT;
                case RIGHT:
                    return OmniDrive.Direction.LEFT;
                default:
                    return direction;
            }
        }
    }

    public double val(double val) {
        return team == baseTeam ? val : -val;
    }

    public int val(int val) {
        return team == baseTeam ? val : -val;
    }

    public float val(float val) {
        return team == baseTeam ? val : -val;
    }

    public <E> E tval(E rval, E bval) {
        return team == RED ? rval : bval;
    }
}
