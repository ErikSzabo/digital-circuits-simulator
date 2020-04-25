package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.devices.Device;

public class DeviceBundle {
    private Device device;
    private String type;

    public DeviceBundle(Device device, String type) {
        this.device = device;
        this.type = type;
    }

    public Device getDevice() {
        return device;
    }

    public String getType() {
        return type;
    }
}
