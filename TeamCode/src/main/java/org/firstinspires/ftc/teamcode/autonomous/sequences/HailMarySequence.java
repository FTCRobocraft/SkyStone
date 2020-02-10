package org.firstinspires.ftc.teamcode.autonomous.sequences;

import org.firstinspires.ftc.teamcode.action.BulkExecuteAction;
import org.firstinspires.ftc.teamcode.action.FindSkystoneAction;
import org.firstinspires.ftc.teamcode.action.GripAction;
import org.firstinspires.ftc.teamcode.action.LiftMotorAction;
import org.firstinspires.ftc.teamcode.action.LowerPlatformGripAction;
import org.firstinspires.ftc.teamcode.action.MoveAction;
import org.firstinspires.ftc.teamcode.action.RaisePlatformGripAction;
import org.firstinspires.ftc.teamcode.action.SensorMoveAction;
import org.firstinspires.ftc.teamcode.action.StoneStrafeAction;
import org.firstinspires.ftc.teamcode.action.UnstrafeAction;
import org.firstinspires.ftc.teamcode.action.WaitAction;
import org.firstinspires.ftc.teamcode.hardware.BaseHardware;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware;
import org.firstinspires.ftc.teamcode.hardware.SkyStoneRobotHardware.SkystoneStartingPosition;
import org.firstinspires.ftc.teamcode.playmaker.Action;
import org.firstinspires.ftc.teamcode.playmaker.ActionSequence;
import org.firstinspires.ftc.teamcode.util.OmniDrive;
import org.firstinspires.ftc.teamcode.util.OmniDrive.Direction;
import org.firstinspires.ftc.teamcode.util.TeamMeasurements;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HailMarySequence extends ActionSequence {

    TeamMeasurements tm;


    float FAST_SPEED = 1f;
    float NORM_SPEED = 0.7f;
    float STRAFE_SPEED = 0.6f;
    float SLOW_SPEED = 0.5f;

    double WALL_TO_BLOCKS = 18;
    double STRAFE_DIST_TO_SCAN = 8.5;
    double GRIP_ALIGN_DISTANCE = 3.5;


    FindSkystoneAction findSkystoneAction;

    public HailMarySequence(BaseHardware.Team team, SkystoneStartingPosition startingPosition) {
        boolean onBlue = team == BaseHardware.Team.BLUE;
        tm = new TeamMeasurements(team);
        tm.setBaseTeam(BaseHardware.Team.BLUE);

        // Move to starting point for scanning
        ArrayList<Action> blockActions = new ArrayList<>();
        blockActions.add(new RaisePlatformGripAction());
        blockActions.add(new LiftMotorAction(5));
        blockActions.add(new GripAction(false));
        blockActions.add(new MoveAction(OmniDrive.Direction.FORWARD, WALL_TO_BLOCKS, NORM_SPEED));
        addAction(new BulkExecuteAction(blockActions));
        addAction(new MoveAction(OmniDrive.Direction.RIGHT, STRAFE_DIST_TO_SCAN, STRAFE_SPEED));

        // Find a skystone
        findSkystoneAction = new FindSkystoneAction(StoneStrafeAction.StrafeDirection.RIGHT);
        addAction(findSkystoneAction);

        // Move to where we can grip
        addAction(new MoveAction(OmniDrive.Direction.RIGHT, GRIP_ALIGN_DISTANCE, STRAFE_SPEED));

        // Simultaneously lift, open grip, and move forward.

        addAction(new MoveAction(OmniDrive.Direction.FORWARD, 14, NORM_SPEED));
        addAction(new SensorMoveAction(4, false));

        // Lower and grip
        addAction(new LiftMotorAction(0));
        addAction(new GripAction(true));
        addAction(new LiftMotorAction(1.25));

        // Move back and to building area, release stone
        addAction(new MoveAction(OmniDrive.Direction.BACKWARD, 10, NORM_SPEED));
        addAction(new UnstrafeAction(findSkystoneAction));
        addAction(new MoveAction(OmniDrive.Direction.LEFT, 70.5, FAST_SPEED));
        ArrayList<Action> platformActions = new ArrayList<Action>();
        platformActions.add(new LiftMotorAction(8));
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
        parkingActions.add(new MoveAction(Direction.RIGHT, 45, FAST_SPEED));
        parkingActions.add(new LiftMotorAction(0));
        addAction(new BulkExecuteAction(parkingActions));
    }

}
