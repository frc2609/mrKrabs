
package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2609.robot.commands.ExampleCommand;
import org.usfirst.frc.team2609.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team2609.robot.subsystems.SwerveDrive;
import org.usfirst.frc.team2609.robot.subsystems.WheelDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	private WheelDrive rearRight;
	private WheelDrive rearLeft;
	private WheelDrive frontLeft;
	private WheelDrive frontRight;
	private SwerveDrive swerveDrive;
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		RobotMap.init();
		// wheels facing front: front right & left: 3.88 & 2.9
		rearRight = new WheelDrive(RobotMap.rearSteer, RobotMap.rear, 2);
		rearLeft = new WheelDrive(RobotMap.rearSteer, RobotMap.rear, 3);
		frontRight = new WheelDrive(RobotMap.rightSteer, RobotMap.right, 0);
		frontRight.configureOffset(2.9);
		frontLeft = new WheelDrive(RobotMap.leftSteer, RobotMap.left, 1);
		frontLeft.configureOffset(3.88);
		swerveDrive = new SwerveDrive(rearRight,rearLeft,frontRight,frontLeft);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("leftEnc.get()", RobotMap.leftEnc.get());
		SmartDashboard.putNumber("rightEnc.get()", RobotMap.rightEnc.get());
		SmartDashboard.putNumber("leftEnc.getDistance()", RobotMap.leftEnc.getDistance());
		SmartDashboard.putNumber("rightEnc.getDistance()", RobotMap.rightEnc.getDistance());



		SmartDashboard.putNumber("Rear right enc voltage", rearRight.getAnalogVolt());
		SmartDashboard.putNumber("Rear left enc voltage", rearLeft.getAnalogVolt());
		SmartDashboard.putNumber("Front right enc voltage", frontRight.getAnalogVolt());
		SmartDashboard.putNumber("Front left enc voltage", frontLeft.getAnalogVolt());
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		SmartDashboard.putNumber("Rear right enc voltage", rearRight.getAnalogVolt());
		SmartDashboard.putNumber("Rear left enc voltage", rearLeft.getAnalogVolt());
		SmartDashboard.putNumber("Front right enc voltage", frontRight.getAnalogVolt());
		SmartDashboard.putNumber("Front left enc voltage", frontLeft.getAnalogVolt());
		
//		RobotMap.left.set(0.25);
//		RobotMap.right.set(0.25);
//		RobotMap.rear.set(0.25);
//		RobotMap.leftSteer.set(0.25);
//		RobotMap.rightSteer.set(0.25);
//		RobotMap.rearSteer.set(0.25);
//		
		swerveDrive.drive(OI.joystick.getRawAxis(1), OI.joystick.getRawAxis(0), OI.joystick.getRawAxis(4));
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
