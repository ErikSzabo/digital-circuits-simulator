package hu.erik.digitalcircuits.errors;

/**
 * Default exception to handle Cli errors.
 */
public abstract class CliException extends Exception {
    private String cmdOrName;

    /**
     * Constructor to save the wrong command or the wong
     * device name.
     *
     * @param cmdOrName wrong command or device name
     */
    public CliException(String cmdOrName) {
        this.cmdOrName = cmdOrName;
    }

    /**
     * Returns the command or the device name, what is used incorrectly.
     *
     * @return the stored string
     */
    public String getCmdOrName() {
        return cmdOrName;
    }

    /**
     * Returns the error message. Defined as abstract, so it's
     * children must implement this method.
     *
     * @return error message
     */
    @Override
    public abstract String getMessage();

}
