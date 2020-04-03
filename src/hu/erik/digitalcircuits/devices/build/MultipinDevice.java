package hu.erik.digitalcircuits.devices.build;

import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinAlreadyInUseException;
import hu.erik.digitalcircuits.errors.PinNotExists;

public abstract class MultipinDevice extends ConnectableDevice {
    private Pin[] inputPins;
    private Pin[] outputPins;

    public MultipinDevice(int numOfInputPins, int numOfOutputPins) {
        this.inputPins = new Pin[numOfInputPins];
        this.outputPins = new Pin[numOfOutputPins];
        for (int i = 0; i < inputPins.length; i++) inputPins[i] = new Pin(this);
        for (int i = 0; i < outputPins.length; i++) outputPins[i] = new Pin(this);
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
        for(Pin pin : outputPins) {
            if(pin.isFree()) return pin;
        }
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
        if(index >= outputPins.length) throw new PinNotExists(this, index);
        if(outputPins[index].isFree()) return outputPins[index];
        throw new PinAlreadyInUseException(this, index);
    }

    @Override
    public void sendOutput() {
        for(Pin pin : outputPins) {
            transferValue(pin);
        }
    }

    protected Pin[] getInputPins() {
        return inputPins;
    }

    protected Pin[] getOutputPins() {
        return outputPins;
    }
}
