package hu.erik.digitalcircuits.errors;

/**
 * Cli exception that handles errors which caused by giving too many arguments to a command.
 */
public class TooManyArgumentException extends CliException {

    /**
     * Default constructor to initialize the command which got too many arguments.
     *
     * @param cmd command that got too many arguments
     */
    public TooManyArgumentException(String cmd) {
        super(cmd);
    }

    /**
     * Returns an error specific message which tells the user that the extra arguments will be ignored.
     *
     * @return error message
     */
    @Override
    public String getMessage() {
        return "For command: " + getCmdOrName() + ", there are too many arguments specified! Extra args will be ignored!";
    }
}
