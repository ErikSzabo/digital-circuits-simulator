package hu.erik.digitalcircuits.devices;


import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinAlreadyInUseException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;

import java.io.Serializable;

/**
 * Interface to represent functionalities of a Device.
 */
public interface Device extends Serializable {
    /**
     * Returns all of the input pins on the device.
     *
     * @return all input pins on the device
     */
    Pin[] inputPins();

    /**
     * Returns all of the output pins on the device.
     *
     * @return all output pins on the device
     */
    Pin[] outputPins();

    /**
     * Calculates and sets the Device OutputPin values.
     */
    void calcOutput();

    /**
     * Sends output pin values to the connected pins.
     */
    void sendOutput();

    /**
     * Connects this device next free output pin to the
     * target Device next free input pin.
     *
     * @param device                target device for the connection
     * @return                      target device
     * @throws NoMorePinException   If there isn't any more free pins on any of the devices.
     */
    Device connect(Device device) throws NoMorePinException;

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
    Device connect(Device device, int targetInputIndex) throws PinAlreadyInUseException, NoMorePinException, PinNotExistsException;

    /**
     * Connects this device next free output pin to the
     * required input pin on the target device.
     *
     * @param device                    target device for the connection
     * @param outputIndex               index of the current device output pin
     * @param targetInputIndex          index of the target device input pin
     * @return                          target device
     * @throws PinAlreadyInUseException If the target pin is already in use.
     * @throws PinNotExistsException    If there aren't any pin at the given index.
     */
    Device connect(Device device, int outputIndex, int targetInputIndex) throws PinAlreadyInUseException, PinNotExistsException;


    /**
     * Disconnects this device output pins from the
     * target device input pins.
     *
     * @param device target device we disconnect from
     */
    void disconnect(Device device);

}
