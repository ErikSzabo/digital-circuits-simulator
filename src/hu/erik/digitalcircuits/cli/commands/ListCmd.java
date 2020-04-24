package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.devices.Device;
import hu.erik.digitalcircuits.devices.DeviceType;
import hu.erik.digitalcircuits.errors.InvalidArgumentException;
import hu.erik.digitalcircuits.errors.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.Printer;

import java.util.Map;

/**
 * Class to handle commands prefixed with "list".
 */
public class ListCmd extends Command {

    /**
     * Constructor to setup the command's name.
     *
     * @param name Name of the command.
     */
    public ListCmd(String name) {
        super(name);
    }

    /**
     * Lists the name and type of the created devices.
     * It supports filtering by device type.
     *
     * Command format:
     * list [type]
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) {
        if(cmd.length > 2) Printer.printErr(new TooManyArgumentException(cmd[0]));

        Map<String, Device> devices = storage.getMap();

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
