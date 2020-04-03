package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.build.Device;

public class PinNotExists extends Exception {
    private Device device;
    private int pinIndex;

    public PinNotExists(Device device, int pinIndex) {
        this.device = device;
        this.pinIndex = pinIndex;
    }

    @Override
    public String getMessage() {
        return "At the given index: " + pinIndex + ", " + device + " does not have a pin!";
    }
}
