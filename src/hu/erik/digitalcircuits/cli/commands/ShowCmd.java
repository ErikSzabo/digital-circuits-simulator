package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.devices.Device;
import hu.erik.digitalcircuits.errors.PinNotExistsException;
import hu.erik.digitalcircuits.errors.DeviceNotExistsException;
import hu.erik.digitalcircuits.errors.InvalidArgumentException;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;
import hu.erik.digitalcircuits.utils.Printer;

public class ShowCmd extends Command {
    public ShowCmd(String name) {
        super(name);
    }

    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        // FORMAT
        // show <output | input> <name> <index>

        if(cmd.length < 4) throw new NotEnoughArgsException(cmd[0], 3, cmd.length - 1);

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
