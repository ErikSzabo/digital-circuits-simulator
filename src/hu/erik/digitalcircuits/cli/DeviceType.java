package hu.erik.digitalcircuits.cli;

/**
 * Stores unified device types. The whole Cli will use this.
 */
public enum DeviceType {

    /**
     * Represents a Switch type.
     */
    SWITCH("switch"),
    /**
     * Represents a PowerSource type.
     */
    POWER("powersource"),
    /**
     * Represents an Inverter type.
     */
    INVERTER("inverter"),
    /**
     * Represents a Junction type.
     */
    JUNCTION("junction"),
    /**
     * Represents a CircuitBox type.
     */
    CIRCUITBOX("circuitbox"),
    /**
     * Represents a NandGate type.
     */
    NANDGATE("nandgate"),
    /**
     * Represents a NorGate type.
     */
    NORGATE("norgate"),
    /**
     * Represents an AndGate type.
     */
    ANDGATE("andgate"),
    /**
     * Represents an OrGate type.
     */
    ORGATE("orgate");

    /**
     * String value of the enum. Will be useful in commands, and in the whole cli.
     */
    private String value;

    /**
     * Constructor to initialize the value of the enums.
     *
     * @param value string value of the enum
     */
    DeviceType(String value) {
        this.value = value;
    }

    /**
     * @return string value of the enum
     */
    public String getValue() {
        return value;
    }

    /**
     * Checks if any of the enums has the same string value.
     *
     * @param type string value to check
     * @return     true if one of the enums' value is the same as the parameter
     */
    public static boolean contains(String type) {
        for(DeviceType type1: values()) {
            if(type1.value.equals(type)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }
}
