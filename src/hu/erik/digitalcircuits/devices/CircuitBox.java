package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.devices.build.Device;
import hu.erik.digitalcircuits.devices.build.MultipinDevice;
import hu.erik.digitalcircuits.devices.build.Pin;
import hu.erik.digitalcircuits.errors.BoundException;
import hu.erik.digitalcircuits.errors.PinAlreadyInUseException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;
import hu.erik.digitalcircuits.utils.Printer;


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
            if(!bindPin.isFree()) throw new PinAlreadyInUseException(d, pinIndex);
            if(!boxPin.isFree()) throw new PinAlreadyInUseException(this, boxPinIndex);
            if(!boxPin.getParentDevice().toString().equals("CircuitBox")) throw new BoundException(this);
            getInputPins()[boxPinIndex] = bindPin;
        } catch (PinAlreadyInUseException | PinNotExistsException | BoundException err) {
            Printer.printErr(err);
        }
    }


    public void bindOutputPin(Device d, int pinIndex, int boxPinIndex) {
        try {
            Pin bindPin = d.getOutputPin(pinIndex);
            Pin boxPin = getOutputPin(boxPinIndex);
            if(!bindPin.isFree()) throw new PinAlreadyInUseException(d, pinIndex);
            if(!boxPin.isFree()) throw new PinAlreadyInUseException(this, boxPinIndex);
            if(!boxPin.getParentDevice().toString().equals("CircuitBox")) throw new BoundException(this);
            getOutputPins()[boxPinIndex] = bindPin;
        } catch (PinAlreadyInUseException | PinNotExistsException | BoundException err) {
            Printer.printErr(err);
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
