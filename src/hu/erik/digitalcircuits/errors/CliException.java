package hu.erik.digitalcircuits.errors;

public abstract class CliException extends Exception {
    private String cmdOrName;

    public CliException(String cmd) {
        this.cmdOrName = cmd;
    }

    public String getCmdOrName() {
        return cmdOrName;
    }

    @Override
    public abstract String getMessage();

}
