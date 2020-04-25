package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.errors.BoundException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;

/**
 * Represents a digital circuit in a box.
 */
public class CircuitBox extends MultipinDevice {
    private String name;

    /**
     * Constructor to create Circuit Boxes with the given name
     * and numbers of input and output pins.
     *
     * @param name              name of the CircuitBox
     * @param numOfInputPins    number of input pins
     * @param numOfOutputPins   number of output pins
     */
    public CircuitBox(String name, int numOfInputPins, int numOfOutputPins) {
        super(numOfInputPins, numOfOutputPins);
        this.name = name;
    }

    /**
     * Empty method. No need for it because of reference pin binding.
     */
    @Override
    public void calcOutput() {
        // This will never run
    }

    /**
     * Binds the target device input pin with the box input pin based on the given indexes.
     *
     * @param d             device to bind
     * @param pinIndex      pin index on the device
     * @param boxPinIndex   pin index on the box
     * @throws BoundException           if an already bound pin is targeted on the box.
     * @throws PinNotExistsException    If there isn't any pin at the given index.
     */
    public void bindInputPin(Device d, int pinIndex, int boxPinIndex) throws BoundException, PinNotExistsException {
        Pin bindPin = d.getInputPin(pinIndex);
        Pin boxPin = getInputPin(boxPinIndex);
        if(!boxPin.getParentDevice().toString().equals(DeviceType.CIRCUITBOX)) throw new BoundException(this);
        getAllInputPins()[boxPinIndex] = bindPin;
    }


    /**
     * Binds the target device output pin with the box output pin based on the given indexes.
     *
     * @param d             device to bind
     * @param pinIndex      pin index on the device
     * @param boxPinIndex   pin index on the box
     * @throws BoundException           if an already bound pin is targeted on the box.
     * @throws PinNotExistsException    If there isn't any pin at the given index.
     */
    public void bindOutputPin(Device d, int pinIndex, int boxPinIndex) throws BoundException, PinNotExistsException {
        Pin bindPin = d.getOutputPin(pinIndex);
        Pin boxPin = getOutputPin(boxPinIndex);
        if(!boxPin.getParentDevice().toString().equals(DeviceType.CIRCUITBOX)) throw new BoundException(this);
        getAllOutputPins()[boxPinIndex] = bindPin;
    }

    /**
     * Returns the name of the Box. This will be used in saving and loading
     * circuits from/to file.
     *
     * @return the name of the CircuitBox
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the device type specified in the DeviceType static class.
     * CircuitBox binding methods are using this. Changes should be made very carefully.
     *
     * @return the type of the Device
     */
    @Override
    public String toString() {
        return DeviceType.CIRCUITBOX;
    }
}
