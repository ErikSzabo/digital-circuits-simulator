package hu.erik.digitalcircuits.errors.clierror;

public class InvalidDeviceTypeException extends Exception {
    private String type;

    public InvalidDeviceTypeException(String type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "Device type: " + type + " does not exists!";
    }
}
