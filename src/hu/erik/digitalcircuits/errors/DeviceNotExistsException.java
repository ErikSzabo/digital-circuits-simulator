package hu.erik.digitalcircuits.errors;

public class DeviceNotExistsException extends CliException {

    public DeviceNotExistsException(String name) {
        super(name);
    }

    @Override
    public String getMessage() {
        return "There isn't any device with this name: " + getCmdOrName();
    }
}
