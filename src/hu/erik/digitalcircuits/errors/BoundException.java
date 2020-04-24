package hu.erik.digitalcircuits.errors;

import hu.erik.digitalcircuits.devices.CircuitBox;

/**
 * Device exception that handles errors which caused by circuit box bindings.
 */
public class BoundException extends DeviceException {
    private String boxName;

    /**
     * Constructor to save the given box which had the binding problem.
     *
     * @param box the box with which the problem occurred
     */
    public BoundException(CircuitBox box) {
        super(box);
        this.boxName = box.getName();
    }


    /**
     * Returns an error specific message which tells that the required pin is already bound.
     *
     * @return error message
     */
    @Override
    public String getMessage() {
        return "[" + getDevice() + "] " + boxName + " >> this pin is already bound!";
    }
}
