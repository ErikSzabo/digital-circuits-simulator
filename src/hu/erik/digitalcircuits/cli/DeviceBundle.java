package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.devices.Device;

/**
 * Class to store devices along with their types.
 */
public class DeviceBundle {
    /**
     * User created device.
     */
    private Device device;
    /**
     * Type of the user created device.
     */
    private DeviceType type;

    /**
     * Constructor to initialize user created device and it's type.
     *
     * @param device    stored device
     * @param type      type of the stored device
     */
    public DeviceBundle(Device device, DeviceType type) {
        this.device = device;
        this.type = type;
    }

    /**
     * Returns the user created device.
     *
     * @return the device what is stored
     */
    public Device getDevice() {
        return device;
    }


    /**
     * Returns the type of the user created device.
     *
     * @return type of the device
     */
    public DeviceType getType() {
        return type;
    }
}
