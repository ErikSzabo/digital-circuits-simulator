package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.devices.Device;
import hu.erik.digitalcircuits.errors.DeviceNotExistsException;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;
import hu.erik.digitalcircuits.errors.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.Printer;

/**
 * Class to handle commands prefixed with "disconnect".
 */
public class DisconnectCmd extends Command {

    /**
     * Constructor to setup the command's name.
     *
     * @param name Name of the command.
     */
    public DisconnectCmd(String name) {
        super(name);
    }

    /**
     * Fully disconnects the two given device.
     *
     * Command format:
     * disconnect {@literal <}name{@literal >} from {@literal <}name{@literal >}
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 3.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        if(cmd.length < 4) throw new NotEnoughArgsException(cmd[0], 3, cmd.length - 1);
        if(cmd.length > 4) Printer.printErr(new TooManyArgumentException(cmd[0]));

        try {
            Device d1 = storage.get(cmd[1]);
            Device d2 = storage.get(cmd[3]);
            d1.disconnect(d2);
            Printer.println("Disconnected!");
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
        }
    }
}
