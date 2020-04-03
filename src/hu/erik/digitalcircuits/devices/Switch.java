package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.devices.build.SimpleDevice;

public class Switch extends SimpleDevice {
    private boolean status;

    public Switch() {
        this.status = false;
    }

    public void on() {
        this.status = true;
        calcOutput();
        sendOutput();
    }

    public void  off() {
        this.status = false;
        calcOutput();
        sendOutput();
    }

    @Override
    public void calcOutput() {
        getOutputPin().setValue(getInputPin().getValue() && status);
    }

    @Override
    public String toString() {
        return "Switch";
    }
}
