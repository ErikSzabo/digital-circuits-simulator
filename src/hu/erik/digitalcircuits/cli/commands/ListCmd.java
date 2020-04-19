package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.devices.Device;
import hu.erik.digitalcircuits.devices.DeviceType;
import hu.erik.digitalcircuits.errors.DeviceNotExistsException;
import hu.erik.digitalcircuits.errors.InvalidArgumentException;
import hu.erik.digitalcircuits.errors.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.Printer;

import java.util.HashMap;

public class ListCmd extends Command {
    public ListCmd(String name) {
        super(name);
    }

    @Override
    public void action(DeviceMap storage, String[] cmd) {
        // FORMAT:
        // list [type]

        if(cmd.length > 2) Printer.printErr(new TooManyArgumentException(cmd[0]));

        HashMap<String, Device> devices = storage.getMap();

        if(cmd.length == 1) {
            for(String name : devices.keySet()) {
                Printer.println("[Name]: " + name + ", [Type]: " + devices.get(name));
            }
        } else {
            if(!DeviceType.ALL.contains(cmd[1])) {
                Printer.printErr(new InvalidArgumentException(cmd[0], cmd[1]));
                return;
            }
            for(String name : devices.keySet()) {
                String type = devices.get(name).toString();
                if(type.equalsIgnoreCase(cmd[1])) {
                    Printer.println("[Name]: " + name + ", [Type]: " + type);
                }
            }
        }
        Printer.println("Done!");

    }
}
