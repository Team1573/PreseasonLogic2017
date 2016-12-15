package org.usfirst.frc.team1573.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class MyClass {
	
	DoubleSolenoid mysolenoid = new DoubleSolenoid(0, 1);
	CANTalon mymotor = new CANTalon(3);
	
	public float divide (float a, float b){
		return a/b ; 
	}
	
	//public void setsolonoide (DoubleSolenoid.Value value) {
	//	mysolenoid.set(value);
	//}
	public void setmotor (double outputValue) {
		mymotor.set(outputValue);
	}
	
	public void openSolenoid () {
		mysolenoid.set(Value.kForward);
	}   
	
	public void closeSolenoid () {
		mysolenoid.set(Value.kReverse);
	}
	
}
