package frc.robot.Subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

/**
 * Very basic class used for testing basic functionality of a SparkMax motor Controller
 */
public class BasicSparkMax extends SubsystemBase {

    private SparkMax motor;
    private DigitalInput switch1 = new DigitalInput(0);

    /**
     * Constructs a BasicSparkMax object with the given id and motorType. This class uses only one motor, so additional instances of this class must be created to support more
     * @param id The CAN id of the motor controller
     * @param motorType The type of motor attached to the motor controller, either MotorType.kBrushed or MotorType.kBrushless
     */
    public BasicSparkMax(int id, MotorType motorType) {
        motor = new SparkMax(id, motorType);
    }

    /**
     * Configures the motor with the given values
     * @param inverted Whether the input values are inverted or not
     * @param resetSafe Whether the resetSafeParameters are active or not
     * @param persistParam Whether the persistParameters are active or not
     * @param closedLoopRampRate The time it takes (in seconds) to reach full speed in closed loop control
     * @param openLoopRampRate The time it takes (in seconds) to reach full speed in open loop control
     */
    public void configure(boolean inverted, boolean resetSafe, boolean persistParam, double closedLoopRampRate, double openLoopRampRate) {
        SparkMaxConfig config = new SparkMaxConfig();
        ResetMode reset = ResetMode.kResetSafeParameters;
        PersistMode persist = PersistMode.kPersistParameters;

        if (!resetSafe) reset = ResetMode.kNoResetSafeParameters;
        if (!persistParam) persist = PersistMode.kNoPersistParameters;

        config
        .inverted(inverted)
        .closedLoopRampRate(closedLoopRampRate)
        .openLoopRampRate(openLoopRampRate);

        motor.configure(config, reset, persist);
    }

    /**
     * Sets the speed of the motor
     * @param speed Target speed, expressed as a double from -1.00 - 1.00
     */
    public void set(double speed) {
        motor.set(speed);
    }

    /**
     * Stops the motor
     */
    public void stop() {
        motor.stopMotor();
    }

    /**
     * Sets the target voltage of the motor. This is a "set-and-forget" style method that only really needs to be called once
     * @param volts Target voltage as a double
     */
    public void setVoltage(double volts) {
        motor.setVoltage(volts);
    }

    /**
     * Sets the target voltage of the motor, which will be applied regardless of battery voltage. This function MUST be called repeatedly to function properly
     * @param voltage Target voltage as a Voltage object
     */
    public void setVoltage(Voltage voltage) {
        motor.setVoltage(voltage);
    }

    /**
     * Runs the motor until a digital input is triggered
     * @param speed Target speed, expressed as a double from -1.00 - 1.00
     */
    public void runToSensor(double speed) {
        if (switch1.get()) motor.stopMotor();
        else motor.set(speed);
    }
}
