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
     * @param numOfInputPins Number of required input pins
     */
    public Gate(int numOfInputPins) {
        this.outputPin = new Pin(this);
        this.inputPins = new Pin[numOfInputPins];
        for (int i = 0; i < inputPins.length; i++) inputPins[i] = new Pin(this);
    }

    /**
     * Method to find an unconnected input pin on the Gate
     *
     * @return Pin or null if there isn't a free input pin
     * @throws NoMorePinException If the device doesn't have any more free input pins.
     */
    @Override
    public Pin getFreeInputPin() throws NoMorePinException {
        for(Pin pin : inputPins) {
            if(pin.isFree()) return pin;
        }
        throw new NoMorePinException(this, "input");
    }

    /**
     * Method to find an unconnected output pin on the Gate
     *
     * @return Pin or null if there isn't a free output pin
     * @throws NoMorePinException If the device doesn't have any more free output pins.
     */
    @Override
    public Pin getFreeOutputPin() throws NoMorePinException {
        if(outputPin.isFree()) return outputPin;
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
        if(index >= inputPins.length) throw new PinNotExistsException(this, index);
        return inputPins[index];
    }

    /**
     * Method to get a specified output pin.
     * On gates, there are only one output pin, so only the 0 index
     * will be valid.
     *
     * @param index Index of the required output pin
     * @return Pin or null if there isn't any pin at the given index
     * @throws PinNotExistsException If the selected pin is not exists
     */
    @Override
    public Pin getOutputPin(int index) throws PinNotExistsException {
        if(index == 0) return outputPin;
        throw new PinNotExistsException(this, index);
    }

    /**
     * Transfer the value from the output pin to the target device.
     */
    @Override
    public void sendOutput() {
        transferValue(outputPin);
    }

    /**
     * Method to get all of the input pins on the Gate.
     *
     * @return All of the input pins on the Device.
     */
    @Override
    public Pin[] getAllInputPins() {
        return inputPins;
    }

    /**
     * Method to get all of the output pins on the Gate.
     *
     * @return All of the output pins on the Device.
     */
    @Override
    public Pin[] getAllOutputPins() {
        return new Pin[] {outputPin};
    }

    /**
     * Method to get a Gate output pin.
     * This method should only be used in the subclasses.
     *
     * @return Gate output pin
     */
    protected Pin getOutputPin() {
        return outputPin;
    }

}
