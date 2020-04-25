package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.cli.DeviceType;
import hu.erik.digitalcircuits.errors.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.Printer;

/**
 * Class to handle command prefixed with "devicetypes".
 */
public class DeviceTypesCmd extends Command {

    /**
     * Constructor to setup the command's name.
     *
     * @param name Name of the command.
     */
    public DeviceTypesCmd(String name) {
        super(name);
    }

    /**
     * Lists the available device types.
     *
     * Command format:
     * devicetypes
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) {
        if(cmd.length > 1) Printer.printErr(new TooManyArgumentException(cmd[0]));

        for(DeviceType type : DeviceType.values()) {
            Printer.println(type.getValue());
        }
    }
}
