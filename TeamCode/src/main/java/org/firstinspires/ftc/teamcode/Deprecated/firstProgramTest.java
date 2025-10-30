//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//@Deprecated
//
//@TeleOp(name="firstProgramTest")
//public class firstProgramTest extends OpMode
//{
//    DcMotor motor1; // front right
//    DcMotor motor2; // back right
//    DcMotor motor3; // back left
//    DcMotor motor4; // front left
//
//    @Override
//    public void init() {
//        // Map each motor to its name in the configuration
//        motor1 = hardwareMap.get(DcMotor.class, "FR");
//        motor2 = hardwareMap.get(DcMotor.class, "BR");
//        motor3 = hardwareMap.get(DcMotor.class, "BL");
//        motor4 = hardwareMap.get(DcMotor.class, "FL");
//
//        // Reverse the left side motors so the robot drives straight
//        motor3.setDirection(DcMotor.Direction.REVERSE);
//        motor4.setDirection(DcMotor.Direction.REVERSE);
//
//        telemetry.addLine("Arcade Drive Initialized");
//        telemetry.update();
//    }
//
//    @Override
//    public void loop() {
//        // --- Read joystick values ---
//        double drive = -gamepad1.left_stick_y;  // Forward/backward (negative because up is -1)
//        double turn  = gamepad1.left_stick_x;    // Left/right turning
//
//        // --- Combine drive and turn for arcade control ---
//        double leftPower  = drive + turn;
//        double rightPower = drive - turn;
//
//        // --- Limit the power range to [-1.0, 1.0] ---
//        leftPower  = Math.max(-1.0, Math.min(1.0, leftPower));
//        rightPower = Math.max(-1.0, Math.min(1.0, rightPower));
//
//        // --- Set motor power ---
//        motor1.setPower(rightPower);
//        motor2.setPower(rightPower);
//        motor3.setPower(leftPower);
//        motor4.setPower(leftPower);
//
//        //Drifting system
//        double drift  = gamepad1.right_stick_x;
//        motor1.setPower(drift);
//        motor2.setPower(drift);
//        motor3.setPower(drift);
//        motor4.setPower(drift);
//    }
//}
