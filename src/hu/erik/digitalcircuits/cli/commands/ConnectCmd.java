package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.devices.Device;
import hu.erik.digitalcircuits.errors.clierror.DeviceNotExistsException;
import hu.erik.digitalcircuits.errors.clierror.NotEnoughArgsException;
import hu.erik.digitalcircuits.utils.Printer;

public class ConnectCmd extends Command{
    public ConnectCmd(String name) {
        super(name);
    }

    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        // FORMAT
        // connect <name> to <name>
        // This will only use the easiest connect method
        if(cmd.length < 4) throw new NotEnoughArgsException(cmd[0], 3, cmd.length - 1);

        try {
            Device d1 = storage.get(cmd[1]);
            Device d2 = storage.get(cmd[3]);
            d1.connect(d2);
            Printer.println("Connected!");
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
        }

    }
}
