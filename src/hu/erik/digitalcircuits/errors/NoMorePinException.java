package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.Device;

public class NoMorePinException extends DeviceException {
    private String pinType;

    public NoMorePinException(Device device, String pinType) {
        super(device);
        this.pinType = pinType;
    }

    @Override
    public String getMessage() {
        return getDevice() + " doesn't have more " + pinType + "pin.";
    }
}
