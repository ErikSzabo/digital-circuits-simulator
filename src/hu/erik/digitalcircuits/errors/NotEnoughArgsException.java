package hu.erik.digitalcircuits.errors;


public class NotEnoughArgsException extends CliException {
    private int reqArgNum;
    private int givenArgNum;

    public NotEnoughArgsException(String cmd, int reqArgNum, int givenArgNum) {
        super(cmd);
        this.reqArgNum = reqArgNum;
        this.givenArgNum = givenArgNum;
    }

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
