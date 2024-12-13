package org.firstinspires.ftc.teamcode.drive;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.action.mecanumDrive;
import org.firstinspires.ftc.teamcode.action.claw;

@Autonomous(name = "ParkByTime", group = "Main")
public class basicAuto extends OpMode {
    //Timers, code, and all that fun stuff
    public ElapsedTime autoRuntime = new ElapsedTime(); //How long autonomous has been doin' its thing for
    ElapsedTime actionRuntime = new ElapsedTime();
    org.firstinspires.ftc.teamcode.action.mecanumDrive mecanumDrive = new mecanumDrive();
    org.firstinspires.ftc.teamcode.action.claw claw = new claw();
    //Our custom stuff to use here
    int robotAction = 0;
    boolean teamSelected = false;
    boolean sideSelected = false;
    boolean throughMiddle = false;

    public void init() {
        mecanumDrive.init(this);
        mecanumDrive.runWithoutEncoder();
        claw.init(this);
    }

    public void start() {
        autoRuntime.reset();
        actionRuntime.reset();
    }

    @Override
    public void loop() {
        if(robotAction == 0) {
            mecanumDriver(1, 0, -.5, 0);
        }
    }

    /**
     * This lets our code know that we want a motor power applied to motors in a specific way, and
     * this OpMode will keep track of the time.
     * @param secondsToRunFor is how long this OpMode needs to keep track of.
     * @param yAxisPower is how fast forward and back we want to go.
     * @param xAxisPower is how fast to the left or right we want to strafe.
     * @param rotation is how fast clockwise or counter-clockwise we want to turn.
     */
    public void mecanumDriver(double secondsToRunFor, double yAxisPower, double xAxisPower, double rotation) {
        mecanumDrive.slowMode(true); //Despite what it says, this will make the robot go at max speed
        yAxisPower = yAxisPower * -1;
        mecanumDrive.setPower(xAxisPower, yAxisPower, rotation);
        if(actionRuntime.time() >= secondsToRunFor) {
            robotAction++; //moves to next action
            actionRuntime.reset(); //resets timer
            mecanumDrive.setPower(0, 0, 0); //stops the motors
        }
    }

    /**
     * This is a custom-built wait() command. We have to have this, because OpModes do not like to
     * looping.
     * @param secondsToWait is our value to wait for,
     */
    public void waitThenGoToNextAction(double secondsToWait) {
        if(actionRuntime.time() >= secondsToWait) {
            robotAction++; //moves to next action
            actionRuntime.reset(); //resets timer
        }
    }
}