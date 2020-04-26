package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.errors.DeviceNotExistsException;
import hu.erik.digitalcircuits.errors.RedundantKeyException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Data structure for storing user created devices.
 */
public class DeviceMap {
    /**
     * Stores user created devices where the key is the device name,
     * and the DeviceBundle is the created Device with it's type.
     */
    private HashMap<String , DeviceBundle> map;

    /**
     * Default constructor for initializing.
     */
    public DeviceMap() {
        this.map = new HashMap<>();
    }

    /**
     * Adds a device to the data structure.
     *
     * @param name                      name of the device
     * @param bundle                    the device and it's type which will be added
     * @throws RedundantKeyException    If the device already exists.
     */
    public void add(String name, DeviceBundle bundle) throws RedundantKeyException {
        if(map.get(name) != null) throw new RedundantKeyException(name);
        map.put(name, bundle);
    }

    /**
     * Removes the device with the given name from the data structure.
     *
     * @param name                      name of the device which will be removed
     * @throws DeviceNotExistsException If there isn't any mapping for the device.
     */
    public void remove(String name) throws DeviceNotExistsException {
        DeviceBundle d = map.remove(name);
        if(d == null) throw new DeviceNotExistsException(name);
    }

    /**
     * Gets the device with the given name from the data structure if it exists.
     *
     * @param name                      name of the device
     * @return                          the device with it's type
     * @throws DeviceNotExistsException If there isn't any mapping for the device.
     */
    public DeviceBundle get(String name) throws DeviceNotExistsException {
        DeviceBundle device = map.get(name);
        if(device == null) throw new DeviceNotExistsException(name);
        return device;
    }

    /**
     * Returns the full device map in an unmodifiable format.
     * Useful for iterations.
     *
     * @return unmodifiable data structure/map
     */
    public Map<String, DeviceBundle> getMap() {
        return Collections.unmodifiableMap(map);
    }

}
