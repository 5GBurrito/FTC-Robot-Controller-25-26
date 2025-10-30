//package org.firstinspires.ftc.teamcode;
//
//import android.util.Size;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//
//import java.util.List;
//
//@Deprecated
//
//public class Cameracode {@Override
//public void loop() {
//
//    double myX = myAprilTagDetection.ftcPose.x;
//    double myY = myAprilTagDetection.ftcPose.y;
//    double myZ = myAprilTagDetection.ftcPose.z;
//    double myPitch = myAprilTagDetection.ftcPose.pitch;
//    double myRoll = myAprilTagDetection.ftcPose.roll;
//    double myYaw = myAprilTagDetection.ftcPose.yaw;
//
//    telemetry.addData("XDistance", myX);
//    telemetry.addData("YDistance", myY);
//
//    telemetryAprilTag();
//
//    telemetry.update();
//
//    if (gamepad1.dpad_down) {
//        visionPortal.stopStreaming();
//    } else if (gamepad1.dpad_up) {
//        visionPortal.resumeStreaming();
//    }
//
//
//    double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
//    double x = -gamepad1.right_stick_y * 1.1;
//    double rx = -gamepad1.left_stick_x;
//    double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
//    double frontLeftPower = (y + x + rx) / denominator;
//    double backLeftPower = (y - x + rx) / denominator;
//    double frontRightPower = (y - x - rx) / denominator;
//    double backRightPower = (y + x - rx) / denominator;
//
//    FL.setPower(frontLeftPower * 0.85);
//    BL.setPower(-backLeftPower * 0.85);
//    FR.setPower(-frontRightPower * 0.85);
//    BR.setPower(backRightPower * 0.85);
//
//}
//
//    private void buildAprilTag() {
//
//        aprilTag = new AprilTagProcessor.Builder()
//                .setDrawAxes(false)
//                .setDrawCubeProjection(false)
//                .setDrawTagOutline(true)
//                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
//                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
//                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
//
//                .setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
//                .build();
//
//        aprilTag.setDecimation(3);
//
//        VisionPortal.Builder builder = new VisionPortal.Builder();
//
//        if (USE_WEBCAM) {
//            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
//        } else {
//            builder.setCamera(BuiltinCameraDirection.BACK);
//        }
//
//        builder.setCameraResolution(new Size(640, 480));
//
//        builder.enableLiveView(true);
//
//        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
//
//        builder.setAutoStopLiveView(false);
//
//        builder.addProcessor(aprilTag);
//
//        visionPortal = builder.build();
//
//    }
//
//    private void telemetryAprilTag() {
//
//        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
//        telemetry.addData("# AprilTags Detected", currentDetections.size());
//
//        // Step through the list of detections and display info for each one.
//        for (AprilTagDetection detection : currentDetections) {
//            if (detection.metadata != null) {
//                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
//                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
//                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
//                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
//            } else {
//                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
//                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
//            }
//        }   // end for() loop
//
//        // Add "key" information to telemetry
//        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
//        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
//        telemetry.addLine("RBE = Range, Bearing & Elevation");
//
//    }
//}
//
//}
