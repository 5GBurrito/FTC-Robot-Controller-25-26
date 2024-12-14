package org.firstinspires.ftc.teamcode.CommandGroups.AutomatedMovements;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Translation2d;

import org.firstinspires.ftc.teamcode.CommandGroups.ArmPositions.ArmStowHigh;
import org.firstinspires.ftc.teamcode.Commands.Claw.CloseClaw;
import org.firstinspires.ftc.teamcode.Commands.FollowPath;
import org.firstinspires.ftc.teamcode.Commands.Claw.OpenClaw;
import org.firstinspires.ftc.teamcode.Commands.Pause;
import org.firstinspires.ftc.teamcode.RobotContainer;
import org.firstinspires.ftc.teamcode.utility.AutoFunctions;

import java.util.ArrayList;

// Example Sequential Command Group
// There are also:
// ParallelCommandGroup
// ParallelRaceGroup
// ParallelDeadlineGroup

public class WallPickUp extends SequentialCommandGroup {

    // constructor
    public WallPickUp() {

        addCommands (
        // What this position should do is give the camera a good vantage point as well as keep the arm out of the way

                //new InstantCommand(()-> RobotContainer.odometry.setCurrentPos(new Pose2d(-0.40,1.6,new Rotation2d(Math.toRadians(-90))))),


                new FollowPath(
                        1.0,
                        1.0,
                        0.0,
                        0.0,
                        AutoFunctions.redVsBlue(new Rotation2d(Math.toRadians(90.0))),
                        new ArrayList<Translation2d>() {{ }},
                        AutoFunctions.redVsBlue(new Pose2d(-1.2, 1.2, new Rotation2d(Math.toRadians(90.0)))),
                        AutoFunctions.redVsBlue(new Rotation2d(Math.toRadians(-90.0)))),

                // lifts the shoulder up 90+-60 degrees
                // lifts the shoulder up to 135 degrees
                new InstantCommand(() ->RobotContainer.shoulderJoint.RotateTo(35)),

                // folds the elbow in 270
                new InstantCommand(() ->RobotContainer.elbowJoint.RotateTo(266)),

                // folds the wrist in 0
                new InstantCommand(() -> RobotContainer.flappyFlappyWrist.RotateTo(100)),

                // powers the wrist and moves it to straight position
                new InstantCommand(() -> RobotContainer.wristRotateServo.RotateTo(0)),

                new Pause(0.25),

                new OpenClaw(),

                new Pause(0.25),


                new FollowPath(
                        1.0,
                        1.0,
                        0.0,
                        0.0,
                        AutoFunctions.redVsBlue(new Rotation2d(Math.toRadians(90.0))),
                        new ArrayList<Translation2d>() {{ }},
                        AutoFunctions.redVsBlue(new Pose2d(-1.2, 1.42, new Rotation2d(Math.toRadians(90.0)))),
                        AutoFunctions.redVsBlue(new Rotation2d(Math.toRadians(-90.0)))),

                new CloseClaw(),

                new Pause(0.25),

                new ArmStowHigh(),

                new Pause(0.25)


        );
    }

}

// Example #1: Lily's 2023 FRC super cube auto
/*          // enable arm, and lift to stow position
            new InstantCommand(() -> RobotContainer.arm.SetEnableArm(true)),

            // move arm back to drop off cube
            new InstantCommand(() -> RobotContainer.arm.SetArmPosition(RobotContainer.arm.HIGH_DEG)),

            // delay until arm gets back
            new DelayCommand(1.0),

            // place cube
            new InstantCommand(() -> RobotContainer.grabber.setClose()),

            // delay for gripper to close
            new DelayCommand(0.7),

            // move arm to 'forward position' but inside robot bumper)
            // move to 135deg
            new InstantCommand(() -> RobotContainer.arm.SetArmPosition(135.0)),

            // delay for arm to get to stow
            new DelayCommand(1.5),

            // ensure arm is stowed before it is allow to begin moving over charge station
            new SafetyCheckStowPosition(),

            // drive right
            // new DrivetoRelativePose(new Pose2d(0,-2.0, new Rotation2d(0.0)), 1.0, 0.1, 5.0),

            // drive straight
            new DrivetoRelativePose(new Pose2d(5.0, 0, new Rotation2d(0.0)),1.8,0.1, 7.0),

            // pick up cube from floor :)
            new AutoFloorCubePickup(),

            // delay
            new DelayCommand(0.5),

            // drive back
            //new DrivetoRelativePose(new Pose2d(1.0,0, new Rotation2d(0.0)), 1.0, 0.1, 2.0),

            // drive left to center
            new DrivetoRelativePose(new Pose2d(-1.0,2.0, new Rotation2d(0.0)), 1.8, 0.1, 5.0),

            // drive straight onto charge station
            new DrivetoRelativePose(new Pose2d(-1.5, 0, new Rotation2d(0.0)),1.0,0.1, 30.0),

            // balance
            new AutoBalance()

// Example #2: Matthew's 2024 shoot donut sequence.
This sequence contains parallel and parallelrace subgroups within an overall series command

      addCommands(
      new ParallelRaceGroup(
        new AimToSpeaker(),
        new SpinupSpeaker()
      ),
      new ParallelCommandGroup(
        new WaitForEffectorAngle(),
        new WaitForShooterSpinup()
      ),

      new ShootSpeaker()
    );

 */