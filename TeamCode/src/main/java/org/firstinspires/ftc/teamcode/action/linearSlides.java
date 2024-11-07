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
    double currentPosition;
    double currentAngle;
    double angleOffset;
    boolean downLastPressed;
    double MAX_FORWARD_DISTANCE = 3250; //Approximately max distance we can horizontally extend in ticks
    double maxExtend;
    double speedRatio;
    double slidesPosition;
    double lastReadPosition;
    double slidesOffset;


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
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Set angle motor offset
        angleOffset = Math.abs(angleMotor.getCurrentPosition());
        slidesOffset = Math.abs(rightSlide.getCurrentPosition());
    }

    public void setSlides() {
        rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * This adds power to the motor that angles the slides.
     * @param up keeps track of if the up button on the dPad is hit
     * @param down keeps track of if the down button on the dPad is hit
     */
    public void angMotorPower(boolean up, boolean down){
        double y;
        //Set booleans to double values to work the linear slides
        y = (up ? .5 : 0) + (down ? -.5 : 0);
        angleMotor.setPower(y * MAX_POWER);
        if(down || up) {
            angleMotor.setPower(y);
            downLastPressed = !downLastPressed;
        } else if (angMotorFalling(Math.abs(angleMotor.getCurrentPosition())) && touchSensor.getState()){
            angleMotor.setPower(0.2);
        }
    }

    public void slidePower(double x) {
        slidesPosition = Math.abs(rightSlide.getCurrentPosition()) - slidesOffset;
        maxExtend = Math.abs(MAX_FORWARD_DISTANCE / Math.cos(ticksToRadians(angleMotor.getCurrentPosition())));
        speedRatio = ((maxExtend - slidesPosition) / 500);
        if(slidesPosition - maxExtend < 0 || x > 0) {
            rightSlide.setPower(x);
            leftSlide.setPower(x);
        } else {
            rightSlide.setPower(-x);
            leftSlide.setPower(-x);
        }
    }

    /**
     * This will take the amount of ticks the motor have moved and translates
     * it to radians, so we can calculate the farthest distance the linear slides can extend to.
     * @param rotation is how many ticks have been counted
     * @return returns the rotation in radians.
     */
    public double ticksToRadians(double rotation) {
        if(!touchSensor.getState()){
            currentPosition = 0;
            angleOffset = Math.abs(rotation);
        } else {
            currentPosition = rotation - angleOffset;
        }
        currentAngle = Math.toRadians(90) * (currentPosition / 650);
        return currentAngle;
    }

    /**
     * This will find if the angle motor is falling
     */
    public boolean angMotorFalling(double angleMotor) {
        if(lastReadPosition == 0) {
            lastReadPosition = Math.abs(angleMotor);
        }
        if(angleMotor - lastReadPosition < 0) {
            return true;
        } else {
            return false;
        }
    }
    public void telemetryOutput() {
        telemetry.addData("Right Motor Power: ", df.format(rightSlide.getPower()));
        telemetry.addData("Right Motor Position: ", df.format(slidesPosition - slidesOffset));
        telemetry.addData("Max distance: ", maxExtend);
        telemetry.addData("Angle Motor Power: ", df.format(angleMotor.getPower()));
        telemetry.addData("Angle Motor Position: ", df.format(Math.toDegrees(ticksToRadians(angleMotor.getCurrentPosition()))) + " Degrees");
        telemetry.addData("Offset: ", df.format(angleOffset));
        telemetry.addData("Position: ", df.format(angleMotor.getCurrentPosition()));
    }
}
