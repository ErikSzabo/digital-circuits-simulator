package hu.erik.digitalcircuits.devices;

/**
 * Class to create Power Sources.
 */
public class PowerSource extends SimpleDevice {

    /**
     * Default constructor for PowerSource.
     * Sets the output signal to true, and sets the input
     * pin availability to false.
     */
    public PowerSource() {
        super();
        getInputPin().setAvailability(false);
        getOutputPin().setSignal(true);
    }

    /**
     * Sets its output to true, since PowerSource can only output true signal.
     */
    @Override
    public void calcOutput() {
        getOutputPin().setSignal(true);
    }

    @Override
    public String toString() {
        return "PowerSource";
    }
}
