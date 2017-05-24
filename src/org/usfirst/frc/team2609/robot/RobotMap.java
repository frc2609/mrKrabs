package org.usfirst.frc.team2609.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	public static AnalogInput driveEncoderLeftFrontAbsolute;
	public static AnalogInput frontRight;
	public static AnalogInput rearRight;
	public static CANTalon driveTalonLeftFrontSteer;
	public static CANTalon driveTalonRightFront;
	public static CANTalon driveTalonRear;
	public static Victor driveTalonLeftFrontDrive;
	
	public static Encoder driveEncoderLeftFront;
	

	public static void init() {
		driveEncoderLeftFrontAbsolute = new AnalogInput(1);
		frontRight = new AnalogInput(0);
		rearRight = new AnalogInput(2);
		driveTalonLeftFrontSteer = new CANTalon(1);
		driveTalonRightFront = new CANTalon(3);
		driveTalonRear = new CANTalon(2);
		
		driveTalonLeftFrontDrive = new Victor(1);
		
		driveTalonLeftFrontSteer.changeControlMode(TalonControlMode.PercentVbus);
		
		driveEncoderLeftFront = new Encoder(0,2);
		
		driveEncoderLeftFront.setDistancePerPulse(0.8);
	}
	
	
	
	
}
