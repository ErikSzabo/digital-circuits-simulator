package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.errors.NoMorePinException;

import java.util.ArrayList;

/**
 * Class to create Junctions.
 * Junctions are used for multiplying and sending their input signal
 * to the connected devices.
 */
public class Junction extends DispenserDevice {

    /**
     * Constructor to create Junction with the given number
     * of output pins.
     *
     * @param numOfOutputPins number of output pins
     */
    public Junction(int numOfOutputPins) {
        super(numOfOutputPins);
    }

    /**
     * Calculates the output.
     * In other words, it sets all of it's output pin signals
     * to it's input pin signal.
     */
    @Override
    public void calcOutput() {
        for(Pin pin : outputPins()) {
            pin.setSignal(getInputPin().getSignal());
        }
    }

    /**
     * Connects all of the given devices to the junction output pins.
     *
     * @param devices               devices which will be connected
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
     * @param devices               array of devices which will be connected
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
