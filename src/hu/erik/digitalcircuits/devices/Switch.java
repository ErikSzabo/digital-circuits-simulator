package hu.erik.digitalcircuits.devices;

/**
 * Class to create Switches.
 * These switches will most likely be your variables in circuits.
 */
public class Switch extends SimpleDevice {
    private boolean status;


    /**
     * Default constructor for Switch.
     * This will create exactly one input, and output pin.
     */
    public Switch() {
        this.status = false;
    }


    /**
     * This is going to turn on the Switch and
     * updates the device that is connected to it.
     * Switch can only be turned on, if there is a connected active power source.
     */
    public void on() {
        this.status = true;
        calcOutput();
        sendOutput();
    }

    /**
     * This is going to turn off the Switch and
     * updates the device that is connected to it.
     */
    public void off() {
        this.status = false;
        calcOutput();
        sendOutput();
    }


    /**
     * Calculate the Switch output based on available power source and the Switch status.
     */
    @Override
    public void calcOutput() {
        getOutputPin().setValue(getInputPin().getValue() && status);
    }

    @Override
    public String toString() {
        return DeviceType.SWITCH;
    }
}
