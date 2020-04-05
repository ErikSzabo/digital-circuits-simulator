package hu.erik.digitalcircuits.errors.clierror;


public class NotEnoughArgsException extends Exception {
    private String cmd;
    private int reqArgNum;
    private int givenArgNum;

    public NotEnoughArgsException(String cmd, int reqArgNum, int givenArgNum) {
        this.cmd = cmd;
        this.reqArgNum = reqArgNum;
        this.givenArgNum = givenArgNum;
    }

    @Override
    public String getMessage() {
        return "In command: " +
                cmd +
                ", You only specified " +
                givenArgNum +
                " argument(s). You have to specify at least " +
                reqArgNum +
                " argument!";
    }
}
