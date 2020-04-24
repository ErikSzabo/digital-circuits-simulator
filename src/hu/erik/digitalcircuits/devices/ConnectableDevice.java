package hu.erik.digitalcircuits.devices;


import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinAlreadyInUseException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;

/**
 * Abstract class for Device which implements the connection methods.
 */
public abstract class ConnectableDevice implements Device {

    /**
     * Connects this device next free output pin to the
     * target Device next free input pin.
     *
     * @param device                target device for the connection
     * @return                      target device
     * @throws NoMorePinException   If there isn't any more free pins on any of the devices.
     */
    @Override
    public Device connect(Device device) throws NoMorePinException {
        Pin outputPin = getFreeOutputPin();
        new Cable(outputPin, device.getFreeInputPin());
        transferValue(outputPin);
        return device;
    }

    /**
     * Connects this device next free output pin to the
     * required input pin on the target device.
     *
     * @param device                    target device for the connection
     * @param targetInputIndex          index of the target device input pin
     * @return                          target device
     * @throws PinAlreadyInUseException If the target pin is already in use.
     * @throws NoMorePinException       If there isn't any more free pins on any of the devices.
     * @throws PinNotExistsException    If there aren't any pin at the given index.
     */
    @Override
    public Device connect(Device device, int targetInputIndex) throws PinAlreadyInUseException, NoMorePinException, PinNotExistsException {
        Pin outputPin = getFreeOutputPin();
        Pin targetInputPin = device.getInputPin(targetInputIndex);
        if(!targetInputPin.isFree()) throw new PinAlreadyInUseException(device, targetInputIndex);
        new Cable(outputPin, targetInputPin);
        transferValue(outputPin);
        return device;
    }

    /**
     * Connects this device next free output pin to the
     * required input pin on the target device.
     *
     * @param device                    target device for the connection
     * @param targetInputIndex          index of the target device input pin
     * @return                          target device
     * @throws PinAlreadyInUseException If the target pin is already in use.
     * @throws PinNotExistsException    If there aren't any pin at the given index.
     */
    @Override
    public Device connect(Device device, int outputIndex, int targetInputIndex) throws PinAlreadyInUseException, PinNotExistsException {
        Pin outputPin = getOutputPin(outputIndex);
        Pin targetInputPin = device.getInputPin(targetInputIndex);
        if(!outputPin.isFree()) throw new PinAlreadyInUseException(this, outputIndex);
        if(!targetInputPin.isFree()) throw new PinAlreadyInUseException(device, targetInputIndex);
        new Cable(outputPin, targetInputPin);
        transferValue(outputPin);
        return device;
    }

    /**
     * Disconnects this device output pins from the
     * target device input pins.
     *
     * @param device the device we disconnect from
     */
    @Override
    public void disconnect(Device device) {
        Pin[] outputPins = getAllOutputPins();
        for(Pin p : outputPins) {
            if(p.isFree()) continue;
            Pin targetPin = p.getConnectionCable().getOtherPin(p);
            Device targetDevice = targetPin.getParentDevice();
            if(targetDevice == device) {
                p.setConnectionCable(null);
                p.setAvailability(true);
                targetPin.setConnectionCable(null);
                targetPin.setValue(false);
                targetPin.setAvailability(true);
                targetDevice.calcOutput();
                targetDevice.sendOutput();
            }
        }
    }

    /**
     * Sends output pin values to the connected pins.
     */
    @Override
    public void sendOutput() {
        for(Pin outputPin : getAllOutputPins()) {
            transferValue(outputPin);
        }
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
