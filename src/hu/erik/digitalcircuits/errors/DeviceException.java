package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.Device;

/**
 * Default exception to handle Device errors.
 */
public abstract class DeviceException extends Exception {
    private Device device;

    /**
     * Constructor to save the given device which had some problem.
     *
     * @param device the device with which the problem occurred
     */
    public DeviceException(Device device) {
        this.device = device;
    }

    /**
     * @return the device with which the problem occurred
     */
    public Device getDevice() {
        return device;
    }

    /**
     * Returns the error message. Defined as abstract, so it's
     * children must implement this method.
     *
     * @return error message
     */
    @Override
    public abstract String getMessage();
}
