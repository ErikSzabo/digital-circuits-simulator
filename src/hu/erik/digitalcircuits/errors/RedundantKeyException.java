package hu.erik.digitalcircuits.errors;

/**
 * Cli exception that handles errors which caused by adding devices with a name that
 * already has a mapping in the data structure.
 */
public class RedundantKeyException extends CliException {

    /**
     * Default constructor to initialize the duplicated name.
     *
     * @param key the device name that is already exists
     */
    public RedundantKeyException(String key) {
        super(key);
    }

    /**
     * Returns an error specific message which shows information about the duplication.
     *
     * @return error message
     */
    @Override
    public String getMessage() {
        return "Device with name: " + getCmdOrName() + ", is already exists!";
    }
}
