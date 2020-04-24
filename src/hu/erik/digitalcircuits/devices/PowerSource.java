package hu.erik.digitalcircuits.devices;

/**
 * Class to create Power Sources.
 */
public class PowerSource extends SimpleDevice {

    /**
     * Sets the output signal to true.
     */
    public void on() {
        getInputPin().setValue(true);
        calcOutput();
        sendOutput();
    }

    /**
     * Sets the output signal to false.
     */
    public void off() {
        getInputPin().setValue(false);
        calcOutput();
        sendOutput();
    }

    /**
     * Calculates the output purely based on the input pin value.
     */
    @Override
    public void calcOutput() {
        getOutputPin().setValue(getInputPin().getValue());
    }

    @Override
    public String toString() {
        return DeviceType.POWER;
    }
}
