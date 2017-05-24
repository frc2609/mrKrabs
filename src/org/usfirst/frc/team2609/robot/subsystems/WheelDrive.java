package org.usfirst.frc.team2609.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;

public class WheelDrive {
	private PIDController pidController;
	private CANTalon steerMotor;
	private Victor driveMotor;
	private AnalogInput analog;
	private double MAX_VOLTS = 4.75;
	private double OFFSET = 0;
	
	public WheelDrive(CANTalon steerMotor, Victor driveMotor, int encoder){
		this.steerMotor = steerMotor;
		this.driveMotor = driveMotor;
		this.analog = new AnalogInput(encoder);
		pidController = new PIDController(1,0,0, this.analog, steerMotor);
		
		pidController.setOutputRange(-1, 1);
		pidController.setContinuous();
		pidController.enable();
		
	}
	public void setMaxVolt(double maxvolt){
		this.MAX_VOLTS = maxvolt;
	}
	public void drive(double speed, double angle){
		driveMotor.set(speed);
		double setpoint = (angle)*(MAX_VOLTS*0.5) + (MAX_VOLTS*0.5);
		if(setpoint < 0){
			setpoint = MAX_VOLTS+setpoint;
		}
		if(setpoint > MAX_VOLTS){
			setpoint = setpoint-MAX_VOLTS;
		}
		
		pidController.setSetpoint(angleWithOffset(setpoint));
	}
	public double angleWithOffset(double angle){
		double predictedAngle = angle+OFFSET;
		if(predictedAngle>MAX_VOLTS){
			predictedAngle = predictedAngle-MAX_VOLTS;
		}
		return predictedAngle;
	}
	public void configureOffset(double offset){
		this.OFFSET = offset;
	}
	public double getAnalogVolt(){
		return analog.pidGet();
	}
}
