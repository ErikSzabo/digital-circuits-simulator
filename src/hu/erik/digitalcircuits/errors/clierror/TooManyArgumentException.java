package hu.erik.digitalcircuits.errors.clierror;


public class TooManyArgumentException extends Exception {
    private String cmd;

    public TooManyArgumentException(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String getMessage() {
        return "For command: " + cmd + ", there are too many arguments specified! Extra args will be ignored!";
    }
}
