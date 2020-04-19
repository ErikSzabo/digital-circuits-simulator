package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.CircuitBox;

public class BoundException extends DeviceException {
    private String boxName;

    public BoundException(CircuitBox box) {
        super(box);
        this.boxName = box.getName();
    }

    @Override
    public String getMessage() {
        return "[" + getDevice() + "] " + boxName + " >> this pin is already bound!";
    }
}
