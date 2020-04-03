package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.devices.build.SimpleDevice;

public class Inverter extends SimpleDevice {

    @Override
    public void calcOutput() {
        getOutputPin().setValue(!getInputPin().getValue());
    }

    @Override
    public String toString() {
        return "Inverter";
    }
}
