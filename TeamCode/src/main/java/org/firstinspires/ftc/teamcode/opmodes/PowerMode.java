package org.firstinspires.ftc.teamcode.opmodes;

public enum PowerMode {
    REGULAR(.8),
    NITRO(1),
    SLOW(.25);

    PowerMode(double powerRatio) {
        this.powerRatio = powerRatio;
    }

    public double getPowerRatio() {
        return powerRatio;
    }

    private final double powerRatio;
}
