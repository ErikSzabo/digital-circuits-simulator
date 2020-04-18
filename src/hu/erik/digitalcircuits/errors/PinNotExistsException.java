package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.Device;

public class PinNotExistsException extends Exception {
    private Device device;
    private int pinIndex;

    public PinNotExistsException(Device device, int pinIndex) {
        this.device = device;
        this.pinIndex = pinIndex;
    }

    @Override
    public String getMessage() {
        return "At the given index: " + pinIndex + ", " + device + " does not have a pin!";
    }
}
