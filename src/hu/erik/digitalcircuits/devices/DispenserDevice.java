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
     * @param numOfOutputPins Number of required output pins
     */
    public DispenserDevice(int numOfOutputPins) {
        this.inputPin = new Pin(this);
        this.outputPins = new Pin[numOfOutputPins];
        for (int i = 0; i < outputPins.length; i++) outputPins[i] = new Pin(this);
    }

    /**
     * Method to find an unconnected input pin on the Device.
     *
     * @return Pin or null if there isn't a free input pin
     * @throws NoMorePinException If the device doesn't have any more free input pins.
     */
    @Override
    public Pin getFreeInputPin() throws NoMorePinException {
        if(inputPin.isFree()) return inputPin;
        throw new NoMorePinException(this, "input");
    }

    /**
     * Method to find an unconnected output pin on the Device.
     *
     * @return Pin or null if there isn't a free output pin
     * @throws NoMorePinException If the device doesn't have any more free output pins.
     */
    @Override
    public Pin getFreeOutputPin() throws NoMorePinException {
        for(Pin pin : outputPins) {
            if(pin.isFree()) return pin;
        }
        throw new NoMorePinException(this, "output");
    }

    /**
     * Method to get a specified input pin.
     *
     * @param index Index of the required input pin.
     * @return Pin or null if there isn't any pin at the given index.
     * @throws PinNotExistsException If the selected pin is not exists
     */
    @Override
    public Pin getInputPin(int index) throws PinNotExistsException {
        if(index == 0) return inputPin;
        throw new PinNotExistsException(this, index);
    }

    /**
     * Method to get a specified output pin.
     *
     * @param index Index of the required output pin
     * @return Pin or null if there isn't any pin at the given index
     * @throws PinNotExistsException If the selected pin is not exists
     */
    @Override
    public Pin getOutputPin(int index) throws PinNotExistsException {
        if(index >= outputPins.length) throw new PinNotExistsException(this, index);
        return outputPins[index];
    }

    /**
     * This method should take care of signal transfer and auto update
     * on all output pins.
     */
    @Override
    public void sendOutput() {
        for(Pin pin : outputPins) {
            transferValue(pin);
        }
    }

    /**
     * Method to get all of the input pins on the Device.
     *
     * @return All of the input pins on the Device.
     */
    @Override
    public Pin[] getAllInputPins() {
        return new Pin[] {inputPin};
    }

    /**
     * Method to get all of the output pins on the Device.
     *
     * @return All of the output pins on the Device.
     */
    @Override
    public Pin[] getAllOutputPins() {
        return outputPins;
    }

    /**
     * Returns the input pin.
     * Should only be used in subclasses
     *
     * @return The input pin
     */
    protected Pin getInputPin() {
        return inputPin;
    }
}
