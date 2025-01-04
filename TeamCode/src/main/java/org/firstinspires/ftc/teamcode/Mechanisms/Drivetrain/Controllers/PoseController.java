package org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain.Controllers;

import com.acmerobotics.dashboard.config.Config;

import org.ejml.simple.SimpleMatrix;
import org.firstinspires.ftc.teamcode.Mechanisms.Utils.Controllers.PID;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain.Utils.Utils;

@Config
public class PoseController {
    public PID xPID;
    public PID yPID;
    public PID tPID;
    public static double kPX = 3.05;
    public static double kPY = 3.1;
    public static double kPTheta= 4.45;
    public static double kIX, kIY, kITheta = 0;
    public static double kDX = 0.325;
    public static double kDY = 0.325;
    public static double kDTheta = 0;

    public PoseController(){
        this.xPID = new PID(kPX, kIX, kDX);
        this.yPID = new PID(kPY, kIY, kDY);
        this.tPID = new PID(kPTheta, kITheta, kDTheta);
    }
    public SimpleMatrix calculate(SimpleMatrix pose, SimpleMatrix desiredPose){
        SimpleMatrix errorField = new SimpleMatrix(
                new double[][]{
                        new double[]{desiredPose.get(0,0)-pose.get(0,0)},
                        new double[]{desiredPose.get(1,0)-pose.get(1,0)},
                        new double[]{Utils.angleWrap(desiredPose.get(2, 0) - pose.get(2, 0))}
                }
        );

        SimpleMatrix errorRobot = Utils.rotateGlobalToBody(errorField, pose.get(2,0));

        double vX = xPID.calculate(errorRobot.get(0, 0),0);
        double vY = yPID.calculate(errorRobot.get(1, 0),0);
        double omega = tPID.calculate(Utils.angleWrap(desiredPose.get(2, 0) - pose.get(2, 0)),0);

        SimpleMatrix vRobot = new SimpleMatrix (
                new double[][] {
                        new double[]{vX},
                        new double[]{vY},
                        new double[]{omega}
                }
        );
        return Utils.inverseKinematics(vRobot);
    }
}


