package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.Device;

public abstract class DeviceException extends Exception {
    private Device device;

    public DeviceException(Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    @Override
    public abstract String getMessage();
}
