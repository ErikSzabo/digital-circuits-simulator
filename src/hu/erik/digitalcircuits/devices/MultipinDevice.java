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
     * Returns all of the input pins on the device.
     *
     * @return all input pins on the device
     */
    @Override
    public Pin[] getAllInputPins() {
        return inputPins;
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

}
