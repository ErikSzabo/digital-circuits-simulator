package hu.erik.digitalcircuits.errors.clierror;

public class RedundantKeyException extends Exception {
    private String key;

    public RedundantKeyException(String key) {
        this.key = key;
    }

    @Override
    public String getMessage() {
        return "Device with name: " + key + " already exists!";
    }
}
