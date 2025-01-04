package org.firstinspires.ftc.teamcode.Mechanisms.Claw;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Actuators.ServoAdvanced;


@Config
public class Claw {
    HardwareMap hardwareMap;
    ServoAdvanced clawServo;

    public static double clawOpen = 0.66;
    public static double clawClosed = 0.5;
    public clawState clawPos = clawState.OPEN;
    public Claw(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        this.clawServo = new ServoAdvanced(hardwareMap.get(Servo.class, "clawServo"));

    }

    public enum clawState {
        CLOSE, //spins to close servo, should only be closed enough to hold piece
        OPEN   //spins to have nearly fully open servo
    }
    public ElapsedTime timer = new ElapsedTime();
    public Action servoClaw(clawState clawPos){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket Packet) {
                    if (clawPos == clawState.CLOSE) {
                        clawServo.setPosition(clawClosed);
                    }
                    else if (clawPos == clawState.OPEN) {
                        clawServo.setPosition(clawOpen);
                    }
                // servo parameter -1, 0   0, 1
                return false;
            }
        };
    }
}

