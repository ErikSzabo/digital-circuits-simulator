package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.Device;

public class NoMorePinException extends Exception {
    private Device device;
    private String pinType;

    public NoMorePinException(Device device, String pinType) {
        this.device = device;
        this.pinType = pinType;
    }

    @Override
    public String getMessage() {
        return device + "doesn't have more " + pinType + "pin.";
    }
}
