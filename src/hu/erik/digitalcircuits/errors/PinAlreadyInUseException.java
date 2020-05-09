package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.Device;

/**
 * Device exception that handles errors which caused by connection methods.
 * Thrown when a connection method tries to connect a pin that is already connected
 * to something else.
 */
public class PinAlreadyInUseException extends DeviceException {
    /**
     * Index of the pin that is already in use on the device.
     */
    private int pinIndex;

    /**
     * Constructor to initialize the problematic device, and the pin index
     * which is already in use.
     *
     * @param device    device on which the problem occurs
     * @param pinIndex  index of the pin that is already in use
     */
    public PinAlreadyInUseException(Device device, int pinIndex) {
        super(device);
        this.pinIndex = pinIndex;
    }

    /**
     * Returns an error specific message about the pin that is already in use.
     *
     * @return error message
     */
    @Override
    public String getMessage() {
        return "On " + getDevice() + ", at the given index: " + pinIndex + ", the pin is already in use!";
    }
}
