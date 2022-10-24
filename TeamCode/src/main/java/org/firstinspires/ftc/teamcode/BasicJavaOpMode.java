package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Basic Java OpMode", group="Linear Opmode")
public class BasicJavaOpMode extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor frontLeft = null;
    private DcMotor frountRight = null;
    private DcMotor arm = null;

    private Servo armServo = null;

    private ElapsedTime runtime = new ElapsedTime();

    // Define the speeds for the motors
    static final double FORWARD_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;
    static final double ARM_SPEED = 0.2;
    static final double SERVO_SPEED = 0.2;



    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frountRight = hardwareMap.get(DcMotor.class, "front_right");
        arm = hardwareMap.get(DcMotor.class, "arm");
        armServo = hardwareMap.get(Servo.class, "arm_servo");


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frountRight.setDirection(DcMotor.Direction.REVERSE);
        arm.setDirection(DcMotor.Direction.FORWARD);
        armServo.setDirection(Servo.Direction.FORWARD);

        waitForStart();

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frountRight.setDirection(DcMotor.Direction.FORWARD);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        // Step 1:  Drive forward for 3 seconds
        arm.setPower(ARM_SPEED);
        armServo.setPosition(0.5);
        frontLeft.setPower(FORWARD_SPEED);
        frountRight.setPower(FORWARD_SPEED);
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 2:  Spin right for 1.3 seconds
        frontLeft.setPower(TURN_SPEED);
        frountRight.setPower(-TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.3)) {
            telemetry.addData("Path", "Leg 2: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 3:  Drive Backward for 1 Second
        frontLeft.setPower(FORWARD_SPEED);
        frountRight.setPower(FORWARD_SPEED);
        arm.setPower(ARM_SPEED);
        armServo.setPosition(-0.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addData("Path", "Leg 3: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 4:  Stop and close the claw.


        // Step 4:  Stop
        frontLeft.setPower(0);
        frountRight.setPower(0);
        arm.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}
