package hu.erik.digitalcircuits.errors;

/**
 * Cli exception that handles errors which caused by using invalid arguments in commands.
 */
public class InvalidArgumentException extends CliException {
    private String argument;

    /**
     * Constructor to save the command and the invalid argument.
     *
     * @param cmd       the command with the problem occurs
     * @param argument  the invalid argument
     */
    public InvalidArgumentException(String cmd, String argument) {
        super(cmd);
        this.argument = argument;
    }

    /**
     * Returns an error specific message which shows the invalid command and argument.
     *
     * @return error message
     */
    @Override
    public String getMessage() {
        return "For command: " + getCmdOrName() + ", argument: " + argument + ", is invalid or it is not in the correct place!";
    }
}
