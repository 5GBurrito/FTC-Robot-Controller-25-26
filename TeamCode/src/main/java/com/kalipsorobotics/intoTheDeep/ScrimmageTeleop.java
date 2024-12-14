package com.kalipsorobotics.intoTheDeep;

import android.util.Log;

import com.kalipsorobotics.actions.drivetrain.DriveAction;
import com.kalipsorobotics.actions.KActionSet;
import com.kalipsorobotics.actions.intake.IntakeDoorAction;
import com.kalipsorobotics.actions.intake.IntakeNoodleAction;
import com.kalipsorobotics.actions.intake.IntakePivotAction;
import com.kalipsorobotics.actions.outtake.SpecimenHangReady;
import com.kalipsorobotics.actions.outtake.SpecimenWallReady;
import com.kalipsorobotics.actions.outtake.teleopActions.OuttakeClawAction;
import com.kalipsorobotics.actions.outtake.teleopActions.OuttakePigeonAction;
import com.kalipsorobotics.actions.outtake.teleopActions.OuttakePivotAction;
import com.kalipsorobotics.actions.outtake.teleopActions.OuttakeSlideAction;
import com.kalipsorobotics.modules.ColorDetector;
import com.kalipsorobotics.modules.DriveTrain;
import com.kalipsorobotics.modules.Intake;
import com.kalipsorobotics.modules.Outtake;
import com.kalipsorobotics.utilities.OpModeUtilities;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
public class ScrimmageTeleop extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        OpModeUtilities opModeUtilities = new OpModeUtilities(hardwareMap, this, telemetry);
        KActionSet teleopActionSet = new KActionSet();
        DriveTrain driveTrain = new DriveTrain(opModeUtilities);
        DriveAction driveAction = new DriveAction(driveTrain);
        Intake intake = new Intake(opModeUtilities);
        IntakeNoodleAction intakeNoodleAction = new IntakeNoodleAction(intake, 0, false);
        IntakePivotAction intakePivotAction = new IntakePivotAction(intake);
        IntakeDoorAction intakeDoorAction = new IntakeDoorAction(intake);
        Outtake outtake = new Outtake(opModeUtilities);
        OuttakePivotAction outtakePivotAction = new OuttakePivotAction(outtake);
        OuttakeSlideAction outtakeSlideAction = new OuttakeSlideAction(outtake);
        OuttakeClawAction outtakeClawAction = new OuttakeClawAction(outtake);
        OuttakePigeonAction outtakePigeonAction = new OuttakePigeonAction(outtake);
        ColorDetector colorDetector = new ColorDetector(opModeUtilities, hardwareMap);
        SpecimenHangReady specimenHangReady = null;
        SpecimenWallReady specimenWallReady = null;


        boolean prevGamePadY = false;
        boolean prevGamePadX = false;
        boolean prevGamePadB = false;
        boolean prevGamePadA = false;
        boolean prevDpadLeft = false;
        boolean prevDpadUp = false;
        boolean prevDpadRight = false;
        boolean prevDpadDown = false;
        boolean retracted = true;

        //CHANGE ACCORDING TO ALLIANCE

        boolean isRed = true;
        boolean takeInYellow = false;

        intakePivotAction.moveUp();
        intakeDoorAction.close();

        outtakeClawAction.close();
        outtakePivotAction.moveIn();

        //Pigeon
        outtakePigeonAction.setPosition(OuttakePigeonAction.OUTTAKE_PIGEON_IN_POS);

        waitForStart();

        while (opModeIsActive()) {

            //Drive
            driveAction.move(gamepad1);

            //INTAKE
            //Noodle
            if (gamepad2.dpad_left && !prevDpadLeft) {
                takeInYellow = !takeInYellow;
            }
            if (gamepad2.left_trigger > 0.5 || gamepad2.right_trigger > 0.5) {
                colorDetector.cycle(isRed, takeInYellow, intakeNoodleAction);
            } else if (gamepad2.left_bumper) {
                intakeNoodleAction.reverse();
            } else {
                intakeNoodleAction.stop();
            }

            //intake pivot
            if (gamepad2.x && !prevGamePadX) {
                intakePivotAction.togglePosition();
            }

            //dpad left for door toggle
            if (gamepad2.dpad_right && !prevDpadRight) {
                intakeDoorAction.togglePosition();
            }

            //manual control intake slide
            if (gamepad2.left_stick_y != 0 && gamepad2.left_stick_y != 0.1) {
            }



            //===============Outtake================

            //outtake pivot
            if (gamepad2.y && !prevGamePadY) {
                outtakePivotAction.togglePosition();
            }
            //outtake linear slides manual
            if (gamepad2.right_stick_y != 0) {
                outtakeSlideAction.setPower(-gamepad2.right_stick_y);
            } else if ((specimenWallReady == null || specimenWallReady.checkDoneCondition()) && (specimenHangReady == null || specimenHangReady.checkDoneCondition())) {

            }

            //outtake claw
            if (gamepad2.right_bumper) {
                outtakeClawAction.open();
            } else outtakeClawAction.close();

//            //outtake linear slide toggle
//            if (gamepad2.dpad_up && !prevDpadUp) {
//                outtakeSlideAction.toggle();
//            }

            if (gamepad2.dpad_up && !prevDpadUp) {
                Log.d("detected press", "dpad up");
                if (specimenHangReady  == null || specimenHangReady.checkDoneCondition()) {
                    specimenHangReady = new SpecimenHangReady(outtake);
                    teleopActionSet.addAction(specimenHangReady);
                }
            }
            if (specimenHangReady != null) {
                specimenHangReady.update();
            }

            if (gamepad2.dpad_down && !prevDpadDown) {
                Log.d("detected press", "dpad down");
                if (specimenWallReady == null || specimenWallReady.checkDoneCondition()) {
                    specimenWallReady = new SpecimenWallReady(outtake);
                }
            }
            if (specimenWallReady != null) {
                specimenWallReady.update();
            }




            if (gamepad2.a && gamepad1.a) {
                telemetry.addData("", "yes");
                telemetry.update();
            }


            prevDpadUp = gamepad2.dpad_up;
            prevDpadLeft = gamepad2.dpad_left;
            prevDpadRight = gamepad2.dpad_right;
            prevDpadDown = gamepad2.dpad_down;
            prevGamePadA = gamepad2.a;
            prevGamePadB = gamepad2.b;
            prevGamePadX = gamepad2.x;
            prevGamePadY = gamepad2.y;

            teleopActionSet.updateCheckDone();



        }
    }
}

