package com.kalipsorobotics.intoTheDeep;

import com.kalipsorobotics.actions.autoActions.InitAuto;
import com.kalipsorobotics.actions.KActionSet;
import com.kalipsorobotics.actions.autoActions.KServoAutoAction;
import com.kalipsorobotics.actions.autoActions.PurePursuitAction;
import com.kalipsorobotics.actions.WaitAction;
import com.kalipsorobotics.actions.autoActions.WallToBarHangRoundTrip;
import com.kalipsorobotics.actions.outtake.SpecimenHangReady;
import com.kalipsorobotics.actions.outtake.MoveOuttakeLSAction;
import com.kalipsorobotics.actions.outtake.SpecimenWallReady;
import com.kalipsorobotics.actions.outtake.teleopActions.OuttakeClawAction;
import com.kalipsorobotics.localization.WheelOdometry;
import com.kalipsorobotics.modules.DriveTrain;
import com.kalipsorobotics.modules.IMUModule;
import com.kalipsorobotics.modules.Intake;
import com.kalipsorobotics.modules.IntakeClaw;
import com.kalipsorobotics.modules.Outtake;
import com.kalipsorobotics.utilities.OpModeUtilities;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class RedAutoSpecimen extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        OpModeUtilities opModeUtilities = new OpModeUtilities(hardwareMap, this, telemetry);
        KActionSet redAutoSpecimen = new KActionSet();
        DriveTrain driveTrain = new DriveTrain(opModeUtilities);
        Outtake outtake = new Outtake(opModeUtilities);
        IntakeClaw intakeClaw = new IntakeClaw(opModeUtilities);
        IMUModule imuModule = new IMUModule(opModeUtilities);
        sleep(1000);
        WheelOdometry wheelOdometry = new WheelOdometry(opModeUtilities, driveTrain, imuModule, 0, 0, 0);
        MoveOuttakeLSAction.setGlobalLinearSlideMaintainTicks(0);
        // Target can always be 0 because Hung said so
        MoveOuttakeLSAction maintainLS = new MoveOuttakeLSAction(outtake, 0);
//                MoveLSAction.globalLinearSlideMaintainTicks);
        maintainLS.setName("maintainLS");

        InitAuto initAuto = new InitAuto(intakeClaw, outtake);
        initAuto.setName("initAuto");

        //================begin of first specimen====================
        WaitAction waitAtStart = new WaitAction(300);
        waitAtStart.setName("waitAtStart");
        redAutoSpecimen.addAction(waitAtStart);

        PurePursuitAction moveToSpecimenBar = new PurePursuitAction(driveTrain, wheelOdometry);
        moveToSpecimenBar.setName("moveToSpecimenBar");
        moveToSpecimenBar.setMaxTimeOutMS(4000);
        moveToSpecimenBar.addPoint(0, 0, 0);
        moveToSpecimenBar.addPoint(-790, 300, 0);
        moveToSpecimenBar.setDependentActions(waitAtStart);
        redAutoSpecimen.addAction(moveToSpecimenBar);

        SpecimenHangReady specimenHangReady1 = new SpecimenHangReady(outtake);
        specimenHangReady1.setName("hangSpecimenReady1");
        redAutoSpecimen.addAction(specimenHangReady1);

        MoveOuttakeLSAction lowerSlidesHalf1 = new MoveOuttakeLSAction(outtake, 225);
        lowerSlidesHalf1.setName("lowerSlidesHalf1");
        lowerSlidesHalf1.setDependentActions(specimenHangReady1, moveToSpecimenBar);
        redAutoSpecimen.addAction(lowerSlidesHalf1);

        WaitAction waitAfterHang = new WaitAction(200);
        waitAfterHang.setName("waitAfterHang");
        waitAfterHang.setDependentActions(lowerSlidesHalf1);
        redAutoSpecimen.addAction(waitAfterHang);

        KServoAutoAction openClaw = new KServoAutoAction(outtake.getOuttakeClaw(),
                OuttakeClawAction.OUTTAKE_CLAW_OPEN_POS);
        openClaw.setName("openClaw");
        openClaw.setDependentActions(waitAfterHang);
        redAutoSpecimen.addAction(openClaw);

        SpecimenWallReady specimenWallReady = new SpecimenWallReady(outtake);
        specimenWallReady.setName("specimenWallReady");
        specimenWallReady.setDependentActions(lowerSlidesHalf1);
        redAutoSpecimen.addAction(specimenWallReady);
        //===============end of first specimen===============



        //================beginning of push================
        PurePursuitAction moveFloorSamples = new PurePursuitAction(driveTrain, wheelOdometry);
        moveFloorSamples.setName("moveFloorSamples");
        moveFloorSamples.setDependentActions(waitAfterHang);
        //first sample to depot
        moveFloorSamples.addPoint( -620, -475, -90); //y -500
        moveFloorSamples.addPoint(-1330, -500, -180); //y -475
        moveFloorSamples.addPoint(-1330, -800, -180);// before push
        moveFloorSamples.addPoint(-240, -800, -180);

        //second sample to depot
        moveFloorSamples.addPoint(-1330, -775, -180); //y -80
        moveFloorSamples.addPoint(-1330, -1050, -180);// before push
        moveFloorSamples.addPoint(-240, -1050, -180);

        //third sample to depot
        moveFloorSamples.addPoint(-1300, -1050, -180);
        moveFloorSamples.addPoint(-1300, -1175, -180);//before push
        moveFloorSamples.addPoint(-240, -1175, -180);
        moveFloorSamples.addPoint(-430, -1170, -180);//move back out to avoid sample carry

        //wall to depot for specimen
        moveFloorSamples.addPoint(-305, -700, -180); //-305, -600
        moveFloorSamples.addPoint(-70, -700, -180);
        redAutoSpecimen.addAction(moveFloorSamples);
        //==============end of pushing================


        //=============begin of second specimen=================
        WallToBarHangRoundTrip wallToBarHangRoundTrip2 = new WallToBarHangRoundTrip(driveTrain, wheelOdometry,
                outtake, 375); //400 //375
        wallToBarHangRoundTrip2.setName("wallToBarHangRoundTrip2");
        wallToBarHangRoundTrip2.setDependentActions(moveFloorSamples);
        redAutoSpecimen.addAction(wallToBarHangRoundTrip2);
        //===============end of second specimen==============

        //============begin of third================
        WallToBarHangRoundTrip wallToBarHangRoundTrip3 = new WallToBarHangRoundTrip(driveTrain, wheelOdometry,
                outtake, 450); //500 //450
        wallToBarHangRoundTrip3.setName("wallToBarHangRoundTrip3");
        wallToBarHangRoundTrip3.setDependentActions(wallToBarHangRoundTrip2);
        redAutoSpecimen.addAction(wallToBarHangRoundTrip3);
        //===============end of third specimen===========

        //===============start of fourth specimen==============
//        WallToBarHangRoundTrip wallToBarHangRoundTrip4 = new WallToBarHangRoundTrip(driveTrain, wheelOdometry,
//                outtake, 525);
//        wallToBarHangRoundTrip4.setName("wallToBarHangRoundTrip4");
//        wallToBarHangRoundTrip4.setDependentActions(wallToBarHangRoundTrip3);
//        redAutoSpecimen.addAction(wallToBarHangRoundTrip4);
        //================end of specimen 4================



        initAuto.update();

        redAutoSpecimen.printWithDependentActions();
        waitForStart();
        while (opModeIsActive()) {

            wheelOdometry.updatePosition();
            maintainLS.update();

            redAutoSpecimen.updateCheckDone();

            initAuto.update();

        }

    }
}
