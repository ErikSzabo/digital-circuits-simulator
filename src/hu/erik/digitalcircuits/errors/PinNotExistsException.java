package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.Device;

/**
 * Device exception that handles errors which caused by accessing non-existent pins.
 */
public class PinNotExistsException extends DeviceException {
    /**
     * Index of the pin that is not exists.
     */
    private int pinIndex;

    /**
     * Constructor to initialize the problematic device, and the non-existing pin index.
     *
     * @param device    device on which the problem occurs
     * @param pinIndex  the non-existing pin index
     */
    public PinNotExistsException(Device device, int pinIndex) {
        super(device);
        this.pinIndex = pinIndex;
    }

    /**
     * Returns an error specific message which tells that at the given index there isn't any pin.
     *
     * @return error message
     */
    @Override
    public String getMessage() {
        return "At the given index: " + pinIndex + ", " + getDevice() + " does not have a pin!";
    }
}
