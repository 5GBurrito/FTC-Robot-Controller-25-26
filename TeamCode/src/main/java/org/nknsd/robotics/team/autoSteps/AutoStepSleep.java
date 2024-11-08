package org.nknsd.robotics.team.autoSteps;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.robotics.framework.NKNAutoStep;
import org.nknsd.robotics.team.autonomous.AutoSkeleton;

import java.util.concurrent.TimeUnit;

public class AutoStepSleep implements NKNAutoStep {
    AutoSkeleton autoSkeleton;
    private final long time;
    private long startTime;

    public AutoStepSleep(long time) {
        this.time = time;
    }

    @Override
    public void link(AutoSkeleton autoSkeleton) {
        this.autoSkeleton = autoSkeleton;

    }

    public void begin(ElapsedTime runtime, Telemetry telemetry) {
        startTime = runtime.now(TimeUnit.MILLISECONDS);
    }

    @Override
    public void run(Telemetry telemetry) {}

    @Override
    public boolean isDone(ElapsedTime runtime) {
        return runtime.now(TimeUnit.MILLISECONDS) - startTime > time;
    }

    @Override
    public String getName() {
        return "Adjusting target";
    }
}
