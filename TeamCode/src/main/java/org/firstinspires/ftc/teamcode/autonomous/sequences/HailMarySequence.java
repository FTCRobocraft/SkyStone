package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.BulkExecuteAction;
import org.firstinspires.ftc.teamcode.action.FasterLiftAction;
import org.firstinspires.ftc.teamcode.action.FindSkystoneAction;
import org.firstinspires.ftc.teamcode.action.GripAction;
import org.firstinspires.ftc.teamcode.action.LiftAction;
import org.firstinspires.ftc.teamcode.action.LowerPlatformGripAction;
import org.firstinspires.ftc.teamcode.action.MoveAction;
import org.firstinspires.ftc.teamcode.action.RaisePlatformGripAction;
import org.firstinspires.ftc.teamcode.action.SensorMoveAction;
import org.firstinspires.ftc.teamcode.action.StoneStrafeAction;
import org.firstinspires.ftc.teamcode.action.UnstrafeAction;
import org.firstinspires.ftc.teamcode.action.WaitAction;
import org.firstinspires.ftc.teamcode.hardware.BaseHardware;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.OmniDrive;
import org.firstinspires.ftc.teamcode.util.OmniDrive.Direction;
import org.firstinspires.ftc.teamcode.util.TeamMeasurements;

import java.util.ArrayList;

public class HailMarySequence extends ActionSequence {
    TeamMeasurements tm;


    float FAST_SPEED = 1f;
    float NORM_SPEED = 0.7f;
    float STRAFE_SPEED = 0.65f;
    float SLOW_SPEED = 0.5f;

    double WALL_EXTRA_DIST = 4;
    double WALL_TO_BLOCKS = 18 + WALL_EXTRA_DIST;
    double STRAFE_DIST_TO_SCAN_B = 8.5;
    double STRAFE_DIST_TO_SCAN_R = STRAFE_DIST_TO_SCAN_B + 14;
    double GRIP_ALIGN_DISTANCE = 3.5;


    FindSkystoneAction findSkystoneAction;
    //dj is big brain i did fancy stuff

    public HailMarySequence(BaseHardware.Team team, boolean park, boolean parkUnderBridge) {
        tm = new TeamMeasurements(team);
        tm.setBaseTeam(BaseHardware.Team.BLUE);

        // Move to starting point for scanning

        ArrayList<Action> blockActions = new ArrayList<>();
        blockActions.add(new RaisePlatformGripAction());
        blockActions.add(new FasterLiftAction(5));
        blockActions.add(new GripAction(false));
        blockActions.add(new MoveAction(Direction.FORWARD, WALL_TO_BLOCKS, NORM_SPEED));
        addAction(new BulkExecuteAction(blockActions));
        addAction(new MoveAction(tm.latDirection(OmniDrive.Direction.RIGHT), tm.tval(STRAFE_DIST_TO_SCAN_R, STRAFE_DIST_TO_SCAN_B), STRAFE_SPEED));

        // Find a skystone like a boss
        findSkystoneAction = new FindSkystoneAction(tm.tval(StoneStrafeAction.StrafeDirection.LEFT, StoneStrafeAction.StrafeDirection.RIGHT));
        addAction(findSkystoneAction);

        // Move to where we can grip
        addAction(new MoveAction(Direction.RIGHT, GRIP_ALIGN_DISTANCE, STRAFE_SPEED));

        // Simultaneously lift, open grip, and move forward. no way boomer

        addAction(new MoveAction(Direction.FORWARD, 14 - WALL_EXTRA_DIST, NORM_SPEED));
        addAction(new SensorMoveAction(4, false));

        // Lower and grip
        addAction(new FasterLiftAction(0));
        addAction(new GripAction(true));
        addAction(new LiftAction(SkyStoneRobotHardware.LIFT_NO_DRAG_HEIGHT));


        // Move back and to building area, release stone
        addAction(new MoveAction(OmniDrive.Direction.BACKWARD, 10, NORM_SPEED));
        addAction(new UnstrafeAction(findSkystoneAction));
        addAction(new MoveAction(tm.latDirection(Direction.LEFT), 70.5, FAST_SPEED));
        ArrayList<Action> platformActions = new ArrayList<Action>();
        platformActions.add(new FasterLiftAction(8));
        platformActions.add(new MoveAction(OmniDrive.Direction.FORWARD, 10, SLOW_SPEED));
        addAction(new BulkExecuteAction(platformActions));
        addAction(new SensorMoveAction(3, false));
        addAction(new LowerPlatformGripAction());
        addAction(new WaitAction(300));
        addAction(new GripAction(false));
        addAction(new WaitAction(200));
        addAction(new MoveAction(OmniDrive.Direction.BACKWARD, 35.5, NORM_SPEED));
        addAction(new RaisePlatformGripAction());
        addAction(new WaitAction(200));

        ArrayList<Action> parkingActions = new ArrayList<Action>();
        if (park) {
            if (parkUnderBridge) {
                parkingActions.add(new MoveAction(tm.latDirection(Direction.RIGHT), 27, FAST_SPEED));
                parkingActions.add(new FasterLiftAction(0));
                addAction(new BulkExecuteAction(parkingActions));
                addAction(new MoveAction(Direction.FORWARD, 24, FAST_SPEED));
                addAction(new MoveAction(tm.latDirection(Direction.RIGHT), 18, FAST_SPEED));
            } else {
                parkingActions.add(new MoveAction(tm.latDirection(Direction.RIGHT), 45, FAST_SPEED));
                parkingActions.add(new FasterLiftAction(0));
                addAction(new BulkExecuteAction(parkingActions));
            }
        } else {
            addAction(new MoveAction(tm.latDirection(Direction.RIGHT), 8, STRAFE_SPEED));
            addAction(new FasterLiftAction(0));
        }



    }

}
