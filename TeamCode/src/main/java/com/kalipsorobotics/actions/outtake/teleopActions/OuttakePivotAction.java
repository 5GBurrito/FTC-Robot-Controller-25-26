package com.kalipsorobotics.actions.outtake.teleopActions;

import com.kalipsorobotics.actions.Action;
import com.kalipsorobotics.modules.Outtake;
import com.kalipsorobotics.utilities.KServo;
import com.qualcomm.robotcore.hardware.Servo;

//0.925 in
//0.0 out
public class OuttakePivotAction {

    final private Outtake outtake;
    private final KServo outtakePivotServo;



    private boolean isIn = true;

    public OuttakePivotAction(Outtake outtake) {
        this.outtake = outtake;
        outtakePivotServo = outtake.getOuttakePivotServo();
    }

    public void setPosition(double position) {
        outtakePivotServo.setPosition(position);
    }

    public void moveIn() {
        setPosition(Outtake.OUTTAKE_PIVOT_DOWN_POS);
        isIn = true;
    }

    public void moveOut() {
        setPosition(Outtake.OUTTAKE_PIVOT_BASKET_POS);
        isIn = false;
    }

    public void moveWall() {
        setPosition(Outtake.OUTTAKE_PIVOT_DOWN_POS);
        isIn = false;
    }

    public void togglePosition() {
        if (!isIn) {
            moveIn();
        } else {
            moveOut();
        }
    }

    public Outtake getOuttake() {
        return outtake;
    }

}
