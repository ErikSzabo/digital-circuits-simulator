package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.devices.build.SimpleDevice;

public class PowerSource extends SimpleDevice {

    public PowerSource() {
        super();
        getInputPin().setValue(true);
        getOutputPin().setValue(true);
    }

    public void destroyPaks() {
        getInputPin().setValue(false);
        calcOutput();
        sendOutput();
    }

    public void rebuildPaks() {
        getInputPin().setValue(true);
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
