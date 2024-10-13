package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name="Competition Teleop2024NewRobot", group="Iterative Opmode")
public class CompetitionTeleop2024NewRobot extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    //Declare the wheels
    //test
    private DcMotor LF = null; //Located on Control Hub- Motor port 0
    private DcMotor RF = null; //Located on Control Hub- Motor port 2
    private DcMotor LB = null; //Located on Control Hub- Motor port 1
    private DcMotor RB = null; //Located on Control Hub- Motor port 3
    // Left trigger is upward and Right is Downward.
    private DcMotor lift = null; // location on expansion hub - Motor port 0
    //Declare variables used for our arm lift

    // positive Y is upward on joystick, left and right
    private Servo leftArm = null; //Located on Expansion Hub- Servo port 0
    private Servo rightArm = null; //Located on Expansion Hub- Servo port 1
    // positive Y on right joystick will be intake, negative = outtake
    private Servo intake = null; //Located on Expansion Hub- Servo port 2


    //private Servo elbow = null; //Located on Expansion Hub- Servo port 0
    //private Servo gripper = null; //Located on Expansion Hub- Servo port 0
    //private Servo plane = null; //Located on Expansion Hub- Servo port 0

    private IMU imu = null;
    private fileUtils fUtils;

    //variable for Rev Touch Sensor
    //private TouchSensor touch;

    private double PowerFactor = 0.8f; //Max power available for wheels
    private int maxEncode = 4200; //4200 for higher, 2175 for lower-- Max so arm won't overextend and position 3
    private int minEncode = 110;//Minimum so string on arm lift doesn't break and position 0
    private int pos1 = 1850; //Position 1
    private int pos2 = 3000; //Position 2
    private int desiredpos = 0; //Used as base for increasing arm position
    private double armPower = .7f;

    boolean changed = false; //Used for the arm button code
    boolean changed2 = false; //Used for the code that allows the driver to disable IMU controlled direction
    boolean changed3 = false; //Used to toggle between auto and manual mode for arm
    boolean changeda1 = false;
    boolean changedb1 = false;
    boolean disableIMU = true;
    boolean game2back = false; //Used to override switch for arm in case of failure
    boolean game1back = false; //Used to override minEncode for arm in case of bad encoding
    boolean gamebpush = false; //To go through intervals one at a time
    Double initHeading = 0.0;

    //boolean touchIsPressed = false;

    /*
      Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initiali ze the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        LF = hardwareMap.get(DcMotor.class, "LF");
        RF = hardwareMap.get(DcMotor.class, "RF");
        LB = hardwareMap.get(DcMotor.class, "LB");
        RB = hardwareMap.get(DcMotor.class, "RB");
        intake = hardwareMap.get(Servo.class, "intake");
        leftArm = hardwareMap.get(Servo.class, "leftArm");
        rightArm = hardwareMap.get(Servo.class, "rightArm");
        lift = hardwareMap.get(DcMotor.class, "lift");
        //gripper = hardwareMap.get(Servo.class, "gripper");
        //elbow = hardwareMap.get(Servo.class, "elbow");
        //plane = hardwareMap.get(Servo.class, "plane");
        //plane.setPosition(0.0);
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                        RevHubOrientationOnRobot.UsbFacingDirection.LEFT)
        );

        imu.initialize(parameters);

        fUtils = new fileUtils();
        fUtils.readConfig(hardwareMap.appContext, this);
        Pose2d pose = fUtils.getPose();
        initHeading = pose.getHeading();


        //gripper sensor for pulling arm down
        //touch  = hardwareMap.get(TouchSensor .class, "Touch");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery

        LF.setDirection(DcMotor.Direction.FORWARD);
        RF.setDirection(DcMotor.Direction.REVERSE);
        LB.setDirection(DcMotor.Direction.FORWARD);
        RB.setDirection(DcMotor.Direction.REVERSE);
        //intake.setDirection(DcMotor.Direction.FORWARD);
        //Reverse the arm direction so it moves in the proper direction
        //arm.setDirection(DcMotor.Direction.REVERSE);
        //arm1.setDirection(DcMotor.Direction.REVERSE);
        //Set arm up to use encoders
        //arm.setPower(0);
        //arm1.setPower(0);
        //Set arm up to brake
        //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //arm1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */


    /*
     * Code to run ONCE when the driver hits PLAY
     */

    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double LFPower;
        double RFPower;
        double LBPower;
        double RBPower;

        //Code for gamepad1
        //Code for throttling the power factor
        PowerFactor = (1 - gamepad1.right_trigger) *.8f;

        //Code for mecanum wheels

        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y) * PowerFactor;
        Orientation angles = imu.getRobotOrientation(AxesReference.INTRINSIC,
                AxesOrder.ZYX,
                RADIANS);
        double heading = (disableIMU) ? 0.0 : angles.firstAngle + Math.PI/2 + initHeading;
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4 + heading;
        double rightX = Math.pow(gamepad1.right_stick_x, 1.0)*(1-gamepad1.right_trigger);
        LBPower = r * Math.cos(robotAngle) - rightX;
        RBPower = r * Math.sin(robotAngle) + rightX;
        LFPower = r * Math.sin(robotAngle) - rightX;
        RFPower = r * Math.cos(robotAngle) + rightX;

        if (gamepad1.b && ! changed2) {
            changed2 = true;
            disableIMU = !disableIMU;
        }else if (!gamepad1.b) {
            changed2 = false;
        }


        // Send calculated power to wheels
        LB.setPower(LBPower);
        RB.setPower(RBPower);
        LF.setPower(LFPower);
        RF.setPower(RFPower);

        //double intakePower;

        //intakePower = (gamepad2.right_trigger) - gamepad2.left_trigger;
        //intake.setPower(intakePower);

        //Send telemetry data of the motor power for wheels
        telemetry.addData("Left Front Motor","Speed: "+ LFPower);
        telemetry.addData("Left Back Motor","Speed: "+ LBPower);
        telemetry.addData("Right Front Motor","Speed: "+RFPower);
        telemetry.addData("Right Back Motor","Speed: "+ RBPower);
        //telemetry.addData("Intake Motor", "Speed: = "+ intakePower);
        //telemetry.addData("Arm Encoder Height","Height: "+arm.getCurrentPosition());
        telemetry.addData("Initial Heading", "Heading: "+initHeading);

        //Code for gamepad2
        //Toggle auto and manual mode
        if (gamepad2.y) {
            changed3 = !changed3;
        }
        if (gamepad2.back) {
            game2back = !game2back;
        }




        //Moves the arm up
        if (gamepad2.left_trigger >= .1)
        {
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift.setPower(gamepad2.left_trigger * (0.5));
            telemetry.addData("Lift ticks", lift.getCurrentPosition());
            telemetry.update();
            //Moves the arm down
        }
        else if (gamepad2.right_trigger >= .1)
        {
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift.setPower(-gamepad2.right_trigger * (0.5));

        }
        else
        {
            lift.setPower(0);
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        //Code to increase height position

        //Allows the drivers to use a single button to open and close gripper
        double leftArmPosition = leftArm.getPosition();
        double rightArmPosition = rightArm.getPosition();
        double armDelta = 0.005;
        if (gamepad2.left_stick_y > (0.1)) {

            leftArm.setPosition(leftArmPosition + (armDelta));
            rightArm.setPosition(rightArmPosition - (armDelta));


        } else if (gamepad2.left_stick_y < (-0.1))
        {
            leftArm.setPosition(leftArmPosition - (armDelta));
            rightArm.setPosition(rightArmPosition + (armDelta));

        }
        if (gamepad2.right_stick_y > (0.1)) {

            intake.setDirection(Servo.Direction.FORWARD);


        } else if (gamepad2.right_stick_y < (-0.1))
        {
            intake.setDirection(Servo.Direction.REVERSE);

        } else
        {
            intake.setDirection();
        }


    // Show the elapsed game time and wheel power.
            telemetry.addData("Status","Run Time: "+runtime.toString());
        //telemetry.addData("touchIsPressed ", touchIsPressed);
        telemetry.update();
    //  telemetry.addData("positionTarget: ", "%.2f", positionTarget);

}

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
            //Nothing in stop
    }
}





