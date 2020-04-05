package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.cli.DeviceType;
import hu.erik.digitalcircuits.errors.clierror.InvalidArgumentException;
import hu.erik.digitalcircuits.errors.clierror.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.Printer;

public class ListCmd extends Command {
    public ListCmd(String name) {
        super(name);
    }

    @Override
    public void action(DeviceMap storage, String[] cmd) {
        // FORMAT:
        // list [type]

        if(cmd.length > 2) Printer.printErr(new TooManyArgumentException(cmd[0]));

        if(cmd.length == 1) {
            for(String name : storage.keySet()) {
                Printer.println("Name: " + name + "\tType: " + storage.getDeviceType(name));
            }
        } else {
            if(!DeviceType.ALL.contains(cmd[1])) {
                Printer.printErr(new InvalidArgumentException(cmd[0], cmd[1]));
                return;
            }
            for(String name : storage.keySet()) {
                String type = storage.getDeviceType(name);
                if(type.equalsIgnoreCase(cmd[1])) {
                    Printer.println("Name: " + name + "\tType: " + type);
                }
            }
        }

    }
}
