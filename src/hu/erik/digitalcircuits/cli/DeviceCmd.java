package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.devices.*;
import hu.erik.digitalcircuits.errors.*;
import hu.erik.digitalcircuits.utils.FileHandler;
import hu.erik.digitalcircuits.utils.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

import static hu.erik.digitalcircuits.cli.DeviceType.*;

/**
 * Class to handle commands prefixed with "device".
 * Handles unique method calls on special devices like Switch and CircuitBox.
 */
public class DeviceCmd extends Command {
    /**
     * Action methods that Device command can perform based on a device type.
     */
    private HashMap<String, BiConsumer<DeviceMap, String[]>> actions;

    /**
     * Constructor to setup the command's name, format, description and it's possible actions.
     */
    public DeviceCmd() {
        super(
                "device",
                "device <devicetype> <name> <uniqe method> <args...>",
                "Access device specific functions."
        );
        actions = new HashMap<>();
        actions.put(SWITCH.getValue(), this::handleSwitch);
        actions.put(POWER.getValue(), this::handlePower);
        actions.put(JUNCTION.getValue(), this::handleJunction);
        actions.put(CIRCUITBOX.getValue(), this::handleCircuitBox);
    }

    /**
     * Handle device unique methods.<br>
     *
     * Command format:<br>
     * device {@literal <}devicetype{@literal >} {@literal <}name{@literal >} {@literal <}uniqe method{@literal >} {@literal <}args...{@literal >}
     *
     * @param storage                   cli data structure
     * @param cmd                       command, split by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 2.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        if(cmd.length < 4) throw new NotEnoughArgsException("device", 3, cmd.length - 1);

        String type = cmd[1].toLowerCase();
        try {
            actions.get(type).accept(storage, cmd);
        } catch (NullPointerException err) {
            if(DeviceType.contains(type)) {
                Printer.printErr("There isn't any specific unique method for this device!");
            } else {
                Printer.printErr("This device type is not exists!");
            }
        }
    }

    /**
     * Handles PowerSource specific unique methods like ON and OFF.<br>
     *
     * Command format:<br>
     * device powersource {@literal <}name{@literal >} {@literal <}on or off{@literal >}
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void handlePower(DeviceMap storage, String[] cmd) {
        Device device;

        try {
            device = storage.get(cmd[2]).getDevice();
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
            return;
        }

        if(!(device instanceof PowerSource)) {
            Printer.printErr(cmd[2] + " is not a "+ POWER +"!");
            return;
        }

        if(cmd[3].equalsIgnoreCase("on")) {
            ((PowerSource) device).on();
            Printer.println("Your switch state is now TRUE(1)");
        } else if(cmd[3].equalsIgnoreCase("off")) {
            ((PowerSource) device).off();
            Printer.println("Your switch state is now FALSE(0)");
        } else {
            Printer.printErr("Invalid method for " + POWER + ": " + cmd[2]);
        }
    }

    /**
     * Handles Switch specific unique methods like ON and OFF.<br>
     *
     * Command format:<br>
     * device switch {@literal <}name{@literal >} {@literal <}on or off{@literal >}
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void handleSwitch(DeviceMap storage, String[] cmd) {
        Device device;

        try {
            device = storage.get(cmd[2]).getDevice();
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
            return;
        }

        if(!(device instanceof Switch)) {
            Printer.printErr(cmd[2] + " is not a "+ SWITCH +"!");
            return;
        }

        if(cmd[3].equalsIgnoreCase("on")) {
            ((Switch) device).on();
            Printer.println("Your switch state is now TRUE(1)");
        } else if(cmd[3].equalsIgnoreCase("off")) {
            ((Switch) device).off();
            Printer.println("Your switch state is now FALSE(0)");
        } else {
            Printer.printErr("Invalid method for " + SWITCH + ": " + cmd[3]);
        }
    }

    /**
     * Handles junction specific unique methods like CONNECTALL.<br>
     *
     * Command format:<br>
     * device junction {@literal <}name{@literal >} connectall {@literal <}devices...{@literal >}
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void handleJunction(DeviceMap storage, String[] cmd) {
        Device device;

        try {
            device = storage.get(cmd[2]).getDevice();
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
            return;
        }

        if(hasJunctionError(device, cmd)) return;
        junctionConnectAll(device, cmd, storage);
    }

    /**
     * Checks if there any problem with the junction or with the command.
     * It will also print the error.
     *
     * @param device    device to test for junction type
     * @param cmd       command, split by spaces
     * @return          true if something is wrong
     */
    private boolean hasJunctionError(Device device, String[] cmd) {
        if(!(device instanceof Junction)) {
            Printer.printErr(cmd[2] + " is not a "+ JUNCTION +"!");
            return true;
        } else if(!cmd[3].equalsIgnoreCase("connectall")) {
            Printer.printErr("Invalid method for " + JUNCTION + ": " + cmd[2]);
            return true;
        } else if(cmd.length < 5) {
            Printer.printErr("You have to specify the devices you want to connect!");
            return true;
        }
        return false;
    }

    /**
     * Connects the devices to the junction as long as the junction
     * actually has free output pins.
     *
     * @param device    the junction that will connect to the devices
     * @param cmd       command, split by spaces
     * @param storage   cli data structure
     */
    private void junctionConnectAll(Device device, String[] cmd, DeviceMap storage) {
        ArrayList<Device> devices = new ArrayList<>();

        for (int i = 4; i < cmd.length; i++) {
            try {
                devices.add(storage.get(cmd[i]).getDevice());
            } catch (DeviceNotExistsException err) {
                Printer.printErr(err);
                Printer.printErr("None of the devices will be connected!");
                return;
            }
        }

        try {
            ((Junction) device).connectAll(devices);
        } catch (NoMorePinException err) {
            Printer.printErr(err);
            Printer.printErr("After and including " + err.getDevice() + ", none of the devices connected");
        }
    }

    /**
     * Handles circuit box specific unique methods like SAVE, LOAD, BINDINPUTPIN, BINDOUTPUTPIN.<br>
     *
     * Command formats:<br>
     * device circuitbox {@literal <}name{@literal >} bindinputpin {@literal <}target name{@literal >} {@literal <}target pin index{@literal >} {@literal <}box pin index{@literal >}<br>
     * device circuitbox {@literal <}name{@literal >} bindoutputpin {@literal <}target name{@literal >} {@literal <}target pin index{@literal >} {@literal <}box pin index{@literal >}<br>
     * device circuitbox {@literal <}name{@literal >} save<br>
     * device circuitbox {@literal <}name{@literal >} load
     *
     * @param storage                   cli data structure
     * @param cmd                       command, split by spaces
     */
    private void handleCircuitBox(DeviceMap storage, String[] cmd) {
        // Load circuit command
        if(cmd[3].equalsIgnoreCase("load")) {
            loadCircuit(cmd[2], storage);
        }
        // Save circuit command
        else if(cmd[3].equalsIgnoreCase("save")) {
            saveCircuit(cmd[2], storage);
        }
        // Bind input pin command
        else if(cmd[3].equalsIgnoreCase("bindinputpin")) {
           bindCircuitInput(storage, cmd);
        }
        // Bind output pin command
        else if(cmd[3].equalsIgnoreCase("bindoutputpin")) {
            bindCircuitOutput(storage, cmd);
        }
        // None of the above
        else {
            Printer.printErr("Invalid unique method!");
        }
    }

    /**
     * Loads a CircuitBox into the cli data structure from a file.
     *
     * @param name      name of the box which will be loaded
     * @param storage   cli data structure
     */
    private void loadCircuit(String name, DeviceMap storage) {
        try {
            CircuitBox box = FileHandler.loadCircuit(name);
            storage.add(box.getName(), new DeviceBundle(box, CIRCUITBOX));
            Printer.println("Circuit loaded!");
        } catch (IOException | ClassNotFoundException err) {
            Printer.printErr("Something went wrong with the circuit loading!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        }
    }

    /**
     * Saves a CircuitBox from the cli data structure to a file.
     *
     * @param boxName   name of the box which will be saved
     * @param storage   cli data structure
     */
    private void saveCircuit(String boxName, DeviceMap storage) {
        Device device;
        try {
            device = storage.get(boxName).getDevice();
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
            return;
        }

        if(checkCircuitBox(device)) return;

        try {
            FileHandler.saveCircuit((CircuitBox) device);
            Printer.println(boxName + " has been saved!");
        } catch (IOException err) {
            Printer.printErr("Save failed!");
        }
    }


    /**
     * Binds an input pin to the box.
     * Won't bind if the command is incorrect, for example
     * non-existing device, non-integer pin indexes.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void bindCircuitInput(DeviceMap storage, String[] cmd) {
        if(cmd.length < 7) {
            Printer.printErr(new NotEnoughArgsException(cmd[0] + " bindinputpin", 5, cmd.length - 1));
            return;
        }

        Device box, other;
        try {
            box = storage.get(cmd[2]).getDevice();
            other = storage.get(cmd[4]).getDevice();
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
            return;
        }

        if(checkCircuitBox(box)) return;

        try {
            ((CircuitBox) box).bindInputPin(other, Integer.parseInt(cmd[5]), Integer.parseInt(cmd[6]));
            Printer.println("Pins now bounded!");
        } catch (BoundException | PinNotExistsException err) {
            Printer.printErr(err);
        } catch (NumberFormatException err) {
            Printer.printErr("Pin indexes must be numbers!");
        }
    }

    /**
     * Binds an output pin to the box.
     * Won't bind if the command is incorrect, for example
     * non-existing device, non-integer pin indexes.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void bindCircuitOutput(DeviceMap storage, String[] cmd) {
        if(cmd.length < 7) {
            Printer.printErr(new NotEnoughArgsException(cmd[0] + " bindoutputpin", 5, cmd.length - 1));
            return;
        }

        Device box, other;
        try {
            box = storage.get(cmd[2]).getDevice();
            other = storage.get(cmd[4]).getDevice();
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
            return;
        }

        if(checkCircuitBox(box)) return;

        try {
            ((CircuitBox) box).bindOutputPin(other, Integer.parseInt(cmd[5]), Integer.parseInt(cmd[6]));
            Printer.println("Pins now bounded!");
        } catch (BoundException | PinNotExistsException err) {
            Printer.printErr(err);
        } catch (NumberFormatException err) {
            Printer.printErr("Pin indexes must be numbers!");
        }
    }

    /**
     * Checks if the given device is a CircuitBox or not.
     * Will print the error message as well.
     *
     * @param device    device to check
     * @return          true if the device is a CircuitBox
     */
    private boolean checkCircuitBox(Device device) {
        if(!(device instanceof CircuitBox)) {
            Printer.printErr("This device is not a CircuitBox!");
            return true;
        }
        return false;
    }
}
