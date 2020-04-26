package hu.erik.digitalcircuits.devices;

/**
 * Abstract class which defines the default behavior for Devices which only
 * have one input and output pin.
 */
public abstract class SimpleDevice extends AbstractDevice {
    private Pin inputPin;
    private Pin outputPin;

    /**
     * Creates a basic SimpleDevice with one input and output pin.
     */
    public SimpleDevice() {
        inputPin = new Pin(this);
        outputPin = new Pin(this);
    }

    /**
     * Returns it's input pin in an array.
     *
     * @return all input pins on the device
     */
    @Override
    public Pin[] inputPins() {
        return new Pin[] {inputPin};
    }

    /**
     * Returns it's output pins in an array.
     *
     * @return all output pins on the device
     */
    @Override
    public Pin[] outputPins() {
        return new Pin[] {outputPin};
    }

    /**
     * Returns the input pin.
     *
     * @return the input pin
     */
    protected Pin getInputPin() {
        return inputPin;
    }

    /**
     * Returns the output pin.
     *
     * @return the output pin
     */
    protected Pin getOutputPin() {
        return outputPin;
    }
}
