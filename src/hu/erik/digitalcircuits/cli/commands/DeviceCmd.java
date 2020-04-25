package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceBundle;
import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.cli.DeviceType;
import hu.erik.digitalcircuits.devices.*;
import hu.erik.digitalcircuits.errors.*;
import hu.erik.digitalcircuits.utils.FileHandler;
import hu.erik.digitalcircuits.utils.Printer;

import java.io.IOException;
import java.util.ArrayList;

import static hu.erik.digitalcircuits.cli.DeviceType.*;

/**
 * Class to handle commands prefixed with a device type.
 * Handles unique method calls on special devices like switch and circuitbox.
 */
public class DeviceCmd extends Command {

    /**
     * Constructor to setup the command's name.
     *
     * @param name Name of the command.
     */
    public DeviceCmd(String name) {
        super(name);
    }

    /**
     * Handle device unique methods.
     *
     * Command format:
     * {@literal <}devicetype{@literal >} {@literal <}name{@literal >} {@literal <}uniqe method{@literal >} {@literal <}args...{@literal >}
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 2.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        if(cmd.length < 3) throw new NotEnoughArgsException("device", 2, cmd.length - 1);

        String type = cmd[0].toLowerCase();

        if(type.equals(JUNCTION.getValue())) {
            handleJunction(storage, cmd);
        } else if(type.equals(CIRCUITBOX.getValue())) {
            handleCircuitBox(storage, cmd);
        } else if(type.equals(SWITCH.getValue())) {
            handleSwitch(storage, cmd);
        } else if(type.equals(POWER.getValue())) {
            handlePower(storage, cmd);
        } else {
            Printer.printErr("There isn't any special function for type: " + cmd[0]);
        }
    }

    /**
     * Handles powersource specific unique methods like ON and OFF.
     *
     * Command format:
     * powersource <name> <on or off>
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     */
    private void handlePower(DeviceMap storage, String[] cmd) {
        try {
            PowerSource power = (PowerSource) storage.get(cmd[1]).getDevice();
            if(cmd[2].equalsIgnoreCase("on")) {
                power.on();
                Printer.println("It's turned on!");
            } else if(cmd[2].equalsIgnoreCase("off")) {
                power.off();
                Printer.println("It's turned off!");
            } else {
                Printer.printErr("Invalid method for " + POWER + ": " + cmd[2]);
            }
        } catch (ClassCastException err) {
            Printer.printErr(cmd[1] + " is not a " + POWER + "!");
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
        }
    }

    /**
     * Handles switch specific unique methods like ON and OFF.
     *
     * Command format:
     * switch <name> <on or off>
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     */
    private void handleSwitch(DeviceMap storage, String[] cmd) {
        try {
            Switch switchy = (Switch) storage.get(cmd[1]).getDevice();
            if(cmd[2].equalsIgnoreCase("on")) {
                switchy.on();
                Printer.println("Your switch state is now TRUE(1)");
            } else if(cmd[2].equalsIgnoreCase("off")) {
                switchy.off();
                Printer.println("Your switch state is now FALSE(0)");
            } else {
                Printer.printErr("Invalid method for " + SWITCH + ": " + cmd[2]);
            }
        } catch (ClassCastException err) {
            Printer.printErr(cmd[1] + " is not a " + SWITCH + "!");
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
        }
    }

    /**
     * Handles powersource specific unique methods like CONNECTALL.
     *
     * Command format:
     * junction {@literal <}name{@literal >} connectall {@literal <}devices...{@literal >}
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     */
    private void handleJunction(DeviceMap storage, String[] cmd) {
        try {
            Junction junction = (Junction) storage.get(cmd[1]).getDevice();
            ArrayList<Device> devicesToConnect = new ArrayList<>();
            if(cmd[2].equalsIgnoreCase("connectall")) {
                if(cmd.length < 4) {
                    Printer.printErr("You didn't specify any device!");
                    return;
                }

                // This step ensures that, if any of the device names are incorrect
                // then no device will be connected to the junction
                for (int i = 3; i < cmd.length; i++) {
                    devicesToConnect.add(storage.get(cmd[i]).getDevice());
                }

                for(Device d : devicesToConnect) {
                    try {
                        junction.connect(d);
                        Printer.println("Connected!");
                    } catch (NoMorePinException err) {
                        Printer.printErr(err);
                    }
                }
            }
        } catch (ClassCastException err) {
            Printer.printErr(cmd[1] + " is not a " + JUNCTION + "!");
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
        }
    }

    /**
     * Handles circuit box specific unique methods like SAVE, LOAD, BINDINPUTPIN, BINDOUTPUTPIN.
     *
     * Command formats:
     * circuitbox {@literal <}name{@literal >} bindinputpin {@literal <}target name{@literal >} {@literal <}target pin index{@literal >} {@literal <}box pin index{@literal >}
     * circuitbox {@literal <}name{@literal >} bindoutputpin {@literal <}target name{@literal >} {@literal <}target pin index{@literal >} {@literal <}box pin index{@literal >}
     * circuitbox {@literal <}name{@literal >} save
     * circuitbox {@literal <}name{@literal >} load
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 5 in case of bind methods.
     */
    private void handleCircuitBox(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        CircuitBox box;

        try {
            // Load circuit command
            if(cmd[2].equalsIgnoreCase("load")) {
                box = FileHandler.loadCircuit(cmd[1]);
                storage.add(box.getName(), new DeviceBundle(box, CIRCUITBOX.getValue()));
                Printer.println(cmd[1] + " loaded successfully!");
            }
            // Save circuit command
            else if(cmd[2].equalsIgnoreCase("save")) {
                box = (CircuitBox) storage.get(cmd[1]).getDevice();
                FileHandler.saveCircuit(box);
                Printer.println("Saved successfully!");
            }
            // Bind input pin command
            else if(cmd[2].equalsIgnoreCase("bindinputpin")) {
                if(cmd.length < 6) throw new NotEnoughArgsException(cmd[0] + " bindinputpin", 5, cmd.length - 1);
                box = (CircuitBox) storage.get(cmd[1]).getDevice();
                box.bindInputPin(storage.get(cmd[3]).getDevice(), Integer.parseInt(cmd[4]), Integer.parseInt(cmd[5]));
                Printer.println("Pins now bounded!");
            }
            // Bind output pin command
            else if(cmd[2].equalsIgnoreCase("bindoutputpin")) {
                if(cmd.length < 6) throw new NotEnoughArgsException(cmd[0] + " bindoutputpin", 5, cmd.length - 1);
                box = (CircuitBox) storage.get(cmd[1]).getDevice();
                box.bindOutputPin(storage.get(cmd[3]).getDevice(), Integer.parseInt(cmd[4]), Integer.parseInt(cmd[5]));
                Printer.println("Pins now bounded!");
            }
            // None of the above
            else {
                Printer.printErr("Invalid unique method!");
            }
        } catch (RedundantKeyException | DeviceNotExistsException | BoundException | PinNotExistsException err) {
            Printer.printErr(err);
        } catch(NumberFormatException err) {
            Printer.printErr("Pin indexes must be numbers!");
        } catch (ClassCastException err) {
            Printer.printErr(cmd[1] + " is not a " + DeviceType.CIRCUITBOX + "!");
        } catch (IOException | ClassNotFoundException err) {
            Printer.printErr("Error while trying to save or load a circuit!");
        }

    }

}
