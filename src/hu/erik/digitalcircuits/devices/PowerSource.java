package hu.erik.digitalcircuits.devices;

/**
 * Class to create Power Sources.
 */
public class PowerSource extends SimpleDevice {


    /**
     * Default constructor.
     * This will create exactly one input and output pin.
     */
    public PowerSource() {
        super();
    }


    /**
     * This will turn on the electricity and updates the device
     * that is connected to it.
     */
    public void on() {
        getInputPin().setValue(true);
        calcOutput();
        sendOutput();
    }

    /**
     * This will turn off the electricity and updates the device
     * that is connected to it.
     */
    public void off() {
        getInputPin().setValue(false);
        calcOutput();
        sendOutput();
    }


    /**
     * Calculates if there is power or not.
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
