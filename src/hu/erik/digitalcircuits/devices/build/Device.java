package hu.erik.digitalcircuits.devices.build;


import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.PinAlreadyInUseException;
import hu.erik.digitalcircuits.errors.PinNotExists;

import java.io.Serializable;

/**
 * Interface to create Devices for
 * digital circuits.
 */
public interface Device extends Serializable {
    /**
     * Calculate and set the Device OutputPin values based on it's input pins.
     */
    void calcOutput();

    /**
     * Method to find an unconnected input pin on the Device
     *
     * @return Pin or null if there isn't a free input pin
     * @throws NoMorePinException If the device doesn't have any more free input pins.
     */
    Pin getFreeInputPin() throws NoMorePinException;

    /**
     * Method to find an unconnected output pin on the Device
     *
     * @return Pin or null if there isn't a free output pin
     * @throws NoMorePinException If the device doesn't have any more free output pins.
     */
    Pin getFreeOutputPin() throws NoMorePinException;

    /**
     * Method to get a specified input pin.
     *
     * @param index Index of the required input pin.
     * @return Pin or null if there isn't any pin at the given index.
     * @throws PinAlreadyInUseException If the required pin is already connected to something
     * @throws PinNotExists If the selected pin is not exists
     */
    Pin getInputPin(int index) throws PinAlreadyInUseException, PinNotExists;

    /**
     * Method to get a specified output pin.
     *
     * @param index Index of the required output pin
     * @return Pin or null if there isn't any pin at the given index
     * @throws PinAlreadyInUseException If the required pin is already connected to something
     * @throws PinNotExists If the selected pin is not exists
     */
    Pin getOutputPin(int index) throws PinAlreadyInUseException, PinNotExists;

    /**
     * Connects this device next free output pin to the
     * target Device next free input pin.
     *
     * @param device Target device for the connection
     * @return Target device
     */
    Device connect(Device device);

    /**
     * Connects this device next free output pin to the
     * required input pin on the target device.
     *
     * @param device           Target device for the connection
     * @param targetInputIndex The index of the target device input pin
     * @return Target device
     */
    Device connect(Device device, int targetInputIndex);

    /**
     * Connects this device specified output pin to the
     * required input pin on the target device.
     *
     * @param device           Target device for the connection
     * @param outputIndex      The output pin index of this device
     * @param targetInputIndex The index of the target device input pin
     * @return Target device
     */
    Device connect(Device device, int outputIndex, int targetInputIndex);

    /**
     * This method should take care of signal transfer and auto update
     */
    void sendOutput();

}
