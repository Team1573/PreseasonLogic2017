
package org.usfirst.frc.team1573.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    // Variables go here
	Compressor comp = new Compressor();
	DoubleSolenoid lifter = new DoubleSolenoid(0,1);
	Solenoid gripper = new Solenoid(2);
	RobotDrive drive = new RobotDrive(0,1);
	Joystick joy = new Joystick(0);
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	//@SuppressWarnings("unused")
		//TcpConnection tcp = new TcpConnection();
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        // Driving control
    	drive.arcadeDrive(joy);
        
        // Lifter control
        if (joy.getRawButton(11)) {
        	lifter.set(Value.kForward);
        } else if (joy.getRawButton(10)) {
        	lifter.set(Value.kReverse);
        }
        
        // Gripper control
        if (joy.getRawButton(2)) {
        	gripper.set(true);
        } else {
        	gripper.set(false);
        }
    }
    
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
