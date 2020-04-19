package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.devices.Device;
import hu.erik.digitalcircuits.errors.clierror.DeviceNotExistsException;
import hu.erik.digitalcircuits.errors.clierror.NullDeviceException;
import hu.erik.digitalcircuits.errors.clierror.RedundantKeyException;

import java.util.HashMap;
import java.util.Set;

public class DeviceMap {
    private HashMap<String , Device> map;

    public DeviceMap() {
        this.map = new HashMap<>();
    }

    public void add(String name, Device device) throws RedundantKeyException, NullDeviceException {
        if(map.get(name) != null) throw new RedundantKeyException(name);
        if(device == null) throw new NullDeviceException();
        map.put(name, device);
    }

    public void remove(String name) throws DeviceNotExistsException {
        Device d = map.remove(name);
        if(d == null) throw new DeviceNotExistsException(name);
    }

    public Device get(String name) throws DeviceNotExistsException {
        Device device = map.get(name);
        if(device == null) throw new DeviceNotExistsException(name);
        return device;
    }

    public String getDeviceType(String name) {
        return map.get(name).toString();
    }

    public Set<String> keySet() {
        return map.keySet();
    }
}
