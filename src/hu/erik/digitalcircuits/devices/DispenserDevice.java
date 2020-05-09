package hu.erik.digitalcircuits.devices;

/**
 *  Abstract class which defines the default behavior of Dispenser devices.
 *  These type of devices can have multiple output pins but only one
 *  input pin.
 */
public abstract class DispenserDevice extends AbstractDevice {
    /**
     * The one and only input pin on a DispenserDevice
     */
    private Pin inputPin;

    /**
     * Holds all of the output pins.
     */
    private Pin[] outputPins;

    /**
     * Basic constructor to create DispenserDevices with the
     * specified number of output pins.
     *
     * @param numOfOutputPins number of output pins
     */
    public DispenserDevice(int numOfOutputPins) {
        this.inputPin = new Pin(this);
        this.outputPins = new Pin[numOfOutputPins];
        for (int i = 0; i < outputPins.length; i++) outputPins[i] = new Pin(this);
    }


    /**
     * Returns input pin in an array.
     *
     * @return all input pins on the device
     */
    @Override
    public Pin[] inputPins() {
        return new Pin[] {inputPin};
    }

    /**
     * Returns all of the output pins on the device.
     *
     * @return all output pins on the device
     */
    @Override
    public Pin[] outputPins() {
        return outputPins;
    }

    /**
     * Returns the input pin.
     *
     * @return the input pin
     */
    protected Pin getInputPin() {
        return inputPin;
    }
}
