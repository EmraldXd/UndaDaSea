package org.firstinspires.ftc.teamcode.init;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.action.mecanumDrive;
@TeleOp (name = "MecanumDrive", group = "Main")
public class teleOp extends OpMode {
    mecanumDrive mecanumDrive = new mecanumDrive();
    @Override
    public void init() {
        //Initialize our motors
        mecanumDrive.init(this);
    }

    public void start() {
        mecanumDrive.runWithoutEncoder();
    }

    @Override
    public void loop() {
        //Controls for mecanumDrive()
        mecanumDrive.slowMode(gamepad1.left_bumper);
        mecanumDrive.setPower(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        mecanumDrive.telemetryOutput();
    }
}