package hu.erik.digitalcircuits.devices;

import java.io.Serializable;

/**
 * Class to create Pins for Devices.
 * Devices can only connect to each other through Pins.
 */
public class Pin implements Serializable {
    private Cable connectionCable;
    private Device parentDevice;
    private boolean value;
    private boolean free;

    /**
     * Creates a Pin and set it's parent device.
     * You can only connect devices through pins.
     *
     * @param parentDevice Device to which it will be connected.
     */
    protected Pin(Device parentDevice) {
        this.parentDevice = parentDevice;
        this.value = false;
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
     * @return Value that the pin has right now.
     */
    public boolean getValue() {
        return value;
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
     * Sets the pin new value.
     *
     * @param value New value
     */
    public void setValue(boolean value) {
        this.value = value;
    }

    /**
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
