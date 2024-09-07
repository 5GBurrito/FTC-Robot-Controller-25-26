package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    public DcMotor spinner;
    public final Gamepad gamepad2;
    public final Gamepad gamepad1;
    public HardwareMap hardwareMap;
    public static volatile double OUTTAKE = -0.5;
    public static volatile double INTAKE = 0.5;

    public Intake(OpMode opMode) {
        this.hardwareMap = opMode.hardwareMap;
        this.gamepad2 = opMode.gamepad2;
        this.gamepad1 = opMode.gamepad1;

        spinner = (DcMotor) hardwareMap.get("Intake");

    }
    public void teleOp() {
        if (gamepad2.a) spinner.setPower(INTAKE);
        if (gamepad2.b) spinner.setPower(OUTTAKE);
        else spinner.setPower(0);
    }
}