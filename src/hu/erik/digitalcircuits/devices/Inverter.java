package hu.erik.digitalcircuits.devices;

/**
 * Class to create Inverters.
 */
public class Inverter extends SimpleDevice {

    /**
     * Default constructor to initialize inverter starting output.
     */
    public Inverter() {
        getOutputPin().setValue(true);
    }

    /**
     * Calculates the output signal based on the input pin.
     * It"s using the inverter truth table.
     */
    @Override
    public void calcOutput() {
        getOutputPin().setValue(!getInputPin().getValue());
    }

    @Override
    public String toString() {
        return "Inverter";
    }
}
