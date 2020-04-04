package hu.erik.digitalcircuits.errors.clierror;

public class DeviceNotExistsException extends Exception {
    private String name;

    public DeviceNotExistsException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "There isn't any device with this name: " + name;
    }
}
