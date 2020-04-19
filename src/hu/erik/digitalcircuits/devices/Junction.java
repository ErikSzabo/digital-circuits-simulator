package hu.erik.digitalcircuits.devices;

/**
 * Class to create Junctions.
 */
public class Junction extends DispenserDevice {

    /**
     * Constructor to create Junction with the given number
     * of output pins.
     *
     * @param numOfOutputPins Required amount of output pins
     */
    public Junction(int numOfOutputPins) {
        super(numOfOutputPins);
    }

    /**
     * Calculate the output. It is basically
     * sends to every output pin whatever is it's input pin value.
     */
    @Override
    public void calcOutput() {
        for(Pin pin : getAllOutputPins()) {
            pin.setValue(getInputPin().getValue());
        }
    }

    /**
     * Connects all of the given devices to the junction output pins.
     *
     * @param devices Array of devices you want to connect
     */
    public void connectAll(Device... devices) {
        for(Device device : devices) {
            connect(device);
        }
    }

    @Override
    public String toString() {
        return DeviceType.JUNCTION;
    }
}
