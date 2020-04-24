package hu.erik.digitalcircuits.devices;

/**
 * Class to create Switches.
 * These switches will most likely be your variables in circuits.
 */
public class Switch extends SimpleDevice {
    private boolean status;


    /**
     * Default constructor for Switch.
     * This will create exactly one input, and output pin, and also
     * sets it's own state or status to false, which means it's not turned on.
     */
    public Switch() {
        this.status = false;
    }


    /**
     * Sets the switch state or status to true.
     * Output can only be true if this is true as well.
     */
    public void on() {
        this.status = true;
        calcOutput();
        sendOutput();
    }

    /**
     * Sets the switch state or status to false.
     * The output now has to be false.
     */
    public void off() {
        this.status = false;
        calcOutput();
        sendOutput();
    }


    /**
     * Calculate the Switch output based on available power source and the Switch status.
     * Output will never be true if there aren't any powersource type devices connected to
     * the switch input pin.
     */
    @Override
    public void calcOutput() {
        if(getInputPin().isFree()) {
            getOutputPin().setValue(false);
        } else {
            Device inputDevice = getInputPin().getConnectionCable().getOtherPin(getInputPin()).getParentDevice();
            getOutputPin().setValue(getInputPin().getValue() && status && inputDevice instanceof PowerSource);
        }
    }

    @Override
    public String toString() {
        return DeviceType.SWITCH;
    }
}
