package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.errors.NoMorePinException;

import java.util.ArrayList;

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
     * @param devices               Array of devices you want to connect
     * @throws NoMorePinException   If any of the devices doesn't have more free pins.
     */
    public void connectAll(Device... devices) throws NoMorePinException {
        for(Device device : devices) {
            connect(device);
        }
    }

    /**
     * Connects all of the given devices to the junction output pins.
     *
     * @param devices               Array of devices you want to connect
     * @throws NoMorePinException   If any of the devices doesn't have more free pins.
     */
    public void connectAll(ArrayList<Device> devices) throws NoMorePinException {
        for(Device device : devices) {
            connect(device);
        }
    }

    @Override
    public String toString() {
        return "Junction";
    }
}
