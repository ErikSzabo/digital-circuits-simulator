package hu.erik.digitalcircuits.devices;


import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinAlreadyInUseException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;
import hu.erik.digitalcircuits.utils.Printer;

/**
 * Abstract class for Device which implements the connection methods
 */
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
            Pin outputPin = getFreeOutputPin();
            new Cable(outputPin, device.getFreeInputPin());
            transferValue(outputPin);
        } catch (NoMorePinException err) {
            Printer.printErr(err);
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
            if(!targetInputPin.isFree()) throw new PinAlreadyInUseException(device, targetInputIndex);
            new Cable(outputPin, targetInputPin);
            transferValue(outputPin);
        } catch(NoMorePinException | PinAlreadyInUseException | PinNotExistsException err) {
            Printer.printErr(err);
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
            if(!outputPin.isFree()) throw new PinAlreadyInUseException(this, outputIndex);
            if(!targetInputPin.isFree()) throw new PinAlreadyInUseException(device, targetInputIndex);
            new Cable(outputPin, targetInputPin);
            transferValue(outputPin);
        } catch (PinAlreadyInUseException | PinNotExistsException err) {
            Printer.printErr(err);
        }
        return device;
    }

    /**
     * Disconnects this device all output pins from the
     * target device all input pins.
     *
     * @param device    The device we disconnect from
     */
    @Override
    public void disconnect(Device device) {
        Pin[] outputPins = getAllOutputPins();
        for(Pin p : outputPins) {
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
