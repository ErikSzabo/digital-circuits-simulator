package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.devices.Device;
import hu.erik.digitalcircuits.errors.DeviceNotExistsException;
import hu.erik.digitalcircuits.errors.RedundantKeyException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for storing user created devices.
 */
public class DeviceMap {
    private HashMap<String , Device> map;

    /**
     * Default constructor for initializing.
     */
    public DeviceMap() {
        this.map = new HashMap<>();
    }

    /**
     * Add a device to the data structure.
     *
     * @param name                      Name of the device
     * @param device                    The device you want to add
     * @throws RedundantKeyException    When the device is already exists
     */
    public void add(String name, Device device) throws RedundantKeyException {
        if(map.get(name) != null) throw new RedundantKeyException(name);
        map.put(name, device);
    }

    /**
     * Remove a device from the data structure
     *
     * @param name                      Name of the device
     * @throws DeviceNotExistsException When there isn't any mapping for the device
     */
    public void remove(String name) throws DeviceNotExistsException {
        Device d = map.remove(name);
        if(d == null) throw new DeviceNotExistsException(name);
    }

    /**
     * Get a device from the data structure.
     *
     * @param name                      Name of the device
     * @return                          The device
     * @throws DeviceNotExistsException When there isn't any mapping for the device
     */
    public Device get(String name) throws DeviceNotExistsException {
        Device device = map.get(name);
        if(device == null) throw new DeviceNotExistsException(name);
        return device;
    }

    /**
     * Returns the full device map in an unmodifiable format.
     * Useful for iterations.
     *
     * @return The unmodifiable data structure
     */
    public Map<String, Device> getMap() {
        return Collections.unmodifiableMap(map);
    }

}
