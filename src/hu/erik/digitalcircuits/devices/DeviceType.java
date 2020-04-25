package hu.erik.digitalcircuits.devices;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Stores unified device types.
 */
public final class DeviceType {

    /**
     * Private default constructor to prevent instance creation.
     */
    private DeviceType() {}

    public static final String SWITCH = "switch";
    public static final String POWER = "powersource";
    public static final String INVERTER = "inverter";
    public static final String JUNCTION = "junction";
    public static final String CIRCUITBOX = "circuitbox";
    public static final String NANDGATE = "nandgate";
    public static final String NORGATE = "norgate";
    public static final String ANDGATE = "andgate";
    public static final String ORGATE = "orgate";
    public static final ArrayList<String> ALL = new ArrayList<>(
            Arrays.asList(SWITCH, POWER, INVERTER, JUNCTION, CIRCUITBOX, NANDGATE, NORGATE, ANDGATE, ORGATE)
    );
}
