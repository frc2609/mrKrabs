package org.usfirst.frc.team2609.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Encoder;
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

	public static Encoder leftEnc;
	public static Encoder rightEnc;
	public static CANTalon leftSteer;
	public static CANTalon rightSteer;
	public static CANTalon rearSteer;
	public static Victor left;
	public static Victor right;
	public static Victor rear;
	public static void init(){
		leftEnc = new Encoder(0,2);
		rightEnc = new Encoder(1,3);
		leftSteer = new CANTalon(1);
		leftSteer.changeControlMode(TalonControlMode.PercentVbus);
		rightSteer = new CANTalon(3);
		rightSteer.changeControlMode(TalonControlMode.PercentVbus);
		rearSteer = new CANTalon(2);
		rearSteer.changeControlMode(TalonControlMode.PercentVbus);
		left = new Victor(1);
		right = new Victor(0);
		rear = new Victor(2);
		System.out.println("INIT");
	}
	
	
}
