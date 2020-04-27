package hu.erik.digitalcircuits.devices;

/**
 * Class to create Switches.
 * These switches will most likely be your variables in circuits.
 */
public class Switch extends SimpleDevice {
    /**
     * Shows whether the switch is on or off.
     */
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
     * Updates the whole connected circuit.
     */
    public void on() {
        this.status = true;
        calcOutput();
        sendOutput();
    }

    /**
     * Sets the switch state or status to false.
     * The output now has to be false.
     * Updates the whole connected circuit.
     */
    public void off() {
        this.status = false;
        calcOutput();
        sendOutput();
    }


    /**
     * Calculates the Switch output based on it's status or state and it's input pin value.
     * Output will only be true, if the input is true as well as the status.
     */
    @Override
    public void calcOutput() {
        getOutputPin().setSignal(getInputPin().getSignal() && status);
    }

    @Override
    public String toString() {
        return "Switch";
    }
}
