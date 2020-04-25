package hu.erik.digitalcircuits.devices;

/**
 * Class to create Nor gates.
 */
public class NorGate extends Gate {

    /**
     * Constructor to create a Nor gate with the given number of input pins.
     *
     * @param numOfInputPins number of input pins
     */
    public NorGate(int numOfInputPins) {
        super(numOfInputPins);
        getOutputPin().setValue(true);
    }

    /**
     * Calculates the output signal based on the input pins.
     * It"s using the nor gate truth table.
     */
    @Override
    public void calcOutput() {
        for(Pin inputPin: getAllInputPins()) {
            if(inputPin.getValue()) {
                getOutputPin().setValue(false);
                return;
            }
        }
        getOutputPin().setValue(true);
    }

    @Override
    public String toString() {
        return "NorGate";
    }
}
