package hu.erik.digitalcircuits.errors;


public class TooManyArgumentException extends CliException {

    public TooManyArgumentException(String cmd) {
        super(cmd);
    }

    @Override
    public String getMessage() {
        return "For command: " + getCmdOrName() + ", there are too many arguments specified! Extra args will be ignored!";
    }
}
