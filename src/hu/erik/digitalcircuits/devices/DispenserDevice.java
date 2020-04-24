package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;

/**
 *  Abstract class which defines the default behavior of Dispenser devices.
 *  These type of devices can have multiple output pins but only one
 *  input pin.
 */
public abstract class DispenserDevice extends ConnectableDevice {
    private Pin inputPin;
    private Pin[] outputPins;

    /**
     * Basic constructor to create DispenserDevices with the
     * specified number of output pins.
     *
     * @param numOfOutputPins number of output pins
     */
    public DispenserDevice(int numOfOutputPins) {
        this.inputPin = new Pin(this);
        this.outputPins = new Pin[numOfOutputPins];
        for (int i = 0; i < outputPins.length; i++) outputPins[i] = new Pin(this);
    }

    /**
     * Returns it's input pin if it is unconnected.
     *
     * @return                      unconnected input pin
     * @throws NoMorePinException   If the it's input is already connected to something.
     */
    @Override
    public Pin getFreeInputPin() throws NoMorePinException {
        if(inputPin.isFree()) return inputPin;
        throw new NoMorePinException(this, "input");
    }

    /**
     * Returns an unconnected output pin on the device.
     *
     * @return                      unconnected output pin
     * @throws NoMorePinException   If the device doesn't have any more free output pin.
     */
    @Override
    public Pin getFreeOutputPin() throws NoMorePinException {
        for(Pin pin : outputPins) {
            if(pin.isFree()) return pin;
        }
        throw new NoMorePinException(this, "output");
    }

    /**
     * Returns an input pin if the given index is 0.
     *
     * @param index                     index of the required pin
     * @return                          pin at the given index
     * @throws PinNotExistsException    If the selected pin is not exists.
     */
    @Override
    public Pin getInputPin(int index) throws PinNotExistsException {
        if(index == 0) return inputPin;
        throw new PinNotExistsException(this, index);
    }

    /**
     * Returns an output pin based on the given index.
     * This pin might be in use.
     *
     * @param index                     index of the required pin
     * @return                          pin at the given index
     * @throws PinNotExistsException    If the selected pin is not exists.
     */
    @Override
    public Pin getOutputPin(int index) throws PinNotExistsException {
        if(index >= outputPins.length) throw new PinNotExistsException(this, index);
        return outputPins[index];
    }

    /**
     * Returns it's input pin in an array.
     *
     * @return all input pins on the device
     */
    @Override
    public Pin[] getAllInputPins() {
        return new Pin[] {inputPin};
    }

    /**
     * Returns all of the output pins on the device.
     *
     * @return all output pins on the device
     */
    @Override
    public Pin[] getAllOutputPins() {
        return outputPins;
    }

    /**
     * Returns the input pin.
     *
     * @return the input pin
     */
    protected Pin getInputPin() {
        return inputPin;
    }
}
