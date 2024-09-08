package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.util.Other.ArrayTypeValue;
import org.firstinspires.ftc.teamcode.util.Other.DynamicTypeValue;
import org.firstinspires.ftc.teamcode.util.Other.MotorTypeValue;
import org.firstinspires.ftc.teamcode.util.Other.ServoTypeValue;

import java.util.HashMap;

/**
 * The RobotHardwareInitializer abstracts the process of setting the hardware variables in RobotOpMode.
 * Using this class helps to increase readability inside of the RobotOpMode class.
 */
public class RobotHardwareInitializer {
    private static void Error(Exception e, OpMode opMode) {
        FTCDashboardPackets dbp = new FTCDashboardPackets("RobotHardwareInit");
        dbp.createNewTelePacket();

        dbp.error(e, true, false);
        opMode.terminateOpModeNow();
    }

    public final static float MIN_POWER = 0;

    public enum DriveMotor {
        LEFT_FRONT,
        RIGHT_FRONT,
        LEFT_BACK,
        RIGHT_BACK,
        ENCODER_LEFT,
        ENCODER_RIGHT,
        ENCODER_BACK
    }

    public enum Other {
        ARM,
        INTAKE,
        LAUNCHER,
        FINGER,
        WRIST,
        COLOR_SENSOR,
        WEBCAM,
        PIXEL_POOPER
    }

    public enum Intake {
        LEFT,
        RIGHT
    }

    public enum Gate {
        LEFT,
        RIGHT
    }

    public static final String FRONT_LEFT_DRIVE = "fl_drv";
    public static final String FRONT_RIGHT_DRIVE = "fr_drv";
    public static final String BACK_LEFT_DRIVE = "bl_drv";
    public static final String BACK_RIGHT_DRIVE = "br_drv";

    public static final String LEFT_ENCODER = FRONT_LEFT_DRIVE;
    public static final String RIGHT_ENCODER = FRONT_RIGHT_DRIVE;
    public static final String BACK_ENCODER = BACK_LEFT_DRIVE;

    public static final String LEFT_GATE = "gateLeft";
    public static final String RIGHT_GATE = "gateRight";

    public static HashMap<DriveMotor, DcMotor> initializeDriveMotors(final HardwareMap hMap, final OpMode opMode) {
        DcMotor leftFrontDrive;
        DcMotor rightFrontDrive;
        DcMotor leftBackDrive;
        DcMotor rightBackDrive;
        try {
            leftFrontDrive = hMap.get(DcMotor.class, FRONT_LEFT_DRIVE);
            rightFrontDrive = hMap.get(DcMotor.class, FRONT_RIGHT_DRIVE);
            leftBackDrive = hMap.get(DcMotor.class, BACK_LEFT_DRIVE);
            rightBackDrive = hMap.get(DcMotor.class, BACK_RIGHT_DRIVE);

            /*leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/

            leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
            leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
            rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
            rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        } catch (Exception e) {
            Error(e, opMode);
            return null;
        }

        DcMotor encoderLeft;
        DcMotor encoderRight;
        DcMotor encoderBack;

        try {
            encoderLeft = hMap.dcMotor.get(LEFT_ENCODER);
            encoderRight = hMap.dcMotor.get(RIGHT_ENCODER);
            encoderBack = hMap.dcMotor.get(BACK_ENCODER);
        } catch (Exception e) {
            Error(e, opMode);
            return null;
        }

        HashMap<DriveMotor, DcMotor> motorMap = new HashMap<>();

        motorMap.put(DriveMotor.LEFT_FRONT, leftFrontDrive);
        motorMap.put(DriveMotor.RIGHT_FRONT, rightFrontDrive);
        motorMap.put(DriveMotor.LEFT_BACK, leftBackDrive);
        motorMap.put(DriveMotor.RIGHT_BACK, rightBackDrive);
        motorMap.put(DriveMotor.ENCODER_LEFT, encoderLeft);
        motorMap.put(DriveMotor.ENCODER_RIGHT, encoderRight);
        motorMap.put(DriveMotor.ENCODER_BACK, encoderBack);

        return motorMap;
    }

    public static HashMap<Gate, Servo> initializeGateServos(final OpMode opMode) {
        HashMap<Gate, Servo> servos = new HashMap<>();

        Servo left = null;
        Servo right = null;
        try {
            left = opMode.hardwareMap.get(Servo.class, LEFT_GATE);
            right = opMode.hardwareMap.get(Servo.class, RIGHT_GATE);
        } catch (Exception e) {
            Error(e, opMode);
        }

        servos.put(Gate.LEFT, left);
        servos.put(Gate.RIGHT, right);
        return servos;
    }

    /** @noinspection rawtypes*/
    public static HashMap<Other, DynamicTypeValue> initializeAllOtherSystems(final OpMode opMode) {
        HashMap<Other, DynamicTypeValue> out = new HashMap<>();
        // Init Arm
        HashMap<Arm, DcMotor> tmp = initializeArm(opMode);
        final DcMotor ARM1 = tmp.get(Arm.ARM1);
        final DcMotor ARM2 = tmp.get(Arm.ARM2);
        DcMotor[] tmp2 = new DcMotor[2];
        tmp2[0] = ARM1;
        tmp2[1] = ARM2;
        out.put(Other.ARM, new ArrayTypeValue<>(tmp2));

        // Init Wrist
        out.put(Other.WRIST, new MotorTypeValue(initializeWrist(opMode)));

        // Init Finger
        out.put(Other.FINGER, new ServoTypeValue(initializeFinger(opMode)));

        // Init Launcher
        out.put(Other.LAUNCHER, new ServoTypeValue(initializeLauncher(opMode)));

        // Init Color Sensor
        //out.put(Other.COLOR_SENSOR, new ColorSensorTypeValue(initializeColorSensor(opMode)));

        // Init Pixel Pooper
        out.put(Other.PIXEL_POOPER, new ServoTypeValue(initializePixelPooper(opMode)));

        /*
        // Init Intake
        HashMap<Intake, CRServo> tmp3 = initializeIntake(opMode);
        final CRServo LEFT = tmp3.get(Intake.LEFT);
        final CRServo RIGHT = tmp3.get(Intake.RIGHT);
        CRServo[] tmp4 = new CRServo[2];
        tmp4[0] = LEFT;
        tmp4[1] = RIGHT;
        out.put(Other.INTAKE, new ArrayTypeValue<>(tmp4));]
         */

        // Init Webcam
        HashMap<Cameras, WebcamName> tmp5 = initializeCamera(opMode);
        assert tmp5 != null;
        out.put(Other.WEBCAM, new ArrayTypeValue<>(tmp5.values().toArray()));

        return out;
    }

    public enum Arm {
        ARM1,
        ARM2
    }

    public static HashMap<Arm, DcMotor> initializeArm(final OpMode opMode) {
        DcMotor arm = null;
        DcMotor arm2 = null;
        try {
            arm = opMode.hardwareMap.get(DcMotor.class, "arm1");
            arm.setDirection(DcMotorSimple.Direction.FORWARD);

            arm2 = opMode.hardwareMap.get(DcMotor.class, "arm2");
            arm2.setDirection(DcMotorSimple.Direction.FORWARD);
        } catch (Exception e) {
            Error(e, opMode);
        }

        HashMap<Arm, DcMotor> out = new HashMap<>();
        out.put(Arm.ARM1, arm);
        out.put(Arm.ARM2, arm2);

        return out;
    }

    public static HashMap<Intake, CRServo> initializeIntake(final OpMode opMode) {
        CRServo left = null;
        CRServo right = null;

        try {
            left = opMode.hardwareMap.get(CRServo.class, "intakeLeft");
            right = opMode.hardwareMap.get(CRServo.class, "intakeRight");
        } catch (Exception e) {
            Error(e, opMode);
        }

        HashMap<Intake, CRServo> out = new HashMap<>();
        out.put(Intake.LEFT, left);
        out.put(Intake.RIGHT, right);
        return out;
    }

    public static Servo initializeLauncher(final OpMode opMode) {
        try {
            return opMode.hardwareMap.get(Servo.class, "launcher");
        } catch (Exception e) {
            Error(e, opMode);
        }
        return null;
    }

    public static DcMotorEx initializeWrist(final OpMode opMode) {
        try {
            DcMotorEx motor = opMode.hardwareMap.get(DcMotorEx.class, "wrist"); // port 2
            return motor;
        } catch(Exception e) {
            Error(e, opMode);
        }
        return null;
    }

    public static Servo initializeFinger(final OpMode opMode) {
        try {
            Servo servo = opMode.hardwareMap.get(Servo.class, "finger_servo");
            servo.setDirection(Servo.Direction.FORWARD);
            return servo;
        } catch(Exception e) {
            Error(e, opMode);
        }
        return null;
    }

    public static Servo initializePixelPooper(final OpMode opMode) {
        try {
            Servo servo = opMode.hardwareMap.get(Servo.class, "pixel_pooper");
            servo.setDirection(Servo.Direction.FORWARD);
            return servo;
        } catch(Exception e) {
            Error(e, opMode);
        }
        return null;
    }

    public static ColorSensor initializeColorSensor(final OpMode opMode) {
        try {
            return opMode.hardwareMap.get(ColorSensor.class, "color_sensor");
        } catch(Exception e) {
            Error(e, opMode);
        }
        return null;
    }

    public enum Cameras {
        CAM1,
        CAM2
    }

    public static HashMap<Cameras, WebcamName> initializeCamera(final OpMode opMode) {
        HashMap<Cameras, WebcamName> out = new HashMap<>();
        try {
            out.put(Cameras.CAM1, opMode.hardwareMap.get(WebcamName.class, "Webcam 1"));
            out.put(Cameras.CAM2, opMode.hardwareMap.get(WebcamName.class, "Webcam 2"));
            return out;
        } catch(Exception e) {
            Error(e, opMode);
        }
        return null;
    }
}
