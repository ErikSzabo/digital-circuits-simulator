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
     * Adds a device to the data structure.
     *
     * @param name                      name of the device
     * @param device                    the device which will be added
     * @throws RedundantKeyException    If the device already exists.
     */
    public void add(String name, Device device) throws RedundantKeyException {
        if(map.get(name) != null) throw new RedundantKeyException(name);
        map.put(name, device);
    }

    /**
     * Removes the device with the give name from the data structure.
     *
     * @param name                      name of the device which will be removed
     * @throws DeviceNotExistsException If there isn't any mapping for the device.
     */
    public void remove(String name) throws DeviceNotExistsException {
        Device d = map.remove(name);
        if(d == null) throw new DeviceNotExistsException(name);
    }

    /**
     * Gets the device with the given name from the data structure if it exists.
     *
     * @param name                      name of the device
     * @return                          the device
     * @throws DeviceNotExistsException If there isn't any mapping for the device.
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
     * @return unmodifiable data structure/map
     */
    public Map<String, Device> getMap() {
        return Collections.unmodifiableMap(map);
    }

}
