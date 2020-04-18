package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;

/**
 * Abstract class which defines the default behavior of the devices
 * with multiple input and output pins.
 */
public abstract class MultipinDevice extends ConnectableDevice {
    private Pin[] inputPins;
    private Pin[] outputPins;

    /**
     * Basic constructor to create Devices with the specified numbers of input
     * and output pins.
     *
     * @param numOfInputPins    Number of the required input pins
     * @param numOfOutputPins   Number of the required output pins
     */
    public MultipinDevice(int numOfInputPins, int numOfOutputPins) {
        this.inputPins = new Pin[numOfInputPins];
        this.outputPins = new Pin[numOfOutputPins];
        for (int i = 0; i < inputPins.length; i++) inputPins[i] = new Pin(this);
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
        for(Pin pin : inputPins) {
            if(pin.isFree()) return pin;
        }
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
        if(index >= inputPins.length) throw new PinNotExistsException(this, index);
        return inputPins[index];
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
     * This will transfer the value from all of the output pins to the connected devices.
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
        return inputPins;
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

}
