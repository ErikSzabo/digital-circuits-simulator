package hu.erik.digitalcircuits.devices.build;

import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinAlreadyInUseException;
import hu.erik.digitalcircuits.errors.PinNotExists;

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
    public Pin getInputPin(int index) throws PinAlreadyInUseException, PinNotExists {
        if(index >= inputPins.length) throw new PinNotExists(this, index);
        if(inputPins[index].isFree()) return inputPins[index];
        throw new PinAlreadyInUseException(this, index);
    }

    @Override
    public Pin getOutputPin(int index) throws PinAlreadyInUseException, PinNotExists {
        if(index > 0) throw new PinNotExists(this, index);
        if(index == 0 && outputPin.isFree()) return outputPin;
        throw new PinAlreadyInUseException(this, index);
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
