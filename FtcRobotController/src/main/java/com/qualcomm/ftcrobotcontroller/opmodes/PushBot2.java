package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

/*
 * An example linear op mode where the pushbot
 * will drive in a square pattern using sleep()
 * and a for loop.
 *
 * INSTRUCTIONS!
 *
 *
 * this code works in a top to bottom style. it will run one line and then the next one down
  *additons can be made as long as they follow the pattern you see below.
  * always turn the robot on and off completly if the program did not run all of the way.
   *
    * */
public class PushBot2 extends LinearOpMode {
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor grabmotor;
    DcMotor sweeper;
    Servo arm;
    Servo claw;
    Servo Dump;
    @Override
    public void runOpMode() throws InterruptedException {
        leftMotor = hardwareMap.dcMotor.get("motorLeft");
        rightMotor = hardwareMap.dcMotor.get("motorRight");
        grabmotor = hardwareMap.dcMotor.get("g");
        sweeper = hardwareMap.dcMotor.get("t");
        arm = hardwareMap.servo.get("servo_2");
        claw = hardwareMap.servo.get("servo_1"); // channel 1
        Dump = hardwareMap.servo.get("dump");


        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        grabmotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);


        waitForStart(); // this waits until you press the start button
        claw.setPosition(.55);// sets the arm with the hangman to its not dumped position
        sweeper.setPower(1);// turns on the sweeper. if it does not fall set the ARM servo to a number that will push it right now it relies on grvaity
        arm.setPosition(1);

        sleep(1000);// waits for 1 second
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);// tells the motors to use the encoders they are only present on our drive motors
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftMotor.setTargetPosition(-11000); // this is the amount of ticks the encoder will let the motor spin to. after it has reached that it will stop
        //remember negative values move the robot in the direction of the sweeper
        rightMotor.setTargetPosition(-11000);
        leftMotor.setPower(-.5);// sets the speed that that motors will run. positive/ negatives don't matter but it is any value from 0-1 1 being full foward
        // slower is always more precise
        rightMotor.setPower(-.5);
        leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        sleep(7500);// you must allow time for it to finish its run. if this sleep is to small it will only run until that ends
        // for example if i were to set the value to 10000 but this sleep command to 2 seconds it would only run for 2 seconds so give it mroe then enough time and then shorten it if needed
        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);// you must reset the encoders to zero and give them 40 ms or so to do it
        sweeper.setPower(1);
        sleep(40);
        sleep(1000);
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);// next loop is next move. if you need to change motors or servos that are not the drive one
        // put them before the run using encoders button
        leftMotor.setTargetPosition(1000);
        rightMotor.setTargetPosition(-1000);
        leftMotor.setPower(-.5);
        rightMotor.setPower(.5);
        leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        sleep(2000);
        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        sweeper.setPower(1);
        sleep(40);
        sleep(1000);
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftMotor.setTargetPosition(-2600);
        rightMotor.setTargetPosition(-2600);
        leftMotor.setPower(.5);
        rightMotor.setPower(.5);
        leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        sleep(2600);
        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        sweeper.setPower(0);
        sleep(40);

        sweeper.setPower(0);
        arm.setPosition(.7);
        sleep(1000);
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftMotor.setTargetPosition(-1900);
        rightMotor.setTargetPosition(1900);
        leftMotor.setPower(-.5);
        rightMotor.setPower(.5);
        leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        sleep(3500);
        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        sweeper.setPower(0);
        sleep(40);

        sweeper.setPower(0);
        arm.setPosition(.05);
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftMotor.setTargetPosition(-2200);
        rightMotor.setTargetPosition(-2200);
        leftMotor.setPower(.5);
        rightMotor.setPower(.5);
        leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        sleep(2000);
        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        sweeper.setPower(0);
        sleep(40);
        Dump.setPosition(1);
        sleep(3000);
        Dump.setPosition(0);
        sleep(1000);
        grabmotor.setPower(0);
        leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        sleep(50);






    }

}

