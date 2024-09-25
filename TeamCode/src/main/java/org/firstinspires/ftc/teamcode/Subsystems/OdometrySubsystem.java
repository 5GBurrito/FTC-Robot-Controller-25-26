package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import org.firstinspires.ftc.teamcode.RobotContainer;


/** Subsystem */
@Config   // EXAMPLE - use @Config to add public variables to dashboard for realtime updating
public class OdometrySubsystem extends SubsystemBase {

    // for Dashboard demo purposes only!
    // values on dashboard for edit must be public and static
    public static double parameter1= 20.0;
    public static int parameter2 = 7;
    private static int parameter3 = 12;

    // Local objects and variables here
    private double previousLeftPos;
    private double previousRightPos;
    private double previousFrontPos;
    private double fieldX = 0.0;
    private double fieldY = 0.0;
    private double fieldAngle = 0.0;

    /** Place code here to initialize subsystem */
    public OdometrySubsystem() {

    }

    /** Method called periodically by the scheduler
     * Place any code here you wish to have run periodically */
    @Override
    public void periodic() {

        double leftPos;
        double rightPos;
        double frontPos;

        leftPos = RobotContainer.odometryPod.getLeftEncoderDistance();
        rightPos = RobotContainer.odometryPod.getRightEncoderDistance();
        frontPos = RobotContainer.odometryPod.getFrontEncoderDistance();

        double leftChangePos;
        double rightChangePos;
        double frontChangePos;

        leftChangePos = leftPos - previousLeftPos;
        rightChangePos = rightPos - previousRightPos;
        frontChangePos = frontPos - previousFrontPos;

        previousLeftPos = leftPos;
        previousRightPos = rightPos;
        previousFrontPos = frontPos;

        // creating the value of sin theta (aka the angle of the hipotinuse)
        double theta = Math.asin((rightChangePos - leftChangePos)/RobotContainer.odometryPod.LATERAL_DISTANCE);

        // equation that tells us how much the robot has moved forward
        double ForwardChange = (leftChangePos + rightChangePos) / 2.0 ;

        // equation that tells us how much the robot has moved laterally
        double LateralChange = (frontChangePos - RobotContainer.odometryPod.FORWARD_OFFSET * Math.sin(theta));// Lateral means left to right

        double IMUHeading = Math.toRadians(RobotContainer.gyro.getYawAngle());

        double fieldForwardChange = ForwardChange * Math.cos(IMUHeading) - LateralChange * Math.sin(IMUHeading);

        double fieldLateralChange = ForwardChange * Math.sin(IMUHeading) + LateralChange * Math.cos(IMUHeading);

        fieldX += fieldForwardChange;// += means is equal to and add fieldForwardChange to itself

        fieldY += fieldLateralChange;// += means is equal to and add fieldLateralChange to itself

        fieldAngle = IMUHeading;

        RobotContainer.ActiveOpMode.telemetry.addData("fieldX",fieldX);
        RobotContainer.ActiveOpMode.telemetry.addData("fieldY",fieldY);

        // update FTC dashboard with latest odometry info
        UpdateDashBoard();
    }

    // place special subsystem methods here
    public Pose2d getCurrentPos() {
       return new Pose2d(fieldX,fieldY,new Rotation2d(fieldAngle));
    }

    public void setCurrentPos(Pose2d pos){
        fieldX = pos.getX();
        fieldY = pos.getY();
        fieldAngle = pos.getHeading();
        RobotContainer.gyro.setYawAngle(Math.toDegrees(fieldAngle));
    }

    public void resetCurrentPos(){
        setCurrentPos(new Pose2d(0,0,new Rotation2d(0)));
    }


    // Updates dashboard with robot odometry info
    private void UpdateDashBoard()
    {
        // SAMPLE CODE ONLY - Students to make their own code

        // Update field
        // Note: many options available to draw things on field
        // robot position, apriltags, other lines, circles, polygons, text, etc.

        TelemetryPacket field = new TelemetryPacket();
        field.fieldOverlay()
                .drawGrid(0, 0, 144, 144, 7, 7)
                .fillText("Origin", 0, 0, "4px Arial", Math.toRadians(90), false)
                .fillCircle(0,0, 1);
                //.setRotation(Math.toRadians(90))
                //.strokeRect(x,y,width,height)
                //.drawImage("/dash/ftc.jpg", 24, 24, 18, 18, Math.toRadians(90), 24, 24, false);
        RobotContainer.DashBoard.sendTelemetryPacket(field);

        // Show data on dashboard
        double value1 = 1.0;
        double value2 = 5.0;

        // Method #1
        RobotContainer.DBTelemetry.addData("Value 1a", value1);
        RobotContainer.DBTelemetry.addData("Value 2a", value2);
        RobotContainer.DBTelemetry.update();

        // Method #2
        TelemetryPacket data = new TelemetryPacket();
        data.put("Value 1b", value1);
        data.put("Value 2b", value2);
        RobotContainer.DashBoard.sendTelemetryPacket(data);
    }


}