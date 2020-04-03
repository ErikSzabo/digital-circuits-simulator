package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.build.Device;

public class PinAlreadyInUseException extends Exception {
    private Device device;
    private int pinIndex;

    public PinAlreadyInUseException(Device device, int pinIndex) {
        this.device = device;
        this.pinIndex = pinIndex;
    }

    @Override
    public String getMessage() {
        return "On " + device + ", at the given index: " + pinIndex + ", the pin is already in use!";
    }
}
