package org.firstinspires.ftc.teamcode.action;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.text.DecimalFormat;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class linearSlides {
    double MAX_POWER = .40;
    static final DecimalFormat df = new DecimalFormat("0.00");
    //Declare null
    DcMotor rightSlide;
    DcMotor leftSlide;
    DcMotor angleMotor;
    Telemetry telemetry;
    DigitalChannel touchSensor;
    HardwareMap hardwareMap;


    public void init(@NonNull OpMode opMode){
        HardwareMap hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        //Initialize motors
        angleMotor = hardwareMap.get(DcMotor.class, "AngleMotor");
        rightSlide = hardwareMap.get(DcMotor.class, "RightSlide");
        leftSlide = hardwareMap.get(DcMotor.class, "LeftSlide");
        //Initialize sensors
        touchSensor = hardwareMap.get(DigitalChannel.class, "Touch Sensor");
        //Set Motor Direction
        rightSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        leftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        angleMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * This adds power to the motor that angles the slides.
     * @param up keeps track of if the up button on the dPad is hit
     * @param down keeps track of if the down button on the dPad is hit
     */
    public void angMotorPower(boolean up, boolean down){
        double y;
        boolean downLastPressed = false;
        //Set booleans to double values to work the linear slides
        y = (up ? 1 : 0) + (down ? -1 : 0);
        angleMotor.setPower(y * MAX_POWER);
        if(down) {
            downLastPressed = true;
        } else if (up) {
            downLastPressed = false;
        }
        if(downLastPressed && touchSensor.getState()){
            angleMotor.setPower(0.05);
        }
    }

    public void slidePower(double x) {
        rightSlide.setPower(x);
        leftSlide.setPower(x);
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
        telemetry.addData("Right Motor Power: ", df.format(rightSlide.getPower()));
        telemetry.addData("Right Motor Position: ", df.format(ticksToInches(rightSlide.getCurrentPosition())));
        telemetry.addData("Angle Motor Power: ", df.format(angleMotor.getPower()));
        telemetry.addData("Angle Motor Position: ", df.format(Math.toDegrees(ticksToRadians(angleMotor.getCurrentPosition()))));
    }
}
