package hu.erik.digitalcircuits.devices;

/**
 * Abstract class which defines the default behavior of the devices
 * with multiple input and output pins.
 */
public abstract class MultipinDevice extends AbstractDevice {
    private Pin[] inputPins;
    private Pin[] outputPins;

    /**
     * Basic constructor to create Devices with the specified numbers of input
     * and output pins.
     *
     * @param numOfInputPins    Number of the required input pins
     * @param numOfOutputPins   Number of the required output pins
     */
    public MultipinDevice(int numOfInputPins, int numOfOutputPins) {
        this.inputPins = new Pin[numOfInputPins];
        this.outputPins = new Pin[numOfOutputPins];
        for (int i = 0; i < inputPins.length; i++) inputPins[i] = new Pin(this);
        for (int i = 0; i < outputPins.length; i++) outputPins[i] = new Pin(this);
    }

    /**
     * Returns all of the input pins on the device.
     *
     * @return all input pins on the device
     */
    @Override
    public Pin[] inputPins() {
        return inputPins;
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

}
