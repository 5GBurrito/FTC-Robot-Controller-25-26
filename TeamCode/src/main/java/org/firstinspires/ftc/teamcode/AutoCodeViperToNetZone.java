/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="Auto Viper NetZone (1)", group="")
public class AutoCodeViperToNetZone extends LinearOpMode {

    static final double     DRIVE_SPEED             = 0.9;
    static final double     DRIVE_INCREASED_SPEED             = 0.8;
    static final double     TURN_SPEED              = 0.2;

    private HornetRobo hornetRobo;

    //manager classes
    private AutoDriveManager driveManager;
    private AutoArmManager armManager;
    private AutoGrabberManager grabberManager;
    private AutoViperSlideManager viperSlideManager;

    //path flags
    private boolean strafeToStart = true;
    private boolean dropSpecimen = true;
    private boolean moveNearToAscentZone = true;
    private boolean ploughSampleToNetZone = true;
    private boolean parkInAscent = true;

    public void initialize()
    {
        hornetRobo = new HornetRobo();
        AutoHardwareMapper.MapToHardware(this, hornetRobo);
        driveManager = new AutoDriveManager(this, hornetRobo);
        grabberManager = new AutoGrabberManager(this, hornetRobo);
        armManager = new AutoArmManager(this, hornetRobo);
        viperSlideManager = new AutoViperSlideManager(this, hornetRobo);
    }

    public void runOpMode() {
        initialize();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        if (opModeIsActive()) {
            telemetry.addData("Starting to move", "");
            telemetry.update();

            //set forward
            driveManager.SetMotorDirection(AutoDriveManager.DriveDirection.FORWARD);
            //viperSlideManager.SetDirection(AutoDriveManager.DriveDirection.FORWARD);
            armManager.SetDirection(AutoDriveManager.DriveDirection.FORWARD);

            while (opModeIsActive() && !isStopRequested()) {

                telemetry.addData("Move to reach submersible  ", "");
                telemetry.update();
                int strafeDistance;
                int distanceToSub;
                int distance;
                int rotateToPosition;

                if (strafeToStart) {

                    telemetry.addData("Strafe to align with submersible", "");
                    telemetry.update();

                    strafeDistance = 30;
                    driveManager.StrafeToPosition(AutoDriveManager.DriveDirection.RIGHT, DRIVE_SPEED, strafeDistance);
                    telemetry.addData("Strafed Right", "");
                    telemetry.update();
                }

                if (dropSpecimen) {
                    dropSpecimenInTopRail();
                }

                if (moveNearToAscentZone) {
                    // Go reverse to be away from submersible to move to pick sample
                    int reverseDistance = 1; //TODO: Adjust during testing
                    driveManager.MoveStraightToPosition(AutoDriveManager.DriveDirection.FORWARD, DRIVE_SPEED, reverseDistance);
                    telemetry.addData("Go reverse to be away from submersible to move to pick sample", reverseDistance);
                    telemetry.update();

                    //strafe to go pass submersible edges to avoid hitting when moving forward to pick samples
                    strafeDistance = 32; //TODO: Adjust during testing
                    driveManager.StrafeToPosition(AutoDriveManager.DriveDirection.LEFT, DRIVE_SPEED, strafeDistance);
                    telemetry.addData("strafe to go pass submersible edges to avoid hitting when moving forward to pick samples", strafeDistance);
                    telemetry.update();

                    //MOVE FORWARD past sample
                    distance = 30; //TODO: Adjust during testing
                    driveManager.MoveStraightToPosition(AutoDriveManager.DriveDirection.BACKWARD, DRIVE_SPEED, distance);
                    telemetry.addData("MOVE FORWARD past sample", distance);
                    telemetry.update();
                }

                if (ploughSampleToNetZone) {

                    strafeDistance = 10; //TODO: Adjust during testing
                    driveManager.StrafeToPosition(AutoDriveManager.DriveDirection.RIGHT, DRIVE_SPEED, strafeDistance);
                    telemetry.addData("strafe to go pass submersible edges to avoid hitting when moving forward to pick samples", strafeDistance);
                    telemetry.update();

                    //Rotate 180 (to point front) + 45 degrees to turn to go in diagonal to plogh sample
                    rotateToPosition = 3;
                    driveManager.TurnUsingEncoders(AutoDriveManager.DriveDirection.RIGHT, DRIVE_SPEED, rotateToPosition);
                    telemetry.addData("rotate to point to front and turn to plough the sample", "");
                    telemetry.update();

                    //Move forward until it reaches net zone
                    distance = 47; //TODO: Adjust during testing
                    driveManager.MoveStraightToPosition(AutoDriveManager.DriveDirection.FORWARD, DRIVE_SPEED, distance);
                    telemetry.addData("Move backward until it reaches net zone", "");
                    telemetry.update();



                    /*
                    rotateToPosition = 30;

                    driveManager.TurnUsingEncoders(AutoDriveManager.DriveDirection.LEFT, DRIVE_SPEED, rotateToPosition);
                    telemetry.addData("rotate to point to front and turn to plough the sample", "");
                    telemetry.update();
                    */
                     
                }

                if (parkInAscent)
                {
                    distance = 47;
                    driveManager.MoveStraightToPosition(AutoDriveManager.DriveDirection.BACKWARD, DRIVE_SPEED, distance);

                    strafeDistance = 11; //TODO: Adjust during testing
                    driveManager.StrafeToPosition(AutoDriveManager.DriveDirection.RIGHT, DRIVE_SPEED, strafeDistance);
                    telemetry.addData("strafe to go pass submersible edges to avoid hitting when moving forward to pick samples", strafeDistance);
                    telemetry.update();

                    rotateToPosition = 0;
                    driveManager.TurnUsingEncoders(AutoDriveManager.DriveDirection.LEFT, DRIVE_SPEED, rotateToPosition);
                    telemetry.addData("rotate to point to front and turn to plough the sample", "");
                    telemetry.update();

                    distance = 50; //TODO: Adjust during testing
                    driveManager.MoveStraightToPosition(AutoDriveManager.DriveDirection.FORWARD, DRIVE_SPEED, distance);
                    telemetry.addData("Move backward until it reaches net zone", "");
                    telemetry.update();

                    distance = 50; //TODO: Adjust during testing
                    driveManager.MoveStraightToPosition(AutoDriveManager.DriveDirection.BACKWARD, DRIVE_SPEED, distance);
                    telemetry.addData("Move backward until it reaches net zone", "");
                    telemetry.update();

                    strafeDistance = 25; //TODO: Adjust during testing
                    driveManager.StrafeToPosition(AutoDriveManager.DriveDirection.LEFT, DRIVE_SPEED, strafeDistance);
                    telemetry.addData("strafe to go pass submersible edges to avoid hitting when moving forward to pick samples", strafeDistance);
                    telemetry.update();

                    rotateToPosition = 25;
                    driveManager.TurnUsingEncoders(AutoDriveManager.DriveDirection.RIGHT, DRIVE_SPEED, rotateToPosition);
                    telemetry.addData("rotate to point to front and turn to plough the sample", "");
                    telemetry.update();

                    //touch the rails
                    touchTheRails();

                }

                //done
                driveManager.StopRobo();
                telemetry.addData("Stopped Robo", "");
                telemetry.update();

                break;

            }
        }

    }

    private void dropSpecimenInBottomRail()
    {
        //TODO: adjust the distance during testing
        int distanceToSub = 26;
        telemetry.addData("Distance To Sub: ", distanceToSub);
        telemetry.update();

        //set target position to encoders and move to position
        driveManager.MoveStraightToPosition(AutoDriveManager.DriveDirection.BACKWARD, DRIVE_SPEED, distanceToSub);
        telemetry.addData("Reached Submersible", "");
        telemetry.update();

        //Set Arm in a position to hang specimen
        double armPosition = 0.40; //TODO: Correct during testing
        armManager.MoveArmToPosition(armPosition);
        telemetry.addData("Set Arm Pos: ", armPosition);
        telemetry.update();
        sleep(2000);

        grabberManager.OpenOrCloseGrabber(true);
        telemetry.addData("Open Grabber", "");
        telemetry.update();
        sleep(1000);

        armPosition = 0.6; //TODO: Correct during testing
        armManager.MoveArmToPosition(armPosition);
        telemetry.addData("Set Arm Pos: ", armPosition);
        telemetry.update();

    }

    private void dropSpecimenInTopRail()
    {
        int distanceToSub = 25;
        telemetry.addData("Distance To Sub: ", distanceToSub);
        telemetry.update();

        //set target position to encoders and move to position
        driveManager.MoveStraightToPosition(AutoDriveManager.DriveDirection.BACKWARD, DRIVE_SPEED, distanceToSub);
        telemetry.addData("Reached Submersible", "");
        telemetry.update();

        //setting viper slide and arm
        armManager.SetDirection(AutoDriveManager.DriveDirection.BACKWARD);
        viperSlideManager.ResetAndSetToEncoder();
        armManager.MoveArmToPosition(0.3);
        grabberManager.OpenOrCloseGrabber(false);

        //moving vs up
        viperSlideManager.SetDirection(AutoDriveManager.DriveDirection.FORWARD);
        viperSlideManager.SetPower(0.28);
        sleep(800);

        //stopping vs and moving arm to drop specimen
        viperSlideManager.SetPower(0.0);
        armManager.MoveArmToPosition(0.6);
        sleep(3000);

        //vs coming down for short time
        viperSlideManager.SetDirection(AutoDriveManager.DriveDirection.BACKWARD);
        viperSlideManager.SetPower(0.5);
        sleep(200);

        //vs stopping for arm to go back
        viperSlideManager.SetPower(0.0);
        armManager.MoveArmToPosition(0.3);
        sleep(200);

        //vs moving down again
        viperSlideManager.SetPower(0.5);
        sleep(200);

    }

    public void touchTheRails(){
        viperSlideManager.SetDirection(AutoDriveManager.DriveDirection.FORWARD);
        viperSlideManager.SetPower(0.3);
        sleep(300);

        //stopping vs and moving arm to drop specimen
        viperSlideManager.SetPower(0.0);
        armManager.MoveArmToPosition(0.6);
        sleep(200);
    }
}