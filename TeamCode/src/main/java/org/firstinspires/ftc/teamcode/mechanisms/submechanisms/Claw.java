package org.firstinspires.ftc.teamcode.mechanisms.submechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.BaseRobot;
import org.firstinspires.ftc.teamcode.Settings;

/** @noinspection FieldCanBeLocal, unused */
public class Claw {
    public final Servo clawServoL;
    public final Servo clawServoR;
    // public static TouchSensor pixelSensor;
    private final BaseRobot baseRobot;
    private final HardwareMap hardwareMap;
    public boolean opened = true;
    public boolean openedR = true;
    public boolean openedL = true;

    public Claw(BaseRobot baseRobot) {
        this.baseRobot = baseRobot;
        this.hardwareMap = baseRobot.hardwareMap;
        clawServoR = hardwareMap.get(Servo.class, "clawR");
        clawServoL = hardwareMap.get(Servo.class, "clawL");
        clawServoR.setDirection(Servo.Direction.REVERSE);
        close();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /* Set the right servo; true = open, false = close */
    public void setRightServo(boolean open) {
        double position = open ? Settings.Hardware.Servo.Claw.RIGHT_OPEN : Settings.Hardware.Servo.Claw.RIGHT_CLOSED;
        clawServoR.setPosition(position);
        openedR = open;
    }

    /* Set the left servo; true = open, false = close */
    public void setLeftServo(boolean open) {
        double position = open ? Settings.Hardware.Servo.Claw.LEFT_OPEN : Settings.Hardware.Servo.Claw.LEFT_CLOSED;
        clawServoL.setPosition(position);
        openedL = open;
    }

    /* Open both servos */
    public void open() {
        if (!opened) {
            setRightServo(true);
            setLeftServo(true);
            opened = true;
            openedR = true;
            openedL = true;
        }
    }

    /* Close both servos */
    public void close() {
        if (opened) {
            setRightServo(false);
            setLeftServo(false);
            opened = false;
            openedR = false;
            openedL = false;
        }
    }

}
