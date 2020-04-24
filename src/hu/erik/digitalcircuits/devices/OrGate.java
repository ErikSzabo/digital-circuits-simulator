package hu.erik.digitalcircuits.devices;

/**
 * Class to create Or gates.
 */
public class OrGate extends Gate {

    /**
     * Constructor to create an Or gate.
     *
     * @param numOfInputPins number of input pins
     */
    public OrGate(int numOfInputPins) {
        super(numOfInputPins);
    }

    /**
     * Calculates the output signal based on the input pins.
     * It"s using the or gate truth table.
     */
    @Override
    public void calcOutput() {
        for(Pin inputPin: getAllInputPins()) {
            if(inputPin.getValue()) {
                getOutputPin().setValue(true);
                return;
            }
        }
        getOutputPin().setValue(false);
    }

    @Override
    public String toString() {
        return DeviceType.ORGATE;
    }
}
