package org.firstinspires.ftc.teamcode.Mechanisms.Arm;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class Arm {
    HardwareMap hardwareMap;
    Servo servoWrist;
    public Servo servoArmLeft;
    Servo servoArmRight;
    public static double armRetract = 0.89;
    public static double armExtend = 0.35;
    public static double armSpecimenExtend = -1;
    public static double wristExtend = 1;
    public static double wristRetract = 0.6;
    public static double wristSpecimenExtend = 0.65;
    public ElapsedTime timer = new ElapsedTime();
    public Arm(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.servoWrist = hardwareMap.get(Servo.class, "wrist");
        this.servoArmLeft = hardwareMap.get(Servo.class, "armRight");
        this.servoArmRight = hardwareMap.get(Servo.class, "armLeft");
    }

    public enum armState {
        RETRACT,    // pulls arm in
        EXTEND,      // pushes arm out
        SPEC
    }
    armState armPos = armState.RETRACT;
    ElapsedTime hahaALEX = new ElapsedTime();
    ElapsedTime hahaALEX2 = new ElapsedTime();
    public Action servoArm(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket Packet) {
                    double tiem = hahaALEX.seconds();
                    if (tiem > .3) {
                        if (armPos == armState.RETRACT) {
                            servoArmLeft.setPosition(armExtend);
                            servoArmRight.setPosition(armExtend);
                            servoWrist.setPosition(wristExtend);
                            armPos = armState.EXTEND;
                            hahaALEX.reset();
                        } else if (armPos == armState.EXTEND) {
                            servoArmLeft.setPosition(armRetract);
                            servoArmRight.setPosition(armRetract);
                            servoWrist.setPosition(wristRetract);
                            armPos = armState.RETRACT;
                            hahaALEX.reset();
                        }
                    }
                return false;
            }
        };
    }
    public Action armClose(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket Packet) {

                        servoArmLeft.setPosition(armRetract);
                        servoArmRight.setPosition(armRetract);
                        servoWrist.setPosition(wristRetract);
                return false;
            }
        };
    }
    public Action armOpen(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket Packet) {
                servoArmLeft.setPosition(armExtend);
                servoArmRight.setPosition(armExtend);
                servoWrist.setPosition(wristExtend);
                return false;
            }
        };
    }
    public Action servoArmSpec(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket Packet) {
                double tiem2 = hahaALEX.seconds();
                if (tiem2 > .3) {
                    if (armPos == armState.RETRACT) {
                        servoArmLeft.setPosition(armSpecimenExtend);
                        servoArmRight.setPosition(armSpecimenExtend);
                        servoWrist.setPosition(wristSpecimenExtend);
                        armPos = armState.SPEC;
                        hahaALEX.reset();
                    } else if (armPos == armState.SPEC) {
                        servoArmLeft.setPosition(armRetract);
                        servoArmRight.setPosition(armRetract);
                        servoWrist.setPosition(wristRetract);
                        armPos = armState.RETRACT;
                        hahaALEX.reset();
                    }
                }
                return false;
            }
        };
    }
}