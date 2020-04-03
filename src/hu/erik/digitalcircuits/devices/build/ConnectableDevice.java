package hu.erik.digitalcircuits.devices.build;


import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinAlreadyInUseException;
import hu.erik.digitalcircuits.errors.PinNotExists;

public abstract class ConnectableDevice implements Device {

    /**
     * Connects this device next free output pin to the
     * target Device next free input pin.
     *
     * @param device Target device for the connection
     * @return Target device
     */
    @Override
    public Device connect(Device device) {
        try {
            new Cable(getFreeOutputPin(), device.getFreeInputPin());
        } catch (NoMorePinException err) {
            System.err.println(err.getMessage());
        }
        return device;
    }

    /**
     * Connects this device next free output pin to the
     * required input pin on the target device.
     *
     * @param device           Target device for the connection
     * @param targetInputIndex The index of the target device input pin
     * @return Target device
     */
    @Override
    public Device connect(Device device, int targetInputIndex) {
        try {
            Pin outputPin = getFreeOutputPin();
            Pin targetInputPin = device.getInputPin(targetInputIndex);
            new Cable(outputPin, targetInputPin);
        } catch(NoMorePinException | PinAlreadyInUseException | PinNotExists err) {
            System.err.println(err.getMessage());
        }
        return device;
    }

    /**
     * Connects this device specified output pin to the
     * required input pin on the target device.
     *
     * @param device           Target device for the connection
     * @param outputIndex      The output pin index of this device
     * @param targetInputIndex The index of the target device input pin
     * @return Target device
     */
    @Override
    public Device connect(Device device, int outputIndex, int targetInputIndex) {
        try {
            Pin outputPin = getOutputPin(outputIndex);
            Pin targetInputPin = device.getInputPin(targetInputIndex);
            new Cable(outputPin, targetInputPin);
        } catch (PinAlreadyInUseException | PinNotExists err) {
            System.err.println(err.getMessage());
        }
        return device;
    }

    /**
     * Transfers value from a pin to another pin if it is possible.
     *
     * @param outputPin OutputPin you want to transfer the value from
     */
    protected void transferValue(Pin outputPin) {
        if(outputPin.isFree()) return;
        Pin connectedPin = outputPin.getConnectionCable().getOtherPin(outputPin);
        connectedPin.setValue(outputPin.getValue());
        Device device = connectedPin.getParentDevice();
        device.calcOutput();
        device.sendOutput();
    }

}
