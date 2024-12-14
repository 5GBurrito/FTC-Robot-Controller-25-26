package org.firstinspires.ftc.teamcode.subsystems.delivery;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.SonicSubsystemBase;
import org.firstinspires.ftc.teamcode.subsystems.feedback.DriverFeedback;

public class Hang extends SonicSubsystemBase {

    private Motor left;

    private Telemetry telemetry;

    GamepadEx gamepad;

    private DriverFeedback feedback;

    public Hang(HardwareMap hardwareMap, GamepadEx gamepad, Telemetry telemetry, DriverFeedback feedback) {
        /* instantiate motors */
        this.left  = new Motor(hardwareMap, "left");


        left.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        this.gamepad = gamepad;
        this.telemetry = telemetry;
        this.feedback = feedback;
    }

    public void Expand() {
        left.set(1);
    }

    public void Collapse() {
        left.set(-1);
    }

    public void Hold() {
        left.set(0);
    }
}