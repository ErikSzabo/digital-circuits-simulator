package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.Device;

public class PinAlreadyInUseException extends DeviceException {
    private int pinIndex;

    public PinAlreadyInUseException(Device device, int pinIndex) {
        super(device);
        this.pinIndex = pinIndex;
    }

    @Override
    public String getMessage() {
        return "On " + getDevice() + ", at the given index: " + pinIndex + ", the pin is already in use!";
    }
}
