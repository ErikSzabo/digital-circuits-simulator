package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.devices.Device;
import hu.erik.digitalcircuits.errors.*;
import hu.erik.digitalcircuits.utils.Printer;

/**
 * Class to handle commands prefixed with "show".
 */
public class ShowCmd extends Command {

    /**
     * Constructor to setup the command's name.
     *
     * @param name Name of the command.
     */
    public ShowCmd(String name) {
        super(name);
    }

    /**
     * Shows information about a pin on a device at the given index.
     *
     * Command format:
     * create {@literal <}output | input{@literal >} {@literal <}name{@literal >} {@literal <}pin index{@literal >}
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 3.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        // FORMAT
        // show <output | input> <name> <index>

        if(cmd.length < 4) throw new NotEnoughArgsException(cmd[0], 3, cmd.length - 1);
        if(cmd.length > 4) Printer.printErr(new TooManyArgumentException(cmd[0]));

        try {
            Device device = storage.get(cmd[2]);

            switch (cmd[1]) {
                case "input":
                    Printer.println(cmd[2] + " inputpin(" + cmd[3] +"): " + device.getInputPin(Integer.parseInt(cmd[3])).getValue());
                    break;
                case "output":
                    Printer.println(cmd[2] + " outputpin(" + cmd[3] +"): " + device.getInputPin(Integer.parseInt(cmd[3])).getValue());
                    break;
                default:
                    Printer.printErr(new InvalidArgumentException(cmd[0], cmd[1]));
            }
        } catch (DeviceNotExistsException | PinNotExistsException  err) {
            Printer.printErr(err);
        } catch (NumberFormatException err) {
            Printer.printErr("Pin index must be number! (counting starts at 0)");
        }


    }
}
