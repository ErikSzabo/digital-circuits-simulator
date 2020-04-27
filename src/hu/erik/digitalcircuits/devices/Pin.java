package hu.erik.digitalcircuits.devices;

import java.io.Serializable;

/**
 * Class to create Pins for Devices.
 * Devices can only connect to each other through Pins.
 */
public class Pin implements Serializable {

    /**
     * Cable which is connected to the pin.
     */
    private Cable connectionCable;
    /**
     * Device that holds this pin.
     */
    private Device parentDevice;
    /**
     * True or false signal that is used for logic.
     */
    private boolean signal;
    /**
     * Whether the pin is connected to something or not.
     */
    private boolean free;

    /**
     * Creates a Pin and set it's parent device.
     * You can only connect devices through pins.
     *
     * @param parentDevice Device to which it will be connected.
     */
    protected Pin(Device parentDevice) {
        this.parentDevice = parentDevice;
        this.signal = false;
        this.free = true;
    }

    /**
     * @return Connection Cable or null if there isn't any
     */
    public Cable getConnectionCable() {
        return connectionCable;
    }

    /**
     * @return Pin's parent device which it is bounded to.
     */
    public Device getParentDevice() {
        return parentDevice;
    }

    /**
     * Returns the current signal of the pin.
     *
     * @return signal that the pin has right now
     */
    public boolean getSignal() {
        return signal;
    }

    /**
     * This will set the pin's connection cable.
     * It shouldn't be used explicitly.
     *
     * @param connectionCable Cable to connect this pin to another
     */
    public void setConnectionCable(Cable connectionCable) {
        this.connectionCable = connectionCable;
    }

    /**
     * Sets the pin new signal.
     *
     * @param value New value
     */
    public void setSignal(boolean value) {
        this.signal = value;
    }

    /**
     * Returns true if the pin is not connected.
     *
     * @return Whether the pin is free or not
     */
    public boolean isFree() {
        return free;
    }

    /**
     * Sets the free status of the pin.
     *
     * @param value Determines the new free status for the pin
     */
    public void setAvailability(boolean value) {
        this.free = value;
    }
}
