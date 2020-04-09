package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.cli.DeviceType;
import hu.erik.digitalcircuits.errors.clierror.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.Printer;

public class DeviceTypesCmd extends Command {
    public DeviceTypesCmd(String name) {
        super(name);
    }

    @Override
    public void action(DeviceMap storage, String[] cmd) {
        if(cmd.length > 1) Printer.printErr(new TooManyArgumentException(cmd[0]));

        for(String type : DeviceType.ALL) {
            System.out.println(type);
        }
    }
}
