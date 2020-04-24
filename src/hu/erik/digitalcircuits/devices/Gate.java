package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;


/**
 * Abstract class which defines the default behavior of Gates.
 */
public abstract class Gate extends ConnectableDevice {
    private Pin[] inputPins;
    private Pin outputPin;

    /**
     * Creates a Gate with a specified number of input pins.
     *
     * @param numOfInputPins number of input pins
     */
    public Gate(int numOfInputPins) {
        this.outputPin = new Pin(this);
        this.inputPins = new Pin[numOfInputPins];
        for (int i = 0; i < inputPins.length; i++) inputPins[i] = new Pin(this);
    }

    /**
     * Returns an unconnected input pin on the device.
     *
     * @return                      unconnected input pin
     * @throws NoMorePinException   If the device doesn't have any more free input pin.
     */
    @Override
    public Pin getFreeInputPin() throws NoMorePinException {
        for(Pin pin : inputPins) {
            if(pin.isFree()) return pin;
        }
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
     * Returns an input pin based on the given index.
     * This pin might be in use.
     *
     * @param index                     index of the required pin
     * @return                          pin at the given index
     * @throws PinNotExistsException    If the selected pin is not exists.
     */
    @Override
    public Pin getInputPin(int index) throws PinNotExistsException {
        if(index >= inputPins.length) throw new PinNotExistsException(this, index);
        return inputPins[index];
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
     * Returns all of the input pins on the device.
     *
     * @return all input pins on the device
     */
    @Override
    public Pin[] getAllInputPins() {
        return inputPins;
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
     * Returns gate output pin.
     *
     * @return gate output pin
     */
    protected Pin getOutputPin() {
        return outputPin;
    }

}
