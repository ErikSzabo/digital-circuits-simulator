package hu.erik.digitalcircuits.devices.build;

import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;

/**
 * Abstract class to create device which only have exactly one input and output pin
 */
public abstract class SimpleDevice extends ConnectableDevice {
    private Pin inputPin;
    private Pin outputPin;

    public SimpleDevice() {
        inputPin = new Pin(this);
        outputPin = new Pin(this);
    }

    /**
     * Send the output to connected devices and update them as well
     */
    @Override
    public void sendOutput() {
        transferValue(outputPin);
    }

    /**
     * Get a free input pin from the device
     *
     * @return Free input pin
     * @throws NoMorePinException If there isn't any more free input pin on the device
     */
    @Override
    public Pin getFreeInputPin() throws NoMorePinException {
        if(inputPin.isFree()) return inputPin;
        throw new NoMorePinException(this, "input");
    }

    /**
     * Get a free output pin from the device
     *
     * @return Free output pin
     * @throws NoMorePinException If there isn't any more free output pin on the device
     */
    @Override
    public Pin getFreeOutputPin() throws NoMorePinException {
        if(outputPin.isFree()) return outputPin;
        throw new NoMorePinException(this, "output");
    }

    /**
     * Get the selected input pin from the device
     *
     * @param index Index of the required input pin.
     * @return Free input pin
     * @throws PinNotExistsException If the selected input pin does not exists
     */
    @Override
    public Pin getInputPin(int index) throws PinNotExistsException {
        if(index == 0) return inputPin;
        throw new PinNotExistsException(this, index);
    }

    /**
     * Get the selected output pin from the device
     *
     * @param index Index of the required output pin
     * @return Free output pin
     * @throws PinNotExistsException If the selected output pin does not exists
     */
    @Override
    public Pin getOutputPin(int index) throws PinNotExistsException {
        if(index == 0) return outputPin;
        throw new PinNotExistsException(this, index);
    }

    protected Pin getInputPin() {
        return inputPin;
    }


    protected Pin getOutputPin() {
        return outputPin;
    }
}
