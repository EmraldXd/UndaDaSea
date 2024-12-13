package org.firstinspires.ftc.teamcode.init;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.action.claw;
import org.firstinspires.ftc.teamcode.action.linearSlides;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous (name = "Colored Auto")
public class RoadRunnerAutoTwo extends OpMode {
    org.firstinspires.ftc.teamcode.action.linearSlides linearSlides = new linearSlides();
    org.firstinspires.ftc.teamcode.action.claw claw = new claw();
    ElapsedTime actionRuntime = new ElapsedTime();
    ElapsedTime autoRuntime = new ElapsedTime();
    TrajectorySequence blue;
    SampleMecanumDrive drive;
    boolean unrun;
    double offsetX = -12.98;
    double offsetY = 62.06;
    double offsetHead = 0;

    public void init() {
        linearSlides.init(this);
        claw.init(this);

        drive = new SampleMecanumDrive(hardwareMap);
    }

    public void start() {
        blue = drive.trajectorySequenceBuilder(new Pose2d(12.98 + offsetX, -62.06 + offsetY, Math.toRadians(90.00 + offsetHead)))
                .splineTo(new Vector2d(-1.85 + offsetX, -34.04 + offsetY), Math.toRadians(90.00 + offsetHead))
                .lineToSplineHeading(new Pose2d(24.99 + offsetX, -53.17 + offsetY, Math.toRadians(-0.78 + offsetHead)))
                .lineTo(new Vector2d(36.41 + offsetX, -37.30 + offsetY))
                .lineTo(new Vector2d(36.70 + offsetX, -12.09 + offsetY))
                .lineTo(new Vector2d(48.87 + offsetX, -12.83 + offsetY))
                .lineTo(new Vector2d(49.16 + offsetX, -51.53 + offsetY))
                .lineTo(new Vector2d(48.57 + offsetX, -9.71 + offsetY))
                .lineTo(new Vector2d(56.28 + offsetX, -11.20 + offsetY))
                .lineTo(new Vector2d(59.39 + offsetX, -51.39 + offsetY))
                .splineToSplineHeading(new Pose2d(34.33 + offsetX, -60.14 + offsetY, Math.toRadians(268.25 + offsetHead)), Math.toRadians(268.25 + offsetHead))
                .splineTo(new Vector2d(1.11 + offsetX, -34.04 + offsetY), Math.toRadians(90.00 + offsetHead))
                .lineTo(new Vector2d(39.23 + offsetX, -38.93 + offsetY))
                .lineTo(new Vector2d(62.81 + offsetX, -10.01 + offsetY))
                .lineTo(new Vector2d(62.95 + offsetX, -52.42 + offsetY))
                .splineToSplineHeading(new Pose2d(36.56 + offsetX, -59.00 + offsetY, Math.toRadians(251.57 + offsetHead)), Math.toRadians(-90.00 + offsetHead))
                .splineTo(new Vector2d(3.78 + offsetX, -33.59 + offsetY), Math.toRadians(86.50 + offsetHead))
                .lineTo(new Vector2d(56.43 + offsetX, -58.21 + offsetY))
                .build();


        unrun = true;
    }

    @Override
    public void loop() {
        if(unrun) {
            drive.followTrajectorySequence(blue);
        }
    }
}
