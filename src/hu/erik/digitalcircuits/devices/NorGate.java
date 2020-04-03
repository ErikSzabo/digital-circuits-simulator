package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.devices.build.Gate;
import hu.erik.digitalcircuits.devices.build.Pin;

public class NorGate extends Gate {

    public NorGate(int numOfInputPins) {
        super(numOfInputPins);
    }

    @Override
    public void calcOutput() {
        for(Pin inputPin: getInputPins()) {
            if(inputPin.getValue()) {
                getOutputPin().setValue(false);
                return;
            }
        }
        getOutputPin().setValue(true);
    }

    @Override
    public String toString() {
        return "NorGate";
    }
}
