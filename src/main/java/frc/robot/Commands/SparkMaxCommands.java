package frc.robot.Commands;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Subsystems.BasicSparkMax;

public class SparkMaxCommands {

    /**
     * Sets the motor controller to a target speed. This Command DOES NOT stop the motor at any point
     * @param spark The BasicSparkMax subsystem
     * @param speed The target speed, expressed as a double from -1.00 - 1.00
     * @return The Command object
     */
    public static Command set(BasicSparkMax spark, double speed) {
        return Commands.runOnce( () -> spark.set(speed), spark);
    }

    /**
     * Runs the motor at a target speed for a specified amount of time
     * @param spark The BasicSparkMax subsystem
     * @param speed The target speed, expressed as a double from -1.00 - 1.00
     * @param time The time to run the motor for, in seconds
     * @return The Command  object
     */
    public static Command timedRun(BasicSparkMax spark, double speed, double time) {
        return Commands.sequence(
            Commands.runOnce( () -> spark.set(speed), spark),
            Commands.waitSeconds(time),
            Commands.runOnce( () -> spark.stop(), spark)
        );
    }

    /**
     * Sets the target voltage of the motor controller
     * @param spark The BasicSparkMax subsystem
     * @param volts The target voltage
     * @return The Command object
     */
    public static Command setVoltage(BasicSparkMax spark, double volts) {
        return Commands.runOnce( () -> spark.setVoltage(volts), spark);
    }

    /**
     * Sets the target voltage of the motor controller
     * @param spark The BasicSparkMax subsystem
     * @param voltage The target voltage
     * @return The Command object
     */
    public static Command setVoltage(BasicSparkMax spark, Voltage voltage) {
        return Commands.run( () -> spark.setVoltage(voltage), spark);
    }

    /**
     * Runs the motor until a sensor is triggered
     * @param spark The BasicSparkMax subsystem
     * @param speed The target speed, expressed as a double from -1.00 to 1.00
     * @return The Command object
     */
    public static Command runToSensor(BasicSparkMax spark, double speed) {
        return Commands.runEnd( () -> spark.runToSensor(speed), () -> spark.stop(), spark);
    }

    /**
     * Stops the motor controller
     * @param spark The BasicSparkMax subsystem
     * @return The Command object
     */
    public static Command stop(BasicSparkMax spark) {
        return Commands.runOnce( () -> spark.stop());
    }
}
