package hu.erik.digitalcircuits.errors.clierror;

public class InvalidArgumentException extends Exception {
    private String command;
    private String argument;

    public InvalidArgumentException(String command, String argument) {
        this.command = command;
        this.argument = argument;
    }

    @Override
    public String getMessage() {
        return "For command: " + command + ", Argument: " + argument + " is invalid or it is not in the correct place!";
    }
}
