package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.Device;

public class PinNotExistsException extends DeviceException {
    private int pinIndex;

    public PinNotExistsException(Device device, int pinIndex) {
        super(device);
        this.pinIndex = pinIndex;
    }

    @Override
    public String getMessage() {
        return "At the given index: " + pinIndex + ", " + getDevice() + " does not have a pin!";
    }
}
