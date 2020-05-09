package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.devices.*;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;
import hu.erik.digitalcircuits.errors.RedundantKeyException;
import hu.erik.digitalcircuits.errors.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.Printer;

import java.util.HashMap;
import java.util.function.BiConsumer;

import static hu.erik.digitalcircuits.cli.DeviceType.*;

/**
 * Class to handle commands prefixed with "create".
 */
public class CreateCmd extends Command {
    /**
     * Action methods that Create command can perform, like createSwitch()
     * Based on device type, it has O(1) complexity reach.
     */
    private HashMap<String, BiConsumer<DeviceMap, String[]>> actions;

    /**
     * Constructor to setup the command's name, format, description and it's possible actions.
     */
    public CreateCmd() {
        super(
                "create",
                "create <type> <name> [inputnum] [outputnum]",
                "Creates a device with the given parameters."
        );
        actions = new HashMap<>();
        actions.put(SWITCH.getValue(), this::createSwitch);
        actions.put(POWER.getValue(), this::createPower);
        actions.put(INVERTER.getValue(), this::createInverter);
        actions.put(ANDGATE.getValue(), this::createAnd);
        actions.put(ORGATE.getValue(), this::createOr);
        actions.put(NANDGATE.getValue(), this::createNand);
        actions.put(NORGATE.getValue(), this::createNor);
        actions.put(CIRCUITBOX.getValue(), this::createBox);
        actions.put(JUNCTION.getValue(), this::createJunction);
    }

    /**
     * Creates a device with the given name and type and uses
     * the optional pin numbers if it's needed.<br>
     *
     * Command format:<br>
     * create {@literal <}type{@literal >} {@literal <}name{@literal >} [inputnum] [outputnum]
     *
     * @param storage                   cli data structure
     * @param cmd                       command, split by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 2.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        if(cmd.length < 3) throw new NotEnoughArgsException(cmd[0], 2, cmd.length - 1);

        String type = cmd[1].toLowerCase();
        try {
            actions.get(type).accept(storage, cmd);
        } catch (NullPointerException err) {
            Printer.printErr("Invalid device type!");
        }

    }

    /**
     * Creates a Switch.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void createSwitch(DeviceMap storage, String[] cmd) {
        if(cmd.length > 3) Printer.printErr(new TooManyArgumentException(cmd[0] + " " + SWITCH));
        try {
            storage.add(cmd[2], new DeviceBundle(new Switch(), SWITCH));
            Printer.println("Switch, " + cmd[2] + " added!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        }
    }

    /**
     * Creates a PowerSource.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void createPower(DeviceMap storage, String[] cmd) {
        if(cmd.length > 3) Printer.printErr(new TooManyArgumentException(cmd[0] + " " + POWER));
        try {
            storage.add(cmd[2], new DeviceBundle(new PowerSource(), POWER));
            Printer.println("PowerSource, " + cmd[2] + " added!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        }
    }

    /**
     * Creates an Inverter.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void createInverter(DeviceMap storage, String[] cmd) {
        if(cmd.length > 3) Printer.printErr(new TooManyArgumentException(cmd[0] + " " + INVERTER));
        try {
            storage.add(cmd[2], new DeviceBundle(new Inverter(), INVERTER));
            Printer.println("Inverter, " + cmd[2] + " added!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        }
    }

    /**
     * Creates an AndGate.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void createAnd(DeviceMap storage, String[] cmd) {
        if(cmd.length > 4) Printer.printErr(new TooManyArgumentException(cmd[0] + " " + ANDGATE));
        try {
            storage.add(cmd[2], new DeviceBundle(new AndGate(Integer.parseInt(cmd[3])), ANDGATE));
            Printer.println("AndGate, " + cmd[2] + " added!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        } catch (NumberFormatException | IndexOutOfBoundsException err) {
            Printer.printErr("Invalid arguments! Try: create andgate <name> <input pin number>");
        }
    }

    /**
     * Creates an OrGate.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void createOr(DeviceMap storage, String[] cmd) {
        if(cmd.length > 4) Printer.printErr(new TooManyArgumentException(cmd[0] + " " + ORGATE));
        try {
            storage.add(cmd[2], new DeviceBundle(new OrGate(Integer.parseInt(cmd[3])), ORGATE));
            Printer.println("OrGate, " + cmd[2] + " added!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        } catch (NumberFormatException | IndexOutOfBoundsException err) {
            Printer.printErr("Invalid arguments! Try: create orgate <name> <input pin number>");
        }
    }

    /**
     * Creates a NandGate.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void createNand(DeviceMap storage, String[] cmd) {
        if(cmd.length > 4) Printer.printErr(new TooManyArgumentException(cmd[0] + " " + NANDGATE));
        try {
            storage.add(cmd[2], new DeviceBundle(new NandGate(Integer.parseInt(cmd[3])), NANDGATE));
            Printer.println("NandGate, " + cmd[2] + " added!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        } catch (NumberFormatException | IndexOutOfBoundsException err) {
            Printer.printErr("Invalid arguments! Try: create nandgate <name> <input pin number>");
        }
    }

    /**
     * Creates a NorGate.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void createNor(DeviceMap storage, String[] cmd) {
        if(cmd.length > 4) Printer.printErr(new TooManyArgumentException(cmd[0] + " " + NORGATE));
        try {
            storage.add(cmd[2], new DeviceBundle(new NorGate(Integer.parseInt(cmd[3])), NORGATE));
            Printer.println("NorGate, " + cmd[2] + " added!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        } catch (NumberFormatException | IndexOutOfBoundsException err) {
            Printer.printErr("Invalid arguments! Try: create norgate <name> <input pin number>");
        }
    }

    /**
     * Prints an error to the console.
     * Users can't create circuit boxes with this command.
     * There is an another command for that which is only available
     * in BoxEditorMode.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void createBox(DeviceMap storage, String[] cmd) {
        Printer.printErr("Sorry, You can only create boxes in Box Editor Mode!");
        Printer.printErr("Use: boxeditor, then: box <name> <input pin number> <output pin number>");
    }

    /**
     * Creates a Junction.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void createJunction(DeviceMap storage, String[] cmd) {
        if(cmd.length > 4) Printer.printErr(new TooManyArgumentException(cmd[0] + " " + JUNCTION));
        try {
            storage.add(cmd[2], new DeviceBundle(new Junction(Integer.parseInt(cmd[3])), JUNCTION));
            Printer.println("Junction, " + cmd[2] + " added!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        } catch (NumberFormatException | IndexOutOfBoundsException err) {
            Printer.printErr("Invalid arguments! Try: create junction <name> <output pin number>");
        }
    }
}
