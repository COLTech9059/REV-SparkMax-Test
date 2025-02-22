// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Commands.SparkMaxCommands;
import frc.robot.Subsystems.BasicSparkMax;

public class RobotContainer {

  private BasicSparkMax spark = new BasicSparkMax(2, MotorType.kBrushless);
  private CommandXboxController controller = new CommandXboxController(0);

  public RobotContainer() {
    configureBindings();

    // Put speed variable onto the dashboard
    SmartDashboard.putNumber("Spark Speed", 0);

    // Put time variable onto the dashboard
    SmartDashboard.putNumber("Run Time", 0);

    // Put commands on the dashboard
    SmartDashboard.putData("Set", SparkMaxCommands.set(spark, SmartDashboard.getNumber("Spark Speed", 0)));

    SmartDashboard.putData("Sensor", SparkMaxCommands.runToSensor(spark, SmartDashboard.getNumber("Spark Speed", 0)));

    SmartDashboard.putData("Time", SparkMaxCommands.timedRun(spark, SmartDashboard.getNumber("Spark Speed", 0), 2));
  }

  private void configureBindings() {
    
    // PRESS RIGHT BUMPER --> Increment Run Time
    controller
        .rightBumper()
        .onTrue(
            Commands.runOnce(
                () ->
                    SmartDashboard.putNumber(
                        "Run Time",
                        (SmartDashboard.getNumber("Run Time", 0) + .25)
                    )
            )
        );

    // PRESS LEFT BUMPER --> Decrement Run Time
    controller
      .leftBumper()
      .onTrue(
          Commands.runOnce(
              () ->
                  SmartDashboard.putNumber(
                      "Run Time",
                      (SmartDashboard.getNumber("Run Time", 0) - .25)
                  )
          )
      );

    // PRESS B BUTTON --> Increment Spark Speed
    controller
        .b()
        .onTrue(
            Commands.runOnce(
                () ->
                    SmartDashboard.putNumber(
                        "Spark Speed",
                        (SmartDashboard.getNumber("Spark Speed", 0) + .1) > 1
                            ? 0
                            : (SmartDashboard.getNumber("Spark Speed", 0) + .1)
                    )
            )
        );

    // PRESS X BUTTON --> Decrement Spark Speed
    controller
    .x()
    .onTrue(
        Commands.runOnce(
            () ->
                SmartDashboard.putNumber(
                    "Spark Speed",
                    (SmartDashboard.getNumber("Spark Speed", 0) - .1) < -1
                        ? 1
                        : (SmartDashboard.getNumber("Spark Speed", 0) - .1)
                )
        )
    );

    // PRESS A BUTTON --> Stop motor
    controller.a().onTrue(SparkMaxCommands.stop(spark));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
