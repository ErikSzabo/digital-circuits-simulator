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
    }

    /**
     * Calculates the output signal based on the input pins.
     * It"s using the nand gate truth table.
     */
    @Override
    public void calcOutput() {
        for(Pin inputPin : getAllInputPins()) {
            if(!inputPin.getValue()) {
                getOutputPin().setValue(true);
                return;
            }
        }
        getOutputPin().setValue(false);
    }

    @Override
    public String toString() {
        return DeviceType.NANDGATE;
    }
}
