
package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2609.robot.commands.ExampleCommand;
import org.usfirst.frc.team2609.robot.subsystems.ExampleSubsystem;

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

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	public static double wheelAngle = 0;
	public static double wheelAngleLoop = 0;
	public static double controlAngle = 0;
	public static double phi = 0;
	public static boolean flipDirection;
	public static boolean driveBackwards;
	public static double controlSpeed;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		RobotMap.init();
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
		SmartDashboard.putNumber("FrontRight", RobotMap.frontRight.getVoltage());
		SmartDashboard.putNumber("FrontLeft", RobotMap.driveEncoderLeftFrontAbsolute.getVoltage());
		SmartDashboard.putNumber("RearRight", RobotMap.rearRight.getVoltage());
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
		RobotMap.driveEncoderLeftFront.reset();
		
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		wheelAngle = (RobotMap.driveEncoderLeftFrontAbsolute.getVoltage()/4.75)*360;
		wheelAngleLoop = wheelAngle - (Math.floor(wheelAngle/360)*360);
		if (OI.driverStick.getMagnitude()<.2){		//deadband
			controlAngle = 0;
		}
		else{
			if (driveBackwards == false){			//drive backwards toggle
				controlAngle = OI.driverStick.getDirectionDegrees();
			}
			else{
				if (controlAngle < 180){			//checking controller angle to make sure it doesnt go negative
					controlAngle = OI.driverStick.getDirectionDegrees() + 180;
				}
				else{
					controlAngle = OI.driverStick.getDirectionDegrees() - 180;
				}
			}
		}
		
		if (OI.driverStick.getMagnitude()<.2){		//deadband
			controlSpeed = 0;
		}
		else{
			if (driveBackwards == false){			//drive backwards toggle
				controlSpeed = OI.driverStick.getMagnitude();
			}
			else{
				controlSpeed = -OI.driverStick.getMagnitude();
			}
		}
		
		if (wheelAngleLoop>270){					//0 to 360 flip angle, phi is magic math dont worry about it
			phi = 360 - wheelAngleLoop;
		}
		else if (wheelAngleLoop<90){
			phi = wheelAngleLoop;
		}
		
		if (controlAngle<0){						//controller 0 to 360 degrees converter, might not work on all controllers
			controlAngle=controlAngle+360;
		}
		
		if (wheelAngleLoop<90 && controlAngle>(360-(90-phi))){		//0 to 360 flip, so the wheels dont do a 360 turn at 0 degrees
			controlAngle = controlAngle - 360;
		}
		else if (wheelAngleLoop>270 && controlAngle<(90-phi)){
			wheelAngleLoop = wheelAngleLoop-360;
		}
		
		if ((wheelAngleLoop - controlAngle) > 90){	//less than 90 degree turn path to target
			if (controlAngle < 180){
				controlAngle = controlAngle + 180;
			}
			else{
				controlAngle = controlAngle - 180;
			}
			flipDirection = true;
		}
		
		if (flipDirection == true){					//flip direction toggle latch, for 90 degree max turn path
			if (driveBackwards == true){
				driveBackwards = false;
			}
			else{
				driveBackwards = true;
			}
			flipDirection = false;
		}
		
		
		RobotMap.driveTalonLeftFrontSteer.set(-0.02*(wheelAngleLoop - controlAngle));	//P steering control
		RobotMap.driveTalonLeftFrontDrive.set(controlSpeed);
		
		SmartDashboard.putNumber("FrontRight", RobotMap.frontRight.getVoltage());
		SmartDashboard.putNumber("FrontLeft", RobotMap.driveEncoderLeftFrontAbsolute.getVoltage());
		SmartDashboard.putNumber("RearRight", RobotMap.rearRight.getVoltage());
		SmartDashboard.putNumber("controller angle", controlAngle);
		SmartDashboard.putNumber("wheel angle", wheelAngle);
		SmartDashboard.putNumber("wheel angle loop", wheelAngleLoop);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
