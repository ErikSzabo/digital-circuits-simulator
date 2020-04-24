package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;

/**
 * Abstract class which defines the default behavior for Devices which only
 * have one input and output pin.
 */
public abstract class SimpleDevice extends ConnectableDevice {
    private Pin inputPin;
    private Pin outputPin;

    /**
     * Creates a basic SimpleDevice with one input and output pin.
     */
    public SimpleDevice() {
        inputPin = new Pin(this);
        outputPin = new Pin(this);
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
     * Returns it's output pin if it is free.
     *
     * @return                      unconnected output pin
     * @throws NoMorePinException   If the device doesn't have any more free output pin.
     */
    @Override
    public Pin getFreeOutputPin() throws NoMorePinException {
        if(outputPin.isFree()) return outputPin;
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
     * Returns an output pin if the given index is 0.
     * This pin might be in use.
     *
     * @param index                     index of the required pin
     * @return                          pin at the given index
     * @throws PinNotExistsException    If the selected pin is not exists.
     */
    @Override
    public Pin getOutputPin(int index) throws PinNotExistsException {
        if(index == 0) return outputPin;
        throw new PinNotExistsException(this, index);
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
     * Returns it's output pins in an array.
     *
     * @return all output pins on the device
     */
    @Override
    public Pin[] getAllOutputPins() {
        return new Pin[] {outputPin};
    }

    /**
     * Returns the input pin.
     *
     * @return the input pin
     */
    protected Pin getInputPin() {
        return inputPin;
    }

    /**
     * Returns the output pin.
     *
     * @return the output pin
     */
    protected Pin getOutputPin() {
        return outputPin;
    }
}
