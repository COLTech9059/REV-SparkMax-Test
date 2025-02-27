// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Commands.SparkMaxCommands;
import frc.robot.Subsystems.BasicSparkMax;

public class RobotContainer {

  private BasicSparkMax spark = new BasicSparkMax(17, MotorType.kBrushless);
  private CommandXboxController controller = new CommandXboxController(0);
  private double runTime = 0;
  private double runSpeed = 0;

//   private XboxController controller2 = new XboxController(0);

  public RobotContainer() {
    spark.configure(true, true, true, 0.2, 0.2);

    configureBindings();

    // Put subsystem on the dashboard
    SmartDashboard.putData(spark);

    // Put speed variable onto the dashboard
    SmartDashboard.putNumber("Spark Speed", runSpeed);

    // Put time variable onto the dashboard
    SmartDashboard.putNumber("Run Time", runTime);

    // Put commands on the dashboard
    SmartDashboard.putData("Set", SparkMaxCommands.set(spark, runSpeed));

    SmartDashboard.putData("Sensor", SparkMaxCommands.runToSensor(spark, runSpeed));

    SmartDashboard.putData("Time", SparkMaxCommands.timedRun(spark, runSpeed, runTime));
  }

  public BasicSparkMax getSpark() {
    return spark;
  }

  private void configureBindings() {
    
    // if (controller2.getRightTriggerAxis() > 0.2) spark.motor.set(0.25);

    // PRESS RIGHT BUMPER --> Increment Run Time
    controller
        .rightBumper()
        .onTrue(
            Commands.runOnce(
                () -> runTime += 0.25
            )
        );

    // PRESS LEFT BUMPER --> Decrement Run Time
    controller
      .leftBumper()
      .onTrue(
          Commands.runOnce(
              () -> runTime -= 0.25
          )
      );

    // PRESS B BUTTON --> Increment Spark Speed
    controller
        .b()
        .onTrue(
            Commands.runOnce(
                () -> runSpeed += ((runSpeed + 0.1) > 1) ? -runSpeed : 0.1
            )
        );

    // PRESS X BUTTON --> Decrement Spark Speed
    controller
    .x()
    .onTrue(
        Commands.runOnce(
            () -> runSpeed -= ((runSpeed - 0.1) < 1) ? -runSpeed : 0.1
        )
    );

    // PRESS A BUTTON --> Stop motor
    controller.a().onTrue(SparkMaxCommands.stop(spark));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
