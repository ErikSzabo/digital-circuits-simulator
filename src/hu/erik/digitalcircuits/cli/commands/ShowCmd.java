package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.devices.build.Device;
import hu.erik.digitalcircuits.errors.PinNotExistsException;
import hu.erik.digitalcircuits.errors.clierror.DeviceNotExistsException;
import hu.erik.digitalcircuits.errors.clierror.InvalidArgumentException;
import hu.erik.digitalcircuits.errors.clierror.NotEnoughArgsException;
import hu.erik.digitalcircuits.errors.clierror.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.Printer;

public class ShowCmd extends Command {
    public ShowCmd(String name) {
        super(name);
    }

    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        // FORMAT
        // show <output | input> <name> <index>

        if(cmd.length < 4) throw new NotEnoughArgsException(cmd[0], 4, cmd.length - 1);

        try {
            Device device = storage.get(cmd[2]);

            switch (cmd[1]) {
                case "input":
                    Printer.println(cmd[2] + " inputpin(" + cmd[3] +"): " + device.getInputPin(Integer.parseInt(cmd[3])));
                    break;
                case "output":
                    Printer.println(cmd[2] + " outputpin(" + cmd[3] +"): " + device.getInputPin(Integer.parseInt(cmd[3])));
                    break;
                default:
                    throw new InvalidArgumentException(cmd[0], cmd[1]);
            }
        } catch (InvalidArgumentException | DeviceNotExistsException | PinNotExistsException | NumberFormatException err) {
            Printer.printErr(err);
        }


    }
}
