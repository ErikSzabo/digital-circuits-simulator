package hu.erik.digitalcircuits.devices.build;

import java.io.Serializable;

/**
 * Cable is used to create connection between two pins.
 */
public class Cable implements Serializable {
    private Pin p1;
    private Pin p2;

    /**
     * Connects the two given pin.
     *
     * @param p1 The first pin
     * @param p2 The second pin
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
     * Get the pin which is not the one what is given
     *
     * @param pin The opposite pin
     * @return The other pin which is not the same as the parameter
     */
    public Pin getOtherPin(Pin pin) {
        if(p1.equals(pin)) {
            return p2;
        } else {
            return p1;
        }
    }
}
