package org.firstinspires.ftc.teamcode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.nknsd.robotics.framework.NKNProgram;
import org.nknsd.robotics.team.autonomous.BasketAuto;

import java.util.concurrent.TimeUnit;

@Autonomous(name = "Score Samples in Basket (GOOD)")
public class NKNAutonomous_Basket extends OpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    //Time counters are in milliseconds
    private long lastTelemetryCall = 0;
    private final long TELEMETRY_DELAY = 200;
    // We use these two to delay telemetry outputs to ~200 milliseconds

    // Whatever program is attached here will be loaded with all its glorious components
    private final NKNProgram program = new BasketAuto();

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        runtime.reset();
        //telemetry.addData("Status", "Initializing");
        //telemetry.update();

        program.init(telemetry, hardwareMap, gamepad1, gamepad2);

        //telemetry.addData("Status", "Initialized");
        //telemetry.update();
    }

    // Code to run REPEATEDLY after hitting INIT but before hitting PLAY
    @Override
    public void init_loop() {program.init_loop(runtime, telemetry);}

    // Code to run ONCE when the driver hits PLAY
    @Override
    public void start() {
        runtime.reset();
        program.start(runtime, telemetry);
    }

    // Code to run REPEATEDLY after the driver hits PLAY
    // Does NOT handle telemetry
    @Override
    public void loop() {
        program.loop(runtime, telemetry);

        if (runtime.now(TimeUnit.MILLISECONDS) - TELEMETRY_DELAY > lastTelemetryCall) {
            doTelemetry();
            lastTelemetryCall = runtime.now(TimeUnit.MILLISECONDS);
        }
    }

    // Runs once every ~500 milliseconds
    // ONLY calls for components to do telemetry
    public void doTelemetry() {
        program.doTelemetry(telemetry);
    }

    // Code to run ONCE after the driver hits STOP
    @Override
    public void stop() {program.stop(runtime, telemetry);}

}
