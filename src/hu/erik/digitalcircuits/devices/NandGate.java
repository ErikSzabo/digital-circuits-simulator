package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.devices.build.Gate;
import hu.erik.digitalcircuits.devices.build.Pin;

public class NandGate extends Gate {

    public NandGate(int numOfInputPins) {
        super(numOfInputPins);
    }

    @Override
    public void calcOutput() {
        for(Pin inputPin : getInputPins()) {
            if(!inputPin.getValue()) {
                getOutputPin().setValue(true);
                return;
            }
        }
        getOutputPin().setValue(false);
    }

    @Override
    public String toString() {
        return "NandGate";
    }
}
