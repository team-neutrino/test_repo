/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//Pathfinder test code 

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.SPI;

import java.io.IOException;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
   
  private static final int k_ticks_per_rev = 360;
  private static final double k_wheel_diameter = 6;
  private static final double k_max_velocity = 13;
  
  private static final int left_motor_one = 0;
  private static final int left_motor_two = 1;
  private static final int right_motor_one = 2;
  private static final int right_motor_two = 3;

  private static final int k_left_encoder_port_a = 2;
  private static final int k_left_encoder_port_b = 3;
  private static final int k_right_encoder_port_a = 4;
  private static final int k_right_encoder_port_b = 5;

  public static final SPI.Port NAVX_PORT = SPI.Port.kMXP;

  private static final String k_path_name = "Testpath";

  public Drive drive;
  public static Joystick lJoy;
  public static Joystick rJoy;

  private TalonSRX lMotor1;
  private TalonSRX lMotor2;
  private TalonSRX rMotor1;
  private TalonSRX rMotor2;

  //private TalonSRX m_right_motor;

  private Encoder m_left_encoder;
  private Encoder m_right_encoder;

  private AHRS navx;

  private EncoderFollower m_left_follower;
  private EncoderFollower m_right_follower;
  
  private Notifier m_follower_notifier;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    drive = new Drive();
    lJoy = new Joystick(0);
    rJoy = new Joystick(1);
    //lMotor1 = new TalonSRX(left_motor_one);
    //lMotor2 = new TalonSRX(left_motor_two);
    //rMotor1 = new TalonSRX(right_motor_one);
    //rMotor2 = new TalonSRX(right_motor_two);

    //rMotor1.setInverted(true);
    //rMotor2.setInverted(true);

    //lMotor1.setInverted(false);
    //lMotor2.setInverted(false);

    m_left_encoder = new Encoder(k_left_encoder_port_a, k_left_encoder_port_b);
    m_right_encoder = new Encoder(k_right_encoder_port_a, k_right_encoder_port_b);
    navx = new AHRS(NAVX_PORT);
  

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  
    try {
      Trajectory left_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".right");
      Trajectory right_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".left");

      m_left_follower = new EncoderFollower(left_trajectory);
      m_right_follower = new EncoderFollower(right_trajectory);

      m_left_follower.configureEncoder(m_left_encoder.get(), k_ticks_per_rev, k_wheel_diameter);
      // You must tune the PID values on the following line!
      m_left_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / k_max_velocity, 0);

      m_right_follower.configureEncoder(m_right_encoder.get(), k_ticks_per_rev, k_wheel_diameter);
      // You must tune the PID values on the following line!
      m_right_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / k_max_velocity, 0);
      
      m_follower_notifier = new Notifier(this::followPath);
      m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
	}

  private void followPath() {
    if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
      m_follower_notifier.stop();
    } else {
      double left_speed = m_left_follower.calculate(m_left_encoder.get());
      double right_speed = m_right_follower.calculate(m_right_encoder.get());
      System.out.println(m_left_follower.getSegment());
      // double heading = navx.getAngle();
      // double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
      // double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
      // double turn =  0.8 * (-1.0/80.0) * heading_difference;
      double turn = 0;
      //lMotor1.set(ControlMode.PercentOutput, left_speed + turn);
     //lMotor2.set(ControlMode.PercentOutput, left_speed + turn);
     // rMotor1.set(ControlMode.PercentOutput, right_speed - turn);
     // rMotor2.set(ControlMode.PercentOutput, right_speed - turn);

    }
  }
  

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  @Override
  public void teleopInit() {
    //m_follower_notifier.stop();
    //lMotor1.set(ControlMode.PercentOutput, 0);
    //lMotor2.set(ControlMode.PercentOutput, 0);
    //rMotor1.set(ControlMode.PercentOutput, 0);
    //rMotor2.set(ControlMode.PercentOutput, 0);
    drive.setLeft(0);
    drive.setRight(0);
  }


  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    drive.setLeft(lJoy.getY());
    drive.setRight(rJoy.getY());
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
