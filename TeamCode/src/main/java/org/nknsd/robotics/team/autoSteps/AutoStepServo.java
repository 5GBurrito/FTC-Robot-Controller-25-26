package org.nknsd.robotics.team.autoSteps;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.robotics.framework.NKNAutoStep;
import org.nknsd.robotics.team.autonomous.AutoSkeleton;
import org.nknsd.robotics.team.components.IntakeSpinnerHandler;

import java.util.concurrent.TimeUnit;

public class AutoStepServo implements NKNAutoStep {
    AutoSkeleton autoSkeleton;
    private final IntakeSpinnerHandler.HandStates power;
    private long timeBegan;
    private final long duration;

    public AutoStepServo(IntakeSpinnerHandler.HandStates handState, long duration) {
        this.duration = duration;
        this.power = handState;
    }

    @Override
    public void link(AutoSkeleton autoSkeleton) {
        this.autoSkeleton = autoSkeleton;

    }

    public void begin(ElapsedTime runtime, Telemetry telemetry) {
        autoSkeleton.setServoPower(power);
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
        return "Running servo at " + power + " for " + duration;
    }
}
