package hu.erik.digitalcircuits.devices;

/**
 * Class to create Nand gates.
 */
public class NandGate extends Gate {

    /**
     * Constructor to create a Nand gate with the given number of input pins.
     *
     * @param numOfInputPins number of input pins
     */
    public NandGate(int numOfInputPins) {
        super(numOfInputPins);
        getOutputPin().setSignal(true);
    }

    /**
     * Calculates the output signal based on the input pins.
     * It"s using the nand gate truth table.
     */
    @Override
    public void calcOutput() {
        for(Pin inputPin : inputPins()) {
            if(!inputPin.getSignal()) {
                getOutputPin().setSignal(true);
                return;
            }
        }
        getOutputPin().setSignal(false);
    }

    @Override
    public String toString() {
        return "NandGate";
    }
}
