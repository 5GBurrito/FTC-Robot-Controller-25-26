package org.nknsd.robotics.team.autoSteps;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.robotics.framework.NKNAutoStep;
import org.nknsd.robotics.team.autonomous.AutoSkeleton;
import org.nknsd.robotics.team.components.RotationHandler;

import java.util.concurrent.TimeUnit;

public class AutoStepRotateArm implements NKNAutoStep {
    private final RotationHandler.RotationPositions rotationPosition;
    AutoSkeleton autoSkeleton;
    private long timeBegan;
    private final long duration;

    public AutoStepRotateArm(RotationHandler.RotationPositions rotationPosition) {
        this.rotationPosition = rotationPosition;
        duration = 500;
    }

    @Override
    public void link(AutoSkeleton autoSkeleton) {
        this.autoSkeleton = autoSkeleton;

    }

    public void begin(ElapsedTime runtime, Telemetry telemetry) {
        autoSkeleton.setTargetArmRotation(rotationPosition);
        timeBegan = runtime.now(TimeUnit.MILLISECONDS);
    }

    @Override
    public void run(Telemetry telemetry) {}

    @Override
    public boolean isDone(ElapsedTime runtime) {
        return runtime.now(TimeUnit.MILLISECONDS) - timeBegan > duration;
    }

    @Override
    public String getName() {
        return "Rotating to " + rotationPosition.name();
    }
}
