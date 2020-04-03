package hu.erik.digitalcircuits.devices.build;

import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;

public abstract class Gate extends ConnectableDevice {
    private Pin[] inputPins;
    private Pin outputPin;

    public Gate(int numOfInputPins) {
        this.outputPin = new Pin(this);
        this.inputPins = new Pin[numOfInputPins];
        for (int i = 0; i < inputPins.length; i++) inputPins[i] = new Pin(this);
    }

    @Override
    public Pin getFreeInputPin() throws NoMorePinException {
        for(Pin pin : inputPins) {
            if(pin.isFree()) return pin;
        }
        throw new NoMorePinException(this, "input");
    }

    @Override
    public Pin getFreeOutputPin() throws NoMorePinException {
        if(outputPin.isFree()) return outputPin;
        throw new NoMorePinException(this, "output");
    }

    @Override
    public Pin getInputPin(int index) throws PinNotExistsException {
        if(index >= inputPins.length) throw new PinNotExistsException(this, index);
        return inputPins[index];
    }

    @Override
    public Pin getOutputPin(int index) throws PinNotExistsException {
        if(index == 0) return outputPin;
        throw new PinNotExistsException(this, index);
    }

    @Override
    public void sendOutput() {
        transferValue(outputPin);
    }

    protected Pin[] getInputPins() {
        return inputPins;
    }

    protected Pin getOutputPin() {
        return outputPin;
    }
}
