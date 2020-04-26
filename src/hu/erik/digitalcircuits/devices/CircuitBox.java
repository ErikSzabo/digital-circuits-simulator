package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.errors.BoundException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;

import java.util.Arrays;

/**
 * Represents a digital circuit in a box.
 */
public class CircuitBox extends MultipinDevice {
    /**
     * Name of the box.
     */
    private String name;
    /**
     * Stores records about the already bounded input pins.
     * Holds the same amount of elements as the input pin array.
     * If at an index the value is true, that pin at this index is already bounded.
     */
    private Boolean[] boundedInputs;
    /**
     * Stores records about the already bounded output pins.
     * Holds the same amount of elements as the output pin array.
     * If at an index the value is true, that pin at this index is already bounded.
     */
    private Boolean[] boundedOutputs;

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
        boundedInputs = new Boolean[numOfInputPins];
        boundedOutputs = new Boolean[numOfOutputPins];
        Arrays.fill(boundedInputs, Boolean.FALSE);
        Arrays.fill(boundedOutputs, Boolean.FALSE);
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
     * @param target                    device to bind
     * @param pinIndex                  pin index on the device
     * @param boxPinIndex               pin index on the box
     * @throws BoundException           if an already bound pin is targeted on the box.
     * @throws PinNotExistsException    If there isn't any pin at the given index.
     */
    public void bindInputPin(Device target, int pinIndex, int boxPinIndex) throws BoundException, PinNotExistsException {
        if(boxPinIndex > inputPins().length - 1) throw new PinNotExistsException(this, boxPinIndex);
        if(pinIndex > target.inputPins().length - 1) throw new PinNotExistsException(target, pinIndex);
        if(boundedInputs[boxPinIndex]) throw new BoundException(this);
        inputPins()[boxPinIndex] = target.inputPins()[pinIndex];
        boundedInputs[boxPinIndex] = true;
    }


    /**
     * Binds the target device output pin with the box output pin based on the given indexes.
     *
     * @param target                    device to bind
     * @param pinIndex                  pin index on the device
     * @param boxPinIndex               pin index on the box
     * @throws BoundException           if an already bound pin is targeted on the box.
     * @throws PinNotExistsException    If there isn't any pin at the given index.
     */
    public void bindOutputPin(Device target, int pinIndex, int boxPinIndex) throws BoundException, PinNotExistsException {
        if(boxPinIndex > outputPins().length - 1) throw new PinNotExistsException(this, boxPinIndex);
        if(pinIndex > target.inputPins().length - 1) throw new PinNotExistsException(target, pinIndex);
        if(boundedOutputs[boxPinIndex]) throw new BoundException(this);
        outputPins()[boxPinIndex] = target.outputPins()[pinIndex];
        boundedOutputs[boxPinIndex] = true;
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
     *
     * @return the type of the Device
     */
    @Override
    public String toString() {
        return "CircuitBox";
    }
}
