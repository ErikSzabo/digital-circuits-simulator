package hu.erik.digitalcircuits.devices;

/**
 * Class to create And gates.
 */
public class AndGate extends Gate {

    /**
     * Constructor to create an And gate with the given number of input pins.
     *
     * @param numOfInputPins required amount of input pins
     */
    public AndGate(int numOfInputPins) {
        super(numOfInputPins);
    }

    /**
     * Calculates the output signal based on the input pins.
     * It's using the and gate truth table.
     */
    @Override
    public void calcOutput() {
        for(Pin inputPin : getAllInputPins()) {
            if(!inputPin.getValue()) {
                getOutputPin().setValue(false);
                return;
            }
        }
        getOutputPin().setValue(true);
    }

    @Override
    public String toString() {
        return DeviceType.ANDGATE;
    }
}
