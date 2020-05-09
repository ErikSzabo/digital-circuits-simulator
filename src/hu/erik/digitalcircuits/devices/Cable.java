package hu.erik.digitalcircuits.devices;

import java.io.Serializable;

/**
 * Cable is used to create connection between two pins.
 */
public class Cable implements Serializable {
    /**
     * Pin at one end of the cable.
     */
    private Pin p1;
    /**
     * Pin at the other end of the cable.
     */
    private Pin p2;

    /**
     * Connects two given pin by setting the pins connection cable to this Cable.
     * Sets the pins availability to false, so the given pins are not connectable
     * anymore.
     *
     * @param p1 first pin
     * @param p2 second pin
     */
    protected Cable(Pin p1, Pin p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.p1.setConnectionCable(this);
        this.p2.setConnectionCable(this);
        this.p1.setAvailability(false);
        this.p2.setAvailability(false);
    }

    /**
     * Returns the pin that is positioned at the other end of the Cable,
     * based on the given pin. If the given pin isn't connected to the Cable,
     * then null will be returned
     *
     * @param pin   one of the cable's pins
     * @return      the other pin on the Cable or null
     */
    public Pin getOtherPin(Pin pin) {
        if(p1.equals(pin)) {
            return p2;
        } else if(p2.equals(pin)) {
            return p1;
        }
        return null;
    }
}
