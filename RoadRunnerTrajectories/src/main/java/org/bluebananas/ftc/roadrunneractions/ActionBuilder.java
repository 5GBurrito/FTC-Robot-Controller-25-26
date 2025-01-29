package org.bluebananas.ftc.roadrunneractions;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

import org.bluebananas.ftc.roadrunneractions.TrajectoryActionBuilders.BlueBasket;

import java.util.function.Function;


public class ActionBuilder {
//Begin Blue Paths

    //Path for BlueSpecimen
    public static Action BlueSpecimen(Function<Pose2d, TrajectoryActionBuilder> builderFunction)
    {
        Pose2d startPose= new Pose2d(-15, 63, Math.toRadians(-90));
        TrajectoryActionBuilder builder = builderFunction.apply(startPose);
        return builder
                .splineTo(new Vector2d(-9,42), Math.toRadians(-90))
                .waitSeconds(1)//hook preloaded specimen
                .strafeTo(new Vector2d(-12,42))
                .splineToSplineHeading(new Pose2d(-36,24, Math.toRadians(0)), Math.toRadians(-90))
                .splineTo(new Vector2d(-42,12), Math.toRadians(180))
                .strafeTo(new Vector2d(-45,12))
                .strafeTo(new Vector2d(-45,48))
                .strafeTo(new Vector2d(-45,24))
                .splineTo(new Vector2d(-57,12), Math.toRadians(180))
                .strafeTo(new Vector2d(-57,48))
                .strafeTo(new Vector2d(-57,42))
                .waitSeconds(1)//wait for human player
                .strafeTo(new Vector2d(-63,63))
                .build();
    }

    //Path for BlueBasket
    public static Action BlueBasket(Function<Pose2d, TrajectoryActionBuilder> builderFunction)
    {
        //Vector2d basket_clear_position = new Vector2d(56.5, 55.875);
        //double basket_clear_heading = Math.toRadians(45);

        //Vector2d start_position = new Vector2d(31, 61.875);
        //double start_heading = Math.toRadians(180);

        //Vector2d drop_position = new Vector2d(56.5, 55.875);
        //Vector2d offwall_position = start_position.plus(drop_position.minus(start_position).times(.5));
        //double drop_heading = Math.toRadians(-135);

        Vector2d outer_sample_pickup_position = new Vector2d(45, 40);
        double sample_pickup_heading = Math.toRadians(-90);

        //Pose2d startPose = BlueBasket.startPose;
        TrajectoryActionBuilder builder = builderFunction.apply(BlueBasket.pose_basket_init);
        return builder

                .setTangent(-45)
                .splineToLinearHeading(BlueBasket.pose_basket_drop, 0)

                //.waitSeconds(3)//bring up arm

                //.splineTo(drop_position, drop_heading)
//                .waitSeconds(0.5)//deposit sample
//                .lineToXConstantHeading(basket_clear_position.x)
//                .waitSeconds(2)//bring down arm
//                .splineTo(outer_sample_pickup_position, sample_pickup_heading)
//                .waitSeconds(2)//pick up sample
//                .strafeToLinearHeading(basket_clear_position, basket_clear_heading)
//                .waitSeconds(3)//bring up arm
//                .splineTo(drop_position, drop_heading)
//                .waitSeconds(0.5)//deposit sample
//                .lineToXConstantHeading(basket_clear_position.x)
//                .waitSeconds(2)//bring down arm
//                .strafeToLinearHeading(outer_sample_pickup_position.plus(new Vector2d(10.5, 0)), sample_pickup_heading)
//                .waitSeconds(2)//pick up sample
//                .strafeToLinearHeading(basket_clear_position, basket_clear_heading)
//                .waitSeconds(3)//bring up arm
//                .splineTo(drop_position, drop_heading)
//                .waitSeconds(0.5)//deposit sample
//                .lineToXConstantHeading(basket_clear_position.x)
//                .waitSeconds(2)//bring down arm
//                .splineTo(new Vector2d(24,60), Math.toRadians(180))
//                .strafeTo(new Vector2d(-36,60))
                .build();
    }

//End Blue Paths

//Begin Red Paths

    //Path for RedSpecimen
    public static Action RedSpecimen(Function<Pose2d, TrajectoryActionBuilder> builderFunction)
    {
        Pose2d startPose = new Pose2d(15, -63, Math.toRadians(90));
        TrajectoryActionBuilder builder = builderFunction.apply(startPose);
        return builder
                .splineTo(new Vector2d(9,-42), Math.toRadians(90))
                .waitSeconds(1)//hook preloaded specimen
                .strafeTo(new Vector2d(12,-42))
                .splineToSplineHeading(new Pose2d(36,-24, Math.toRadians(180)), Math.toRadians(90))
                .splineTo(new Vector2d(42,-12), Math.toRadians(0))
                .strafeTo(new Vector2d(45,-12))
                .strafeTo(new Vector2d(45,-48))
                .strafeTo(new Vector2d(45,-24))
                .splineTo(new Vector2d(57,-12), Math.toRadians(0))
                .strafeTo(new Vector2d(57,-48))
                .strafeTo(new Vector2d(57,-42))
                .waitSeconds(1)//wait for human player
                .strafeTo(new Vector2d(63,-63))
                .build();

    }

    //Path for RedBasket
    public static Action RedBasket(Function<Pose2d, TrajectoryActionBuilder> builderFunction) {
        Pose2d startPose = new Pose2d(0, 0, 0);
        TrajectoryActionBuilder builder = builderFunction.apply(startPose);
        return builder
                .build();
    }

    //Path for RedRightOption2
    public static Action RedRightOption2(Function<Pose2d, TrajectoryActionBuilder> builderFunction) {
        Pose2d startPose = new Pose2d(10, -63, Math.toRadians(90.0));
        TrajectoryActionBuilder builder = builderFunction.apply(startPose);
        return builder
                .strafeTo(new Vector2d(10.0, -45.0))
                .waitSeconds(1)
                .strafeTo(new Vector2d(25, -45))
                .splineTo(new Vector2d(45, -10), Math.toRadians(0))
//                .strafeTo(new Vector2d(28, -10))
//                .strafeTo(new Vector2d(46, -10))
                .strafeTo(new Vector2d(45, -55))
                .strafeToLinearHeading(new Vector2d(45, -30), Math.toRadians(270.0))
                .waitSeconds(1)
                .strafeTo(new Vector2d(45, -45))
                .waitSeconds(1)
                .splineTo(new Vector2d(10, -45), Math.toRadians(90))
//                .splineTo(new Vector2d(46, -30), Math.toRadians(180))
                .build();
    }

//End Red Paths
}