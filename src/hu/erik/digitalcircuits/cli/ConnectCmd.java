package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.devices.Device;
import hu.erik.digitalcircuits.errors.DeviceNotExistsException;
import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;
import hu.erik.digitalcircuits.errors.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.Printer;

/**
 * Class to handle commands prefixed with "connect".
 */
public class ConnectCmd extends Command{

    /**
     * Constructor to setup the command's name, format and description.
     */
    public ConnectCmd() {
        super(
                "connect",
                "connect <name> to <name>",
                "Connects the first device first free output pin to the second device first free input pin."
        );
    }

    /**
     * Connects the two devices if both of them exists.<br>
     *
     * Command format:<br>
     * connect {@literal <}name{@literal >} to {@literal <}name{@literal >}
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
            Device d1 = storage.get(cmd[1]).getDevice();
            Device d2 = storage.get(cmd[3]).getDevice();
            d1.connect(d2);
            Printer.println("Connected!");
        } catch (DeviceNotExistsException | NoMorePinException err) {
            Printer.printErr(err);
        }
    }
}
