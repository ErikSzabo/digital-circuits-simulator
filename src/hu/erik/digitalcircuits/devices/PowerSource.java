package hu.erik.digitalcircuits.devices;

/**
 * Class to create Power Sources.
 */
public class PowerSource extends SimpleDevice {

    /**
     * Sets the output signal to true.
     * Updates the whole connected circuit.
     */
    public void on() {
        getInputPin().setSignal(true);
        calcOutput();
        sendOutput();
    }

    /**
     * Sets the output signal to false.
     * Updates the whole connected circuit.
     */
    public void off() {
        getInputPin().setSignal(false);
        calcOutput();
        sendOutput();
    }

    /**
     * Calculates the output purely based on the input pin value.
     */
    @Override
    public void calcOutput() {
        getOutputPin().setSignal(getInputPin().getSignal());
    }

    @Override
    public String toString() {
        return "PowerSource";
    }
}
