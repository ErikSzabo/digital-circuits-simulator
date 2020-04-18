package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.errors.BoundException;
import hu.erik.digitalcircuits.errors.PinAlreadyInUseException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;
import hu.erik.digitalcircuits.utils.Printer;

/**
 * Class to create Circuit Boxes.
 */
public class CircuitBox extends MultipinDevice {
    private String name;

    /**
     * Constructor to create Circuit Boxes whit the given name
     * and numbers of input and output pins.
     *
     * @param name              Name of the CircuitBox
     * @param numOfInputPins    Number of input pins
     * @param numOfOutputPins   Number of output pins
     */
    public CircuitBox(String name, int numOfInputPins, int numOfOutputPins) {
        super(numOfInputPins, numOfOutputPins);
        this.name = name;
    }

    /**
     * This will calculate the CircuitBox output.
     * Actually... This will never run...
     */
    @Override
    public void calcOutput() {
        // This will never run
    }

    /**
     * With this method you can bind the device input pin with
     * the box input pin. The two will be the same after the binding.
     *
     * @param d             Device you want to bind
     * @param pinIndex      Pin index on the device
     * @param boxPinIndex   Pin index on the box
     */
    public void bindInputPin(Device d, int pinIndex, int boxPinIndex) {
        try {
            Pin bindPin = d.getInputPin(pinIndex);
            Pin boxPin = getInputPin(boxPinIndex);
            if(!bindPin.isFree()) throw new PinAlreadyInUseException(d, pinIndex);
            if(!boxPin.isFree()) throw new PinAlreadyInUseException(this, boxPinIndex);
            if(!boxPin.getParentDevice().toString().equals("CircuitBox")) throw new BoundException(this);
            getAllInputPins()[boxPinIndex] = bindPin;
        } catch (PinAlreadyInUseException | PinNotExistsException | BoundException err) {
            Printer.printErr(err);
        }
    }


    /**
     * With this method you can bind the device output pin with
     * the box output pin. The two will be the same after the binding.
     *
     * @param d             Device you want to bind
     * @param pinIndex      Pin index on the device
     * @param boxPinIndex   Pin index on the box
     */
    public void bindOutputPin(Device d, int pinIndex, int boxPinIndex) {
        try {
            Pin bindPin = d.getOutputPin(pinIndex);
            Pin boxPin = getOutputPin(boxPinIndex);
            if(!bindPin.isFree()) throw new PinAlreadyInUseException(d, pinIndex);
            if(!boxPin.isFree()) throw new PinAlreadyInUseException(this, boxPinIndex);
            if(!boxPin.getParentDevice().toString().equals("CircuitBox")) throw new BoundException(this);
            getAllOutputPins()[boxPinIndex] = bindPin;
        } catch (PinAlreadyInUseException | PinNotExistsException | BoundException err) {
            Printer.printErr(err);
        }
    }

    /**
     * @return The name of CircuitBox
     */
    public String getName() {
        return name;
    }


    /**
     * This toString will return CircuitBox.
     * It's very important to do not change this!
     * If you change this, you have to change in the bind methods as well,
     * otherwise the bind methods won't work.
     *
     * @return The type of the Device
     */
    @Override
    public String toString() {
        return "CircuitBox";
    }
}
