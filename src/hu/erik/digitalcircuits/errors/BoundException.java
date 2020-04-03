package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.CircuitBox;

public class BoundException extends Exception {
    private CircuitBox box;

    public BoundException(CircuitBox box) {
        this.box = box;
    }

    @Override
    public String getMessage() {
        return "[" +box + "] " + box.getName() + " >> this pin is already bound!";
    }
}
