package hu.erik.digitalcircuits.errors;

/**
 * Cli exception that handles errors which caused by accessing non-existent devices.
 */
public class DeviceNotExistsException extends CliException {

    /**
     * Constructor that takes in a non-existent device name.
     *
     * @param name name that does not belongs to any device
     */
    public DeviceNotExistsException(String name) {
        super(name);
    }

    /**
     * Returns an error specific message which shows the non-existent name.
     *
     * @return error message
     */
    @Override
    public String getMessage() {
        return "There isn't any device with this name: " + getCmdOrName();
    }
}
