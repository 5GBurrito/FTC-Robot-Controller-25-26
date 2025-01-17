package org.firstinspires.ftc.teamcode.tatooine.Opmodes.Autonomus;

import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.tatooine.SubSystem.Intake;
import org.firstinspires.ftc.teamcode.tatooine.utils.Alliance.CheckAlliance;
@Disabled
@Autonomous(name = "IntakeTest", group = "Autonomous")
public class IntakeTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        boolean isRed = CheckAlliance.isRed();
        Intake intake = new Intake(this,isRed, true);
        boolean isSpecimen = true;
        waitForStart();
        //while(opModeIsActive()) {
            Actions.runBlocking(intake.intakeByColor(true)
            );
            updateTelemetry(telemetry);
        //}
    }
}