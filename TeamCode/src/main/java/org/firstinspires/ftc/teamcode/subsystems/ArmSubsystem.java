package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.util.FTCDashboardPackets;
import org.firstinspires.ftc.teamcode.util.RobotHardwareInitializer;

import java.util.HashMap;

public class ArmSubsystem extends SubsystemBase {
    private final DcMotor bucketArmMotor; // Bucket arm
    private final DcMotor horizontalArmMotor; //
    private final DcMotor hangingArmMotor;

    public enum ARMS {
        BUCKET_ARM,
        HORIZONTAL_ARM,
        HANGING_ARM,
        ALL;
    }

    private final FTCDashboardPackets dbp = new FTCDashboardPackets("ArmSubsystem");

    public ArmSubsystem(final HashMap<RobotHardwareInitializer.Arm, DcMotor> ARM) {
        this.bucketArmMotor = ARM.get(RobotHardwareInitializer.Arm.ARM1);
        assert this.bucketArmMotor != null;
        bucketArmMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        bucketArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bucketArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        this.horizontalArmMotor = ARM.get(RobotHardwareInitializer.Arm.ARM2);
        assert this.horizontalArmMotor != null;
        horizontalArmMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        horizontalArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        horizontalArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        this.hangingArmMotor = ARM.get(RobotHardwareInitializer.Arm.ARM3);
        assert this.hangingArmMotor != null;
        hangingArmMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        hangingArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hangingArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Sets all arms power to the inputted value
     * @param power the power to set the arms to
     */
    public void manualMoveArm(double power) {
        manualMoveArm(power, ARMS.ALL);
    }

    /**
     * Sets a specified arm's power
     * @param power the power to set the arm to
     * @param arm the arm to set the power to
     */
    public void manualMoveArm(double power, ARMS arm) {
        switchArmMode(DcMotor.RunMode.RUN_USING_ENCODER, arm);
        switch (arm) {
            case ALL:
                manualMoveHorizontalArm(power);
                manualMoveHangingArm(power);
                manualMoveBucketArm(power);
                break;
            case BUCKET_ARM:
                manualMoveBucketArm(power);
                break;
            case HANGING_ARM:
                manualMoveHangingArm(power);
                break;
            case HORIZONTAL_ARM:
                manualMoveHorizontalArm(power);
                break;
            default:
                dbp.error("Unknown arm type: " + arm);
                break;
        }
    }

    public void manualMoveBucketArm(double power) {
        bucketArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bucketArmMotor.setPower(power);
    }

    public void manualMoveHorizontalArm(double power) {
        horizontalArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        horizontalArmMotor.setPower(power);
    }

    public void manualMoveHangingArm(double power) {
        hangingArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hangingArmMotor.setPower(power);
    }

    public void switchArmMode(DcMotor.RunMode runMode) {
        switchArmMode(runMode, ARMS.ALL);
    }

    public void switchArmMode(DcMotor.RunMode runMode, ARMS arm) {
        switch (arm) {
            case ALL:
                horizontalArmMotor.setMode(runMode);
                bucketArmMotor.setMode(runMode);
                hangingArmMotor.setMode(runMode);
                break;
            case BUCKET_ARM:
                bucketArmMotor.setMode(runMode);
                break;
            case HANGING_ARM:
                hangingArmMotor.setMode(runMode);
                break;
            case HORIZONTAL_ARM:
                horizontalArmMotor.setMode(runMode);
                break;
            default:
                dbp.error("Unknown arm type: " + arm);
                break;
        }
    }

    /**
     * Sets the power of the armMotor to zero
     */
    public void haltAllArms() {
        switchArmMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bucketArmMotor.setPower(0);
        horizontalArmMotor.setPower(0);
        hangingArmMotor.setPower(0);
    }

    public void resetAllArms() {
        switchArmMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public DcMotor getBucketArmMotor() {
        return bucketArmMotor;
    }

    public DcMotor getHorizontalArmMotor() {
        return horizontalArmMotor;
    }

    public DcMotor getHangingArmMotor() {
        return hangingArmMotor;
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}
