package org.firstinspires.ftc.teamcode.action;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.text.DecimalFormat;

public class claw {
    static final DecimalFormat df = new DecimalFormat("0.00");
    Telemetry telemetry;
    private double DELAY = 0.75;
    private static final ElapsedTime elapsedTime = new ElapsedTime();
    private Servo claw;
    private Servo clawJointA;
    private Servo clawJointB;
    private Servo clawAngle;
    private boolean isClosed;
    private boolean isTurned;
    private boolean clawToggle;
    public void init(@NonNull OpMode opmode){
        HardwareMap hardwareMap = opmode.hardwareMap;
        telemetry = opmode.telemetry;
        //initialize servo motors
        claw = hardwareMap.get(Servo.class, "Claw");
        clawJointA = hardwareMap.get(Servo.class, "Joint A");
        clawJointB = hardwareMap.get(Servo.class, "Joint B");
        clawAngle = hardwareMap.get(Servo.class, "Angler");
        isClosed = false;
        isTurned = false;
        clawToggle = false;
        //Turn Servos on

    }

    public void startTime() {
        elapsedTime.reset();
    }

    public void moveClaw(double x, boolean toggle) {
        if(toggle && elapsedTime.time() > DELAY && !clawToggle) {
            clawToggle = true;
            elapsedTime.reset();
        } else if (toggle && elapsedTime.time() > DELAY && clawToggle) {
            clawToggle = false;
            elapsedTime.reset();
        }
        if(!clawToggle) {
            if (x > 0 && clawJointA.getPosition() < .55) {
                clawJointA.setPosition(clawJointA.getPosition() + (x * .05));
            } else if (x < 0 && clawJointA.getPosition() > .25) {
                clawJointA.setPosition(clawJointA.getPosition() - (x * .05));
            }
        } else if (clawToggle) {
            if (x > 0 && clawJointB.getPosition() < .67) {
                clawJointB.setPosition(clawJointB.getPosition() + (x * .05));
            } else if (x < 0 && clawJointB.getPosition() > 1) {
                clawJointB.setPosition(clawJointB.getPosition() - (x * .05));
            }
        }
    }

    public void useClaw(boolean isPressed) {
        if (isPressed && elapsedTime.time() > DELAY && !isClosed) {
            claw.setPosition(0);
            isClosed = true;
            elapsedTime.reset();
        } else if (isPressed && elapsedTime.time() > DELAY && isClosed) {
            claw.setPosition(0.8);
            isClosed = false;
            elapsedTime.reset();
        }
    }

    public void rotateClaw(boolean isPressed) {
        if(isPressed && elapsedTime.time() > DELAY && !isTurned) {
            claw.setPosition(0);
            isTurned = true;
        } else if (isPressed && elapsedTime.time() > DELAY && isTurned){
            claw.setPosition(0);
            isTurned = false;
        }
    }
}
