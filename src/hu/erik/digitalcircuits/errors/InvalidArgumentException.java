package hu.erik.digitalcircuits.errors;

public class InvalidArgumentException extends CliException {
    private String argument;

    public InvalidArgumentException(String cmd, String argument) {
        super(cmd);
        this.argument = argument;
    }

    @Override
    public String getMessage() {
        return "For command: " + getCmdOrName() + ", Argument: " + argument + " is invalid or it is not in the correct place!";
    }
}
