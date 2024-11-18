package org.firstinspires.ftc.teamcode.subsystems.feedback;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriverFeedback extends SubsystemBase {
    private final GamepadEx driverGamepad;

    private final GamepadEx operatorGamepad;

    private final Telemetry telemetry;

    private DigitalChannel redLED;

    private DigitalChannel greenLED;

    private final int RumbleTime = 500;

    private final boolean withLED = false;

    public DriverFeedback(HardwareMap hardwareMap, GamepadEx driverGamepad, GamepadEx operatorGamepad, Telemetry telemetry) {
        this.driverGamepad = driverGamepad;
        this.operatorGamepad = operatorGamepad;
        this.telemetry = telemetry;

        if (withLED) {
            redLED = hardwareMap.get(DigitalChannel.class, "Red");
            greenLED = hardwareMap.get(DigitalChannel.class, "Green");

            // Set mode to output
            redLED.setMode(DigitalChannel.Mode.OUTPUT);
            greenLED.setMode(DigitalChannel.Mode.OUTPUT);
        }
    }

    public void TurnLedRed() {
        redLED.setState(false);
        greenLED.setState(true);
    }

    public void TurnLedGreen() {
        redLED.setState(true);
        greenLED.setState(false);
    }
    // Driver rumbles

    public void DriverStopRumble() {
        this.driverGamepad.gamepad.stopRumble();
    }

    public void DriverRumbleLeft() {
        this.driverGamepad.gamepad.rumble(0.7, 0, RumbleTime);

    }

    public void DriverRumbleRight() {
        this.driverGamepad.gamepad.rumble(0, 0.7, RumbleTime);
    }

    public void DriverRumbleBlip() {

        DriverRumbleBlip(2);
    }

    public void DriverRumbleBlip(int blipCount) {
        this.driverGamepad.gamepad.rumbleBlips(blipCount);
    }

    // Operator rumbles

    public void OperatorStopRumble() {
        this.operatorGamepad.gamepad.stopRumble();
    }

    public void OperatorRumbleLeft() {
        this.operatorGamepad.gamepad.rumble(0.7, 0, RumbleTime);

    }

    public void OperatorRumbleRight() {
        this.operatorGamepad.gamepad.rumble(0, 0.7, RumbleTime);
    }

    public void OperatorRumbleBlip() {

        DriverRumbleBlip(2);
    }

    public void OperatorRumbleBlip(int blipCount) {
        this.operatorGamepad.gamepad.rumbleBlips(blipCount);
    }

    public void DriverControllerGreen() {
//        this.driverGamepad.gamepad.setLedColor(0, 1, 0, 3000);
    }

    public void DriverControllerRed() {
        this.driverGamepad.gamepad.setLedColor(1, 0, 0, 3000);
    }
}
