package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.devices.build.ConnectableDevice;
import hu.erik.digitalcircuits.devices.build.Device;
import hu.erik.digitalcircuits.devices.build.Pin;
import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinAlreadyInUseException;
import hu.erik.digitalcircuits.errors.PinNotExists;

public class Junction extends ConnectableDevice {
    private Pin inputPin;
    private Pin[] outputPins;

    public Junction(int numOfOutputPins) {
        this.inputPin = new Pin(this);
        this.outputPins = new Pin[numOfOutputPins];
        for (int i = 0; i < outputPins.length; i++) outputPins[i] = new Pin(this);
    }

    @Override
    public void calcOutput() {
        for(Pin pin : outputPins) {
            pin.setValue(inputPin.getValue());
        }
    }

    @Override
    public Pin getFreeInputPin() throws NoMorePinException {
        if(inputPin.isFree()) return inputPin;
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
        if(index > 0) throw new PinNotExists(this, index);
        if(index == 0 && inputPin.isFree()) return inputPin;
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

    public void connectAll(Device... devices) {
        for(Device device : devices) {
            connect(device);
        }
    }

    @Override
    public String toString() {
        return "Junction";
    }
}
