package org.firstinspires.ftc.teamcode.action;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.text.DecimalFormat;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class linearSlides {

    static final DecimalFormat df = new DecimalFormat("0.00");
    //Declare null
    DcMotor rightSlide;
    DcMotor leftSlide;
    DcMotor angleMotor;
    Telemetry telemetry;
    HardwareMap hardwareMap;


    public void init(@NonNull OpMode opMode){
        HardwareMap hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        //Initialize motors
        angleMotor = hardwareMap.get(DcMotor.class, "AngleMotor");
    }

    /**
     * This adds power to the motor that angles the slides.
     * @param y is the y position of the player 2 left joystick
     */
    public void angMotorPower(double y){
        angleMotor.setPower(y * .25);
    }

    public void addPower(float leftStickY) {
        leftStickY = -leftStickY;
    }

    /**
     * Does what you would expect. Takes the amount of ticks the motor has counted and translates
     * that to a distance value in inches, better for telemetry.
     * @param distance is how many ticks have been counted.
     * @return returns the distance in inches.
     */
    public float ticksToInches(float distance) {
        return distance;
    }

    /**
     * Like, ticksToInches(), this will take the amount of ticks the motor have moved and translates
     * it to radians, so we can calculate the farthest distance the linear slides can extend to.
     * @param rotation is how many ticks have been counted
     * @return returns the rotation in radians.
     */
    public float ticksToRadians(float rotation) {
        return rotation;
    }

    public void telemetryOutput() {
        //telemetry.addData("Right Motor Power: ", df.format(rightSlide.getPower()));
        //telemetry.addData("Right Motor Position: ", df.format(ticksToInches(rightSlide.getCurrentPosition())));
        //telemetry.addData("Left Motor Power: ", df.format(leftSlide.getPower()));
        //telemetry.addData("Left Motor Position: ", df.format(ticksToInches(leftSlide.getCurrentPosition())));
        telemetry.addData("Angle Motor Power: ", df.format(angleMotor.getPower()));
        telemetry.addData("Angle Motor Position: ", df.format(Math.toDegrees(ticksToRadians(angleMotor.getCurrentPosition()))));
    }
}
