package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.devices.build.SimpleDevice;

public class PowerSource extends SimpleDevice {

    public PowerSource() {
        super();
    }

    public void on() {
        getInputPin().setValue(true);
        calcOutput();
        sendOutput();
    }

    public void off() {
        getInputPin().setValue(false);
        calcOutput();
        sendOutput();
    }

    @Override
    public void calcOutput() {
        getOutputPin().setValue(getInputPin().getValue());
    }

    @Override
    public String toString() {
        return "PowerSource";
    }
}
