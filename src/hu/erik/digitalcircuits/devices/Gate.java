package hu.erik.digitalcircuits.devices;

/**
 * Abstract class which defines the default behavior of Gates.
 * Gates are mostly responsible for the circuit logic.
 */
public abstract class Gate extends AbstractDevice {
    /**
     * Holds all of the input pins.
     */
    private Pin[] inputPins;

    /**
     * Single output pin for Gates.
     */
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
     * Returns all of the input pins on the device.
     *
     * @return all input pins on the device
     */
    @Override
    public Pin[] inputPins() {
        return inputPins;
    }

    /**
     * Returns output pin in an array.
     *
     * @return all output pins on the device
     */
    @Override
    public Pin[] outputPins() {
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
