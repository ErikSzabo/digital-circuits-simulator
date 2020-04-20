package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.devices.*;
import hu.erik.digitalcircuits.errors.DeviceNotExistsException;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;
import hu.erik.digitalcircuits.errors.RedundantKeyException;
import hu.erik.digitalcircuits.utils.FileHandler;
import hu.erik.digitalcircuits.utils.Printer;

import java.util.ArrayList;

public class DeviceCmd extends Command {

    public DeviceCmd(String name) {
        super(name);
    }

    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        // Format
        // <devicetype> <name> <uniqe method> <args...>

        if(cmd.length < 3) throw new NotEnoughArgsException(cmd[0], 4, cmd.length - 1);

        switch (cmd[0].toLowerCase()) {
            case DeviceType.JUNCTION:
                handleJunction(storage, cmd);
                break;
            case DeviceType.CIRCUITBOX:
                handleCircuitBox(storage, cmd);
                break;
            case DeviceType.SWITCH:
                handleSwitch(storage, cmd);
                break;
            case DeviceType.POWER:
                handlePower(storage, cmd);
                break;
            default:
                Printer.printErr(new Exception("There isn't any special function for type: " + cmd[0]));
                break;
        }
    }

    private void handlePower(DeviceMap storage, String[] cmd) {
        try {
            PowerSource power = (PowerSource) storage.get(cmd[1]);
            if(cmd[2].equalsIgnoreCase("on")) {
                power.on();
                Printer.println("It's turned on!");
            } else if(cmd[2].equalsIgnoreCase("off")) {
                power.off();
                Printer.println("Success, Paks deactivated!");
            } else {
                Printer.printErr(new Exception("Invalid method for " + DeviceType.POWER + ": " + cmd[2]));
            }
        } catch (ClassCastException err) {
            Printer.printErr(new Exception(cmd[1] + " is not a " + DeviceType.POWER + "!"));
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
        }
    }

    private void handleSwitch(DeviceMap storage, String[] cmd) {
        try {
            Switch switchy = (Switch) storage.get(cmd[1]);
            if(cmd[2].equalsIgnoreCase("on")) {
                switchy.on();
                Printer.println("Your switch state is now TRUE(1)");
            } else if(cmd[2].equalsIgnoreCase("off")) {
                switchy.off();
                Printer.println("Your switch state is now FALSE(0)");
            } else {
                Printer.printErr(new Exception("Invalid method for " + DeviceType.SWITCH + ": " + cmd[2]));
            }
        } catch (ClassCastException err) {
            Printer.printErr(new Exception(cmd[1] + " is not a " + DeviceType.SWITCH + "!"));
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
        }
    }

    private void handleJunction(DeviceMap storage, String[] cmd) {
        try {
            Junction junction = (Junction) storage.get(cmd[1]);
            ArrayList<Device> devicesToConnect = new ArrayList<>();
            if(cmd[2].equalsIgnoreCase("connectall")) {
                // This step ensures that, if any of the device names are incorrect
                // then no device will be connected to the junction
                for (int i = 3; i < cmd.length; i++) {
                    devicesToConnect.add(storage.get(cmd[i]));
                }
                // Although here, we can have free pin issues.
                // If the junction doesn't have more free output pin
                // or the device doesn't have free input pin, well
                // these devices won't be connected to the junction.
                for(Device d : devicesToConnect) {
                    junction.connect(d);
                    Printer.println("Connected!");
                }
            }
        } catch (ClassCastException err) {
            Printer.printErr(new Exception(cmd[1] + " is not a " + DeviceType.JUNCTION + "!"));
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
        }
    }

    private void handleCircuitBox(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        // FORMATS
        // circuitbox <name> save
        // circuitbox <name> load
        // circuitbox <name> bindinputping <target name> <target pin index> <box pin index>
        // circuitbox <name> bindoutputpin <target name> <target pin index> <box pin index>
        CircuitBox box;

        try {
            // Load circuit command
            if(cmd[2].equalsIgnoreCase("load")) {
                box = FileHandler.loadCircuit(cmd[1]);
                if(box == null) {
                    Printer.printErr("There isn't any saved circuit with this name: " + cmd[1]);
                    return;
                }
                storage.add(box.getName(), box);
                Printer.println(cmd[1] + " loaded successfully!");
            }
            // Save circuit command
            else if(cmd[2].equalsIgnoreCase("save")) {
                box = (CircuitBox) storage.get(cmd[1]);
                FileHandler.saveCircuit(box);
            }
            // Bind input pin command
            else if(cmd[2].equalsIgnoreCase("bindinputpin")) {
                if(cmd.length < 6) throw new NotEnoughArgsException(cmd[0] + " bindinputpin", 5, cmd.length - 1);
                box = (CircuitBox) storage.get(cmd[1]);
                box.bindInputPin(storage.get(cmd[3]), Integer.parseInt(cmd[4]), Integer.parseInt(cmd[5]));
                Printer.println("Pins now bounded!");
            }
            // Bind output pin command
            else if(cmd[2].equalsIgnoreCase("bindoutputpin")) {
                if(cmd.length < 6) throw new NotEnoughArgsException(cmd[0] + " bindoutputpin", 5, cmd.length - 1);
                box = (CircuitBox) storage.get(cmd[1]);
                box.bindOutputPin(storage.get(cmd[3]), Integer.parseInt(cmd[4]), Integer.parseInt(cmd[5]));
                Printer.println("Pins now bounded!");
            }
            // None of the above
            else {
                Printer.printErr("Invalid unique method!");
            }
        } catch (RedundantKeyException | DeviceNotExistsException err) {
            Printer.printErr(err);
        } catch (NumberFormatException err) {
            Printer.printErr("Pin indexes must be numbers!");
        } catch (ClassCastException err) {
            Printer.printErr("You should try this again with a circuit box, shouldn't you?");
        }

    }

}
