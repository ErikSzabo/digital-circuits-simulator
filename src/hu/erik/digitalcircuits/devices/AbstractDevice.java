package hu.erik.digitalcircuits.devices;


import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinAlreadyInUseException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;
import hu.erik.digitalcircuits.utils.Printer;

/**
 * Abstract class for Device which implements the connection methods.
 */
public abstract class AbstractDevice implements Device {

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
        Pin outputPin = findFreePin(outputPins());
        Pin inputPin = findFreePin(device.inputPins());
        if(outputPin == null) throw new NoMorePinException(this, "output");
        if(inputPin == null) throw new NoMorePinException(device, "input");
        new Cable(outputPin, inputPin);
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
        Pin outputPin = findFreePin(outputPins());
        if(outputPin == null) throw new NoMorePinException(this, "output");

        if(targetInputIndex > device.inputPins().length - 1) throw new PinNotExistsException(device, targetInputIndex);
        Pin targetInputPin = device.inputPins()[targetInputIndex];
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
        if(outputIndex > outputPins().length - 1) throw new PinNotExistsException(this, outputIndex);
        Pin outputPin = outputPins()[outputIndex];
        if(!outputPin.isFree()) throw new PinAlreadyInUseException(this, targetInputIndex);

        if(targetInputIndex > device.inputPins().length - 1) throw new PinNotExistsException(device, targetInputIndex);
        Pin targetInputPin = device.inputPins()[targetInputIndex];
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
        Pin[] outputPins = outputPins();
        for(Pin p : outputPins) {
            if(p.isFree()) continue;
            Pin targetPin = p.getConnectionCable().getOtherPin(p);
            Device targetDevice = targetPin.getParentDevice();
            if(targetDevice == device) {
                p.setConnectionCable(null);
                p.setAvailability(true);
                targetPin.setConnectionCable(null);
                targetPin.setSignal(false);
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
        for(Pin outputPin : outputPins()) {
            transferValue(outputPin);
        }
    }

    /**
     * Transfers value from a pin to another pin if it is possible.
     *
     * @param outputPin outputPin you want to transfer the value from
     */
    private void transferValue(Pin outputPin) {
        try {
            if(outputPin.isFree()) return;
            Pin connectedPin = outputPin.getConnectionCable().getOtherPin(outputPin);
            connectedPin.setSignal(outputPin.getSignal());
            Device device = connectedPin.getParentDevice();
            device.calcOutput();
            device.sendOutput();
        } catch (StackOverflowError err) {
            // "protection" against oscillation
            Printer.printErr("Oscillation occurred in your circuit. Signal transfers are stopped!");
            Printer.printErr("Please fix your circuit!");
        }
    }

    /**
     * Returns a free pin or null if all of the pins are already connected to a device.
     *
     * @param pins  pins to search in
     * @return      free pin or null if there isn't any
     */
    private Pin findFreePin(Pin[] pins) {
        for(Pin pin : pins) {
            if(pin.isFree()) return pin;
        }
        return null;
    }

}
