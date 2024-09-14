package org.firstinspires.ftc.teamcode.Drivetrain;

import java.util.List;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.ejml.simple.SimpleMatrix;

import org.firstinspires.ftc.teamcode.Drivetrain.Controllers.DrivetrainMotorController;
import org.firstinspires.ftc.teamcode.Localization.DeadWheelOdometery;
import org.firstinspires.ftc.teamcode.Drivetrain.Controllers.PoseController;

public class Drivetrain {
    /**
     * Hardware map
     */
    HardwareMap hardwareMap = null;

    public SimpleMatrix state = new SimpleMatrix(6, 1);
    public DeadWheelOdometery deadWheelOdo;
    public DrivetrainMotorController motorController;
    /**
     * Drive motors
     */
    public DcMotorEx motorLeftFront = null;
    public DcMotorEx motorLeftBack = null;
    public DcMotorEx motorRightBack = null;
    public DcMotorEx motorRightFront = null;
    public SimpleMatrix wheelPowerPrev = new SimpleMatrix(4, 1);
    public PoseController poseControl = new PoseController();
    public static double acceptablePowerDifference = 0.000001; // The acceptable difference between current and previous wheel power to make a hardware call
    public SimpleMatrix prevWheelSpeeds = new SimpleMatrix( new double[][]{
            new double[]{0},
            new double[]{0},
            new double[]{0},
            new double[]{0}
    });
    public ElapsedTime deltaT = new ElapsedTime();
    public Drivetrain(HardwareMap hwMap){
        hardwareMap = hwMap;
        motorController = new DrivetrainMotorController(hwMap);
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        motorLeftFront = hardwareMap.get(DcMotorEx.class, "lfm");
        motorLeftBack = hardwareMap.get(DcMotorEx.class, "lbm");
        motorRightBack = hardwareMap.get(DcMotorEx.class, "rbm");
        motorRightFront = hardwareMap.get(DcMotorEx.class, "rfm");
        motorLeftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        motorLeftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        motorRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        motorRightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /**
         * Establish that motors will not be using their native encoders:
         * 'RUN_WITHOUT_ENCODER' does not actually run without encoders, it
         * deactivates the PID
         */
        motorLeftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLeftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        /**
         * Establish zero-power behavior
         */
        motorLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /**
         * Set zero power to all motors upon initialization
         */
        motorLeftFront.setPower(0);
        motorLeftBack.setPower(0);
        motorRightFront.setPower(0);
        motorRightBack.setPower(0);
        deadWheelOdo = new DeadWheelOdometery(hwMap, motorRightBack, motorRightFront, motorLeftBack);
        deltaT.reset();
    }
    public void localize() {
        state = deadWheelOdo.calculate(state);
    }
    public void setPower(SimpleMatrix powers) {
        double u0 = powers.get(0, 0);
        double u1 = powers.get(1, 0);
        double u2 = powers.get(2, 0);
        double u3 = powers.get(3, 0);
        double u0Prev = wheelPowerPrev.get(0, 0);
        double u1Prev = wheelPowerPrev.get(1, 0);
        double u2Prev = wheelPowerPrev.get(2, 0);
        double u3Prev = wheelPowerPrev.get(3, 0);
        if (Math.abs(u0Prev - u0) > acceptablePowerDifference) {
            motorLeftFront.setPower(powers.get(0, 0));
        }
        if (Math.abs(u1Prev - u1) > acceptablePowerDifference) {
            motorLeftBack.setPower(powers.get(1, 0));
        }
        if (Math.abs(u2Prev - u2) > acceptablePowerDifference) {
            motorRightBack.setPower(powers.get(2, 0));
        }
        if (Math.abs(u3Prev - u3) > acceptablePowerDifference) {
            motorRightFront.setPower(powers.get(3, 0));
        }
        wheelPowerPrev.set(0,0,u0);
        wheelPowerPrev.set(1,0,u1);
        wheelPowerPrev.set(2,0,u2);
        wheelPowerPrev.set(3,0,u3);
    }
    public void setWheelSpeedAcceleration(SimpleMatrix wheelSpeeds, SimpleMatrix wheelAccelerations){
        setPower(motorController.calculate(wheelSpeeds,wheelAccelerations));
    }
    public void goToPose(SimpleMatrix desiredPose){
        SimpleMatrix pose = state.extractMatrix(0,3,0,1);
        SimpleMatrix wheelSpeeds = poseControl.calculate(pose, desiredPose);
        SimpleMatrix wheelAccelerations = wheelSpeeds.minus(prevWheelSpeeds).scale(1/deltaT.seconds());
        deltaT.reset();
        setWheelSpeedAcceleration(wheelSpeeds, wheelAccelerations);
        prevWheelSpeeds = wheelSpeeds;
    }
}
