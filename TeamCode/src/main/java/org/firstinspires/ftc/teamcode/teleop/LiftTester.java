package org.firstinspires.ftc.teamcode.teleop;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
@TeleOp(name = "LiftTester")
public class LiftTester extends LinearOpMode {
    private Lift lift;
    private GamepadEvents controller;
    private double liftPower;
    public void runOpMode(){
        lift = new Lift(hardwareMap, "lift", "lift");
        controller = new GamepadEvents(gamepad1);
        telemetry.addData("Status","Initialized");
        telemetry.update();
        waitForStart();
        while(opModeIsActive()){
            if(controller.right_bumper.onPress()){
                liftPower = -controller.right_trigger.getTriggerValue();
            }else{
                liftPower = controller.right_trigger.getTriggerValue();
            }
            lift.moveLift(liftPower);

            telemetry.addData("Lift Power", liftPower);
            telemetry.addData("Lift Position", lift.getPosition());
            telemetry.update();
        }
    }

}
