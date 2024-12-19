package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="EviumMainTeleop", group="TeleOp")
public class EviumMainTeleop extends OpMode {

    // Motors & Sensors
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor cap;
    private DcMotor spindle;
    private SparkFunOTOS odometry;
    private RevTouchSensor Lswitch;

    // Servo Button Toggles
    private boolean LSLowerOut = true;
    private boolean LSLowerToggling = false;
    private double LSLowerPos;
    private boolean LSTopOut;
    private boolean LSTopToggling;
    private double lolclock = 0.01;
    private double capPower;

    // Servos
    private ServoImplEx LSLower;
    private ServoImplEx LSTop;

    // Constants for mecanum drive
    private final double DRIVE_SENSITIVITY = 1.0;

    @Override
    public void init() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "leftFront");
        frontRightMotor = hardwareMap.get(DcMotor.class, "rightFront");
        backLeftMotor = hardwareMap.get(DcMotor.class, "leftBack");
        backRightMotor = hardwareMap.get(DcMotor.class, "rightBack");

        spindle = hardwareMap.get(DcMotor.class, "spindle");
        odometry = hardwareMap.get(SparkFunOTOS.class, "odometry");
        cap = hardwareMap.get(DcMotor.class, "cap");
        LSLower = hardwareMap.get(ServoImplEx.class, "LSLower");
        LSTop = hardwareMap.get(ServoImplEx.class, "LSTop");
        Lswitch = hardwareMap.get(RevTouchSensor.class, "Lswitch");


        // Define Servo range
        LSLower.setPwmEnable();
        LSTop.setPwmEnable();
        LSLower.scaleRange(0, 1);
        LSTop.scaleRange(0, 1);

        // Set motor directions
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set motor modes
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        cap.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        spindle.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Breaking mode
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        cap.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spindle.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Make sure odo is ready
        odometry.begin();
        odometry.setPosition(new SparkFunOTOS.Pose2D(0,0,0));
    }

    @Override
    public void start() {
        telemetry.speak("Evium Start");
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
    }

    @Override
    public void loop() {
        if (lolclock > 0.05) {
            lolclock = lolclock - 0.001;
        } else {
            lolclock = lolclock + 0.001;
        }
        // Get joystick values
        double y;
        double x;
        double rx;

        if (gamepad1.right_trigger > 0.33) {
            y = -gamepad1.left_stick_y * 0.2;
            x = gamepad1.left_stick_x * 0.5;
            rx = gamepad1.right_stick_x * 0.25;
        } else {
            y = Math.pow(-gamepad1.left_stick_y, 3);
            x = Math.pow(gamepad1.left_stick_x, 3);
            rx = gamepad1.right_stick_x;
        }

        // Set power to motors
//        double heading = Math.toRadians(odometry.getPosition().h);
        frontLeftMotor.setPower(y + x + rx);
        backLeftMotor.setPower(y - x + rx);
        frontRightMotor.setPower(y - x - rx);
        backRightMotor.setPower(y + x - rx);
        if (cap.getCurrentPosition() > 675) {
            capPower = -0.25;
        } else {
            if (gamepad2.right_trigger > 0.05) {
                capPower = -gamepad2.right_trigger;
            } else {
                capPower = gamepad2.left_trigger;
            }
        }
        cap.setPower(capPower);
        if (Lswitch.isPressed()) {
            telemetry.speak("Warning Capstan. Pull Up");
            cap.setPower(capPower * 0.1);
        }
        spindle.setPower(-gamepad2.left_stick_y * 0.75);
        // Linear Slide Lower
        if ((gamepad2.x || gamepad1.x) && !LSLowerToggling) {
            LSLowerOut = !LSLowerOut;
            LSLowerToggling = true;
        }
        if ((!gamepad2.x) && (!gamepad1.x)) {
            LSLowerToggling = false;
        }
        // Linear Servo Top
        if ((gamepad2.a ||gamepad1.a) && !LSTopToggling) {
            LSTopOut = !LSTopOut;
            LSTopToggling = true;
        }
        if ((!gamepad2.a) && (!gamepad1.a)) {
            LSTopToggling = false;
        }
        if (LSTopOut) {
            LSTop.setPosition(0.75 + lolclock);
        } else {
            LSTop.setPosition(0 + lolclock);
        }
        if (LSLowerOut) {
            LSLower.setPosition(0.9 + lolclock);
        } else {
            LSLower.setPosition(0.2 + lolclock);
        }
        // Telemetry for debugging
        SparkFunOTOS.Pose2D pos = odometry.getPosition();
        telemetry.addData("OdoX", pos.x);
        telemetry.addData("OdoY", pos.y);
        telemetry.addData("Angle", pos.h);
        telemetry.addData("lstop", LSTop.getPosition());
        telemetry.addData("lsbottom", LSLower.getPosition());
        telemetry.addData("Left Stick Y", gamepad2.left_stick_y);
        telemetry.update();
    }
}
