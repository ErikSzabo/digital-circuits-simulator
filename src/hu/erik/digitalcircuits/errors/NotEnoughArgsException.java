package hu.erik.digitalcircuits.errors;

/**
 * Cli exception that handles errors which caused by not giving enough arguments to the command.
 */
public class NotEnoughArgsException extends CliException {
    /**
     * Number of arguments that is required for the command to work properly.
     */
    private int reqArgNum;
    /**
     * Number of arguments that the user gave.
     */
    private int givenArgNum;

    /**
     * Constructor to initialize the command, the required argument number and
     * the given argument number.
     *
     * @param cmd           command where the problem occurs
     * @param reqArgNum     minimum required argument number
     * @param givenArgNum   number of arguments that the user gave
     */
    public NotEnoughArgsException(String cmd, int reqArgNum, int givenArgNum) {
        super(cmd);
        this.reqArgNum = reqArgNum;
        this.givenArgNum = givenArgNum;
    }

    /**
     * Returns an error message, which tells the user, how many arguments are required for the command.
     *
     * @return error message
     */
    @Override
    public String getMessage() {
        return "In command: " +
                getCmdOrName() +
                ", You only specified " +
                givenArgNum +
                " argument(s). You have to specify at least " +
                reqArgNum +
                " arguments!";
    }
}
