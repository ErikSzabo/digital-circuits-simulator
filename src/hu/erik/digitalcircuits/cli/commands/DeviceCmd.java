package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.cli.DeviceType;
import hu.erik.digitalcircuits.errors.clierror.NotEnoughArgsException;

public class DeviceCmd extends Command {

    public DeviceCmd(String name) {
        super(name);
    }

    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        // Format
        // <devicetype> <name> <uniqe method> <args...>

        if(cmd.length < 3) throw new NotEnoughArgsException(cmd[0], 4, cmd.length - 1);

        switch (cmd[0]) {
            case DeviceType.JUNCTION:
                break;
            case DeviceType.CIRCUITBOX:
                break;
            case DeviceType.SWITCH:
                break;
            case DeviceType.POWER:
                break;
            default:
                break;
        }
    }
}
