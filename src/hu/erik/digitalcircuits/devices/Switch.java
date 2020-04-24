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
     * Output will never be true if there aren't any PowerSource type devices connected to
     * the switch input pin. PowerSource can be connected via a junction as well.
     */
    @Override
    public void calcOutput() {
        // If there isn't any input, it most be false
        if(getInputPin().isFree()) {
            getOutputPin().setValue(false);
        }
        // If we got some input...
        else {
            // If the input is a nice PowerSource
            Device inputDevice = getInputPin().getConnectionCable().getOtherPin(getInputPin()).getParentDevice();
            if(inputDevice instanceof PowerSource) {
                getOutputPin().setValue(getInputPin().getValue() && status);
            }
            // If it's not but it's at least a junction
            else if(inputDevice instanceof Junction) {
                Pin juncInputPin = inputDevice.getAllInputPins()[0];
                // If the junction's input pin are actually connected to something
                if(!juncInputPin.isFree()) {
                    Device juncInputDevice = juncInputPin.getConnectionCable().getOtherPin(juncInputPin).getParentDevice();
                    // If the something is a PowerSource
                    if(juncInputDevice instanceof PowerSource) {
                        getOutputPin().setValue(getInputPin().getValue() && status);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return DeviceType.SWITCH;
    }
}
