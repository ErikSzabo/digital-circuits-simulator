package hu.erik.digitalcircuits.cli;

/**
 * Stores unified device types.
 */
public enum DeviceType {

    SWITCH("switch"),
    POWER("powersource"),
    INVERTER("inverter"),
    JUNCTION("junction"),
    CIRCUITBOX("circuitbox"),
    NANDGATE("nandgate"),
    NORGATE("norgate"),
    ANDGATE("andgate"),
    ORGATE("orgate");

    private String value;

    DeviceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

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
