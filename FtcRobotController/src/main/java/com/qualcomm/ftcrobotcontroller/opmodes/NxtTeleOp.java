/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class NxtTeleOp extends OpMode {


  // position of the claw servo
  double clawPosition;
  double BackLift;
  int i;

  // amount to change the claw servo position by
  double clawDelta = 0.01;

  // position of the wrist servo
  double wristPosition;

  // amount to change the wrist servo position by
  double wristDelta = 0.01;

  DcMotorController.DeviceMode devMode;
  DcMotorController wheelController;
  DcMotor motorRight;
  DcMotor motorLeft;
  DcMotor tiltMotor;
  DcMotor moveMotor;
  DcMotor grabMotor;
  DcMotor sweeperMotor;
  TouchSensor test1;


  Servo claw;
  Servo wrist;
  Servo Lift;
  Servo Dump;


  int numOpLoops = 1;

  /*-=
   * Code to run when the op mode is first enabled goes here
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#init()
   */
  @Override
  public void init() {
    // these are what defines the motors and servos. The names in green correspond to the configuration file on the phone.

    motorRight = hardwareMap.dcMotor.get("motorLeft");
    motorLeft = hardwareMap.dcMotor.get("motorRight");
    tiltMotor = hardwareMap.dcMotor.get("MT");
    moveMotor = hardwareMap.dcMotor.get("MM");
    grabMotor = hardwareMap.dcMotor.get("g");
    sweeperMotor = hardwareMap.dcMotor.get("t");
    test1 = hardwareMap.touchSensor.get("ts");

    wrist = hardwareMap.servo.get("servo_6"); // channel 6
    claw = hardwareMap.servo.get("servo_1"); // channel 1
    Lift = hardwareMap.servo.get("servo_2");
    Dump = hardwareMap.servo.get("dump");

    wheelController = hardwareMap.dcMotorController.get("wheels");
  }

  /*
   * Code that runs repeatedly when the op mode is first enabled goes here
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#init_loop()
   */
  @Override
  public void init_loop() {

    devMode = DcMotorController.DeviceMode.WRITE_ONLY;
    motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);



    motorRight.setDirection(DcMotor.Direction.REVERSE);
    //motorLeft.setDirection(DcMotor.Direction.REVERSE);

    // set the mode
    // Nxt devices start up in "write" mode by default, so no need to switch device modes here.
    motorLeft.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    motorRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);// ignore crossed out stuff works just fine just bug with android studio
    tiltMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);


    wristPosition = 0.6;
    clawPosition = 0.5;
  }

  /*
   * This method will be called repeatedly in a loop
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
   */
  @Override
  public void loop() {

    // The op mode should only use "write" methods (setPower, setChannelMode, etc) while in
    // WRITE_ONLY mode or SWITCHING_TO_WRITE_MODE
    if (allowedToWrite()) {

    /*
     * Gamepad 1
     *
     * Gamepad 1 controls the motors via the left stick, and it controls the wrist/claw via the a,b,
     * x, y buttons
     */


      // Nxt devices start up in "write" mode by default, so no need to switch modes here.
      motorLeft.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
      motorRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
      tiltMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
      moveMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);


      // throttle:  left_stick_y ranges from -1 to 1, where -1 is full up,  and 1 is full down
      // direction: left_stick_x ranges from -1 to 1, where -1 is full left and 1 is full right


      float throttle = gamepad1.left_stick_y;// this is usedss in dual stick driving
      float throttle2 = gamepad1.right_stick_y;// used in dual and single stick driving
      float power1 = gamepad2.left_stick_y; // not used
      float tilt2 = gamepad2.right_stick_y;// for the grabber arm
      float right = throttle;// used for turing
      float left = throttle2;// used for turning
      float power2 = gamepad2.left_stick_y;

      // clip the right/left values so that the values never exceed +/- 1
      right = Range.clip(right, -1, 1);// just keep them in the useable values
      left = Range.clip(left, -1, 1);
      power1 = Range.clip(power1, -1, 1);
      power2 = Range.clip(power2,-1,1);
      tilt2 = Range.clip(tilt2,-1,1);

      if (gamepad1.left_bumper) {// one stick driving
        throttle2 = -gamepad1.right_stick_y;

        float direction = gamepad1.right_stick_x;
        right = throttle2 -direction;
        left = throttle2 + direction;
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

      }
      throttle2 = -gamepad1.right_stick_y;

      if (gamepad1.b){
        sweeperMotor.setPower(1);


      }
      if (gamepad1.x){
        sweeperMotor.setPower(1);

      }
      if (gamepad1.a){
        sweeperMotor.setPower(0);

      }
      if(gamepad2.x)
      {
        wrist.setPosition(.55);
        claw.setPosition(.6);

    }
      if(gamepad2.b)
      {
        wrist.setPosition(0);
        claw.setPosition(.1);

      }
      if (gamepad2.a)
      {
        wrist.setPosition(0);
        claw.setPosition(.6);
      }
if (gamepad2.dpad_up)
{
  Dump.setPosition(0);
}
      if (gamepad2.dpad_down)
      {
        Dump.setPosition(1);
      }
      if (gamepad2.y)
      {
        Lift.setPosition(1);
      }
      if (gamepad2.right_bumper)
      {
        Lift.setPosition(.7);
      }





      motorRight.setPower(right);
      motorLeft.setPower(left);
      tiltMotor.setPower(power1*.3);
      moveMotor.setPower(power2*.1);
      grabMotor.setPower(tilt2*.8);


      // update the position of the claw


      // clip the position values so that they never exceed 0..1
      wristPosition = Range.clip(wristPosition, 0, 1);
      clawPosition = Range.clip(clawPosition, 0, 1);

      // write position values to the wrist and claw servo


    }

    // To read any values from the NXT controllers, we need to switch into READ_ONLY mode.
    // It takes time for the hardware to switch, so you can't switch modes within one loop of the
    // op mode. Every 17th loop, this op mode switches to READ_ONLY mode, and gets the current power.


    // Every 17 loops, switch to read mode so we can read data from the NXT device.
    // Only necessary on NXT devices.

  }

  // Update the current devMode


  // If the device is in either of these two modes, the op mode is allowed to write to the HW.

   

  // If the device is in either of these two modes, the op mode is allowed to write to the HW.
  private boolean allowedToWrite(){
    return (devMode == DcMotorController.DeviceMode.WRITE_ONLY);
  }
}
