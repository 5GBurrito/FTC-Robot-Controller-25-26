package org.firstinspires.ftc.teamcode.tatooine.Opmodes.Teleops.Tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.tatooine.SubSystem.Arm;

import java.util.ArrayList;
import java.util.List;
@Disabled
@TeleOp(name = "ArmTest",group = "Tests")
public class ArmTest extends LinearOpMode {
    private FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();


    @Override
    public void runOpMode() throws InterruptedException {
        Arm arm = new Arm(this, true);
        waitForStart();
        while (opModeIsActive()){
            TelemetryPacket packet = new TelemetryPacket();
//            telemetry.addData("arm ang",arm.getAngle());
//            telemetry.addData("power",arm.getAngleMotor().getPower());
//            telemetry.addData("getF",arm.getAnglePID().getF());
            // updated based on gamepads
            if (gamepad1.cross) {
                runningActions.add(arm.scoreSpecimenAction());
            }
            if (gamepad1.circle){
                runningActions.add(new InstantAction(()-> arm.resetEncoders()));
            }
            if (gamepad1.square){
                runningActions.add(arm.setAngle(60));
            }
            if(gamepad1.dpad_up){
                runningActions.add(arm.setAngle(0));
            }
            if(gamepad1.triangle){
                runningActions.add(arm.closeAction());
            }


            // update running actions
            List<Action> newActions = new ArrayList<>();
            for (Action action : runningActions) {
                action.preview(packet.fieldOverlay());
                if (action.run(packet)) {
                    newActions.add(action);
                }
            }
            runningActions = newActions;

            dash.sendTelemetryPacket(packet);
          //  telemetry.update();

        }
    }

}
