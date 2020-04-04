package hu.erik.digitalcircuits.errors.clierror;

public class NullDeviceException extends Exception {
    @Override
    public String getMessage() {
        return "You can't add a not existing device!";
    }
}
