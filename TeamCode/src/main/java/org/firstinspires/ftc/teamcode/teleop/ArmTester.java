package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
@TeleOp(name="ArmTester")
public class ArmTester extends LinearOpMode {
    private Arm arm;
    private GamepadEvents controller;
    private double armPos;

    @Override
    public void runOpMode(){
        arm = new Arm(hardwareMap);
        controller = new GamepadEvents(gamepad1);
        telemetry.addData("Status","Initialized");

        waitForStart();
        while(opModeIsActive()){
            telemetry.addLine("OpMode Active");
            if(controller.right_bumper.onPress()){
                armPos = 0.9;
                arm.setPosition(armPos);
            } else if (controller.left_bumper.onPress()) {
                armPos = 0;
                arm.setPosition(armPos);
            }
            telemetry.addData("Arm Position", armPos);
            telemetry.update();
            controller.update();
        }
    }
}
