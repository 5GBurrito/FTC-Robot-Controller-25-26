package org.firstinspires.ftc.teamcode.opmodes.autonomous.movement;

import com.arcrobotics.ftclib.geometry.Pose2d;

public class RotationMovement extends BaseMovement {
    public RotationMovement(double distance) {
        super(distance);
    }

    @Override
    protected void updateDrivingParameters() {
        rotationSpeed = currentSpeed;
        strafeSpeed = 0;
        forwardSpeed = 0;

    }

    @Override
    protected double getCurrentDrivingReading(Pose2d pose) {
        return pose.getRotation().getRadians();
    }
}
