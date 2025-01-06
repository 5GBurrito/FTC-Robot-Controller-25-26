package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends SubsystemBase {

    private Servo intakePivot;
    private Servo intakeExt;
    private IntakeState state;

    private Servo kicker;

    public enum IntakeState {
        HOME,
        COLLECT,
        STORE
    }

    public Intake(HardwareMap hMap) {

        this.intakePivot = hMap.get(Servo.class, "IntakePivot");
        this.intakeExt = hMap.get(Servo.class, "IntakeExt");
        this.state = IntakeState.HOME;

        this.kicker = hMap.get(Servo.class, "Kicker");

    }

    public void extend() {

        intakeExt.setPosition(1);

    }
    public void retract() {

        intakeExt.setPosition(0.3);

    }

    public IntakeState getState() {
        return state;
    }

    public void setState(IntakeState state) {
        this.state = state;
    }

    public void setPivot(IntakeState state) {
 
        this.state = state;

        switch(state) {
            case HOME:
                intakePivot.setPosition(0.5);
                break;
            case STORE:
                intakePivot.setPosition(0.2);
                break;
            case COLLECT:
                intakePivot.setPosition(1);
                break;
        }

    }

    public void extendKicker() {

        kicker.setPosition(0.35);

    }

    public void retractKicker() {

        kicker.setPosition(0);

    }



}
