package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.devices.build.Device;
import hu.erik.digitalcircuits.devices.build.MultipinDevice;
import hu.erik.digitalcircuits.devices.build.Pin;
import hu.erik.digitalcircuits.errors.BoundException;
import hu.erik.digitalcircuits.errors.PinAlreadyInUseException;
import hu.erik.digitalcircuits.errors.PinNotExists;


public class CircuitBox extends MultipinDevice {
    private String name;

    public CircuitBox(String name, int numOfInputPins, int numOfOutputPins) {
        super(numOfInputPins, numOfOutputPins);
        this.name = name;
    }

    @Override
    public void calcOutput() {
        // This will never run
    }


    public void bindInputPin(Device d, int pinIndex, int boxPinIndex) {
        try {
            Pin bindPin = d.getInputPin(pinIndex);
            Pin boxPin = getInputPin(boxPinIndex);
            if(!boxPin.getParentDevice().toString().equals("CircuitBox")) throw new BoundException(this);
            getInputPins()[boxPinIndex] = bindPin;
        } catch (PinAlreadyInUseException | PinNotExists | BoundException err) {
            System.err.println(err.getMessage());
        }
    }


    public void bindOutputPin(Device d, int pinIndex, int boxPinIndex) {
        try {
            Pin bindPin = d.getOutputPin(pinIndex);
            Pin boxPin = getOutputPin(boxPinIndex);
            if(!boxPin.getParentDevice().toString().equals("CircuitBox")) throw new BoundException(this);
            getOutputPins()[boxPinIndex] = bindPin;
        } catch (PinAlreadyInUseException | PinNotExists | BoundException err) {
            System.err.println(err.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CircuitBox";
    }
}
