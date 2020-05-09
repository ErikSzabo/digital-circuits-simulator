package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.Device;

/**
 * Device exception that handles errors which caused by connect methods.
 * Some connect methods throw this error when there isn't any more free pin on the device.
 */
public class NoMorePinException extends DeviceException {
    /**
     * Type of the pin that is "out of stock".
     * Should be "input" or "output"
     */
    private String pinType;

    /**
     * Constructor to initialize the device that has some pin problems and the type of the pin.
     *
     * @param device    device which doesn't have more free pins
     * @param pinType   type of the pin
     */
    public NoMorePinException(Device device, String pinType) {
        super(device);
        this.pinType = pinType;
    }

    /**
     * Returns an error specific message which tells that device doesn't have more pins of the required type.
     *
     * @return error message
     */
    @Override
    public String getMessage() {
        return getDevice() + " doesn't have more " + pinType + "pin.";
    }
}
