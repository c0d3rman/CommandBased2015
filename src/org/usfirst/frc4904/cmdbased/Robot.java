/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc4904.cmdbased;


import org.usfirst.frc4904.cmdbased.commands.AutonomousIdle;
import org.usfirst.frc4904.cmdbased.commands.CommandBase;
import org.usfirst.frc4904.cmdbased.commands.XboxDrive;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private Command autonomousCommand;
	private SendableChooser autoChooser;
	private SendableChooser operatorChooser;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		// Initialize all subsystems
		CommandBase.init();
		// Initialize and configure autonomous command chooser
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Idle", new AutonomousIdle());
		operatorChooser = new SendableChooser();
		operatorChooser.addDefault("Nachi", new OINachi());
		operatorChooser.addObject("Griffin", new OIGriffin());
		// Display autonomous chooser on SmartDashboard
		SmartDashboard.putData("Autonomous mode chooser", autoChooser);
		SmartDashboard.putData("Operator control scheme chooser", operatorChooser);
	}
	
	public void autonomousInit() {
		// Get chosen autonomous command
		autonomousCommand = (Command) autoChooser.getSelected();
		// Schedule the autonomous command
		autonomousCommand.start();
	}
	
	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		autonomousCommand.cancel();
		Scheduler.getInstance().add(new XboxDrive());
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().add(new XboxDrive());
		Scheduler.getInstance().run();
	}
	
	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
