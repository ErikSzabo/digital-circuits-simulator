package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.devices.DeviceType;
import hu.erik.digitalcircuits.devices.*;
import hu.erik.digitalcircuits.errors.clierror.*;
import hu.erik.digitalcircuits.utils.Printer;

public class CreateCmd extends Command {
    public CreateCmd(String name) {
        super(name);
    }

    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        // FORMAT:
        // create <type> <name> [inputnum] [outputnum]

        if(cmd.length < 3) throw new NotEnoughArgsException(cmd[0], 3, cmd.length - 1);
        if(cmd.length > 5) Printer.printErr(new TooManyArgumentException(cmd[0]));

        String type, name;

        name = cmd[2];
        type = cmd[1].toLowerCase();

        try {
            switch (type) {
                case DeviceType.SWITCH:
                    storage.add(name, new Switch());
                    break;
                case DeviceType.POWER:
                    storage.add(name, new PowerSource());
                    break;
                case DeviceType.ORGATE:
                    storage.add(name, new OrGate(Integer.parseInt(cmd[3])));
                    break;
                case DeviceType.NORGATE:
                    storage.add(name, new NorGate(Integer.parseInt(cmd[3])));
                    break;
                case DeviceType.NANDGATE:
                    storage.add(name, new NandGate(Integer.parseInt(cmd[3])));
                    break;
                case DeviceType.JUNCTION:
                    storage.add(name, new Junction(Integer.parseInt(cmd[3])));
                    break;
                case DeviceType.INVERTER:
                    storage.add(name, new Inverter());
                    break;
                case DeviceType.CIRCUITBOX:
                    storage.add(name, new CircuitBox(name, Integer.parseInt(cmd[3]), Integer.parseInt(cmd[4])));
                    break;
                case DeviceType.ANDGATE:
                    storage.add(name, new AndGate(Integer.parseInt(cmd[3])));
                    break;
                default:
                    throw new InvalidDeviceTypeException(type);
            }
            Printer.println("Device added!");
        } catch (RedundantKeyException | NullDeviceException | InvalidDeviceTypeException err) {
            Printer.printErr(err);
        } catch (NumberFormatException | IndexOutOfBoundsException err) {
            Printer.printErr(new Exception("Invalid or missing parameters!"));
        }
    }
}
