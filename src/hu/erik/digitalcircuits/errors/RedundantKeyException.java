package hu.erik.digitalcircuits.errors;

public class RedundantKeyException extends CliException {

    public RedundantKeyException(String key) {
        super(key);
    }

    @Override
    public String getMessage() {
        return "Device with name: " + getCmdOrName() + " already exists!";
    }
}
