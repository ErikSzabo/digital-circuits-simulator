package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceBundle;
import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.cli.DeviceType;
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
     */
    public ListCmd() {
        super(
                "list",
                "list [type]",
                "Lists all available device type."
        );
    }

    /**
     * Lists the name and type of the created devices.
     * It supports filtering by device type.<br>
     *
     * Command format:<br>
     * list [type]
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws InvalidArgumentException {
        if(cmd.length == 2 && !DeviceType.contains(cmd[1].toLowerCase())) throw new InvalidArgumentException(cmd[0], cmd[1]);

        if(cmd.length > 2) Printer.printErr(new TooManyArgumentException(cmd[0]));

        if(cmd.length == 1) {
            listAll(storage);
        } else {
            listFiltered(storage, cmd[1].toLowerCase());
        }

    }

    /**
     * Lists all of the current devices with their name and type.
     *
     * @param storage    cli data structure
     */
    private void listAll(DeviceMap storage) {
        Map<String, DeviceBundle> devices = storage.getMap();

        for(String name : devices.keySet()) {
            Printer.println("[Name]: " + name + ", [Type]: " + devices.get(name).getType());
        }

        Printer.println("Done!");
    }

    /**
     * Lists all of the current devices with their name and type if the
     * given type is equals to the device type.
     *
     * @param storage   cli data structure
     * @param type      filter criteria
     */
    private void listFiltered(DeviceMap storage, String type) {
        Map<String, DeviceBundle> devices = storage.getMap();

        for(String name : devices.keySet()) {
            String currentType = devices.get(name).getType();
            if(currentType.equals(type)) {
                Printer.println("[Name]: " + name + ", [Type]: " + type);
            }
        }

        Printer.println("Done!");
    }
}
