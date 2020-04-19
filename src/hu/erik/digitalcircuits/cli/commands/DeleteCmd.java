package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.errors.DeviceNotExistsException;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;
import hu.erik.digitalcircuits.errors.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.Printer;

public class DeleteCmd extends Command {
    public DeleteCmd(String name) {
        super(name);
    }

    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        // FORMAT
        // delete <name>
        if(cmd.length < 2) throw new NotEnoughArgsException(cmd[0], 1, 0);
        if(cmd.length > 2) Printer.printErr(new TooManyArgumentException(cmd[0]));

        try {
            storage.remove(cmd[1]);
            Printer.println("Device has been deleted!");
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
        }
    }
}
