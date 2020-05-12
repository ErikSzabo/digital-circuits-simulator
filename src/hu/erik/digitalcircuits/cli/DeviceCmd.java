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
     * Based on device type, it has O(1) complexity reach.
     */
    private HashMap<DeviceType, BiConsumer<DeviceMap, String[]>> actions;

    /**
     * Constructor to setup the command's name, format, description and it's possible actions.
     */
    public DeviceCmd() {
        super(
                "device",
                "device <name> <uniqe method> <args...>",
                "Access device specific functions."
        );
        actions = new HashMap<>();
        actions.put(SWITCH, this::handleSwitch);
        actions.put(JUNCTION, this::handleJunction);
        actions.put(CIRCUITBOX, this::handleCircuitBox);
    }

    /**
     * Handle device unique methods.<br>
     *
     * Command format:<br>
     * device {@literal <}name{@literal >} {@literal <}uniqe method{@literal >} {@literal <}args...{@literal >}
     *
     * @param storage                   cli data structure
     * @param cmd                       command, split by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 2.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        if(cmd.length < 3) throw new NotEnoughArgsException("device", 2, cmd.length - 1);

        if(cmd[2].strip().equalsIgnoreCase("load")) {
            handleCircuitBox(storage, cmd);
            return;
        }

        try {
            actions.get(storage.get(cmd[1]).getType()).accept(storage, cmd);
        } catch (NullPointerException err) {
            Printer.printErr("There isn't any specific unique method for this device!");
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
        }
    }

    /**
     * Handles Switch specific unique methods like ON and OFF.<br>
     *
     * Command format:<br>
     * device {@literal <}name{@literal >} {@literal <}on or off{@literal >}
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void handleSwitch(DeviceMap storage, String[] cmd) {
        if(cmd.length > 3) Printer.printErr(new TooManyArgumentException(cmd[0]));
        DeviceBundle bundle;

        try {
            bundle = storage.get(cmd[1]);
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
            return;
        }

        if(cmd[2].equalsIgnoreCase("on")) {
            ((Switch) bundle.getDevice()).on();
            Printer.println("Your switch state is now TRUE(1)");
        } else if(cmd[2].equalsIgnoreCase("off")) {
            ((Switch) bundle.getDevice()).off();
            Printer.println("Your switch state is now FALSE(0)");
        } else {
            Printer.printErr("Invalid arguments! Try: device <name> <on | off>");
        }
    }

    /**
     * Handles junction specific unique methods like CONNECTALL.<br>
     *
     * Command format:<br>
     * device {@literal <}name{@literal >} connectall {@literal <}devices...{@literal >}
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void handleJunction(DeviceMap storage, String[] cmd) {
        DeviceBundle bundle;

        try {
            bundle = storage.get(cmd[1]);
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
            return;
        }

        if(cmd[2].equalsIgnoreCase("connectall") && cmd.length > 3) {
            junctionConnectAll(bundle.getDevice(), cmd, storage);
        } else {
            Printer.printErr("Invalid arguments! Try: device <name> connectall <name1> <name2> <nameN>");
        }


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

        for (int i = 3; i < cmd.length; i++) {
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
            Printer.println("Connected!");
        } catch (NoMorePinException err) {
            Printer.printErr(err);
            Printer.printErr("Some of the connections can't be made.");
        }
    }

    /**
     * Handles circuit box specific unique methods like LOAD<br>
     *
     * Command formats:<br>
     * device {@literal <}name{@literal >} load
     *
     * @param storage                   cli data structure
     * @param cmd                       command, split by spaces
     */
    private void handleCircuitBox(DeviceMap storage, String[] cmd) {
        if(cmd.length > 3) Printer.printErr(new TooManyArgumentException(cmd[0]));
        try {
            CircuitBox box = FileHandler.loadCircuit(cmd[1]);
            storage.add(box.getName(), new DeviceBundle(box, CIRCUITBOX));
            Printer.println("Circuit loaded!");
        } catch (IOException | ClassNotFoundException err) {
            Printer.printErr("Something went wrong with the circuit loading!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        }

    }

}
