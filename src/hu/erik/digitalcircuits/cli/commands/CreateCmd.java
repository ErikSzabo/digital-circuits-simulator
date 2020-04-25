package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceBundle;
import hu.erik.digitalcircuits.cli.DeviceMap;
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
    private HashMap<String, BiConsumer<DeviceMap, String[]>> actions;

    /**
     * Constructor to setup the command's name and it's possible actions.
     *
     * @param name Name of the command.
     */
    public CreateCmd(String name) {
        super(name);
        actions = new HashMap<>();
        actions.put(SWITCH.getValue(), this::createSwitch);
        actions.put(POWER.getValue(), this::createPower);
        actions.put(INVERTER.getValue(), this::createInverter);
        actions.put(ANDGATE.getValue(), this::createAnd);
        actions.put(ORGATE.getValue(), this::createOr);
        actions.put(NANDGATE.getValue(), this::createNand);
        actions.put(NORGATE.getValue(), this::createNor);
        actions.put(CIRCUITBOX.getValue(), this::createBox);
    }

    /**
     * Creates a device with the given name and type and uses
     * the optional pin numbers if it's needed.
     *
     * Command format:
     * create {@literal <}type{@literal >} {@literal <}name{@literal >} [inputnum] [outputnum]
     *
     * @param storage                   cli data structure
     * @param cmd                       command, split by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 2.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        if(cmd.length < 3) throw new NotEnoughArgsException(cmd[0], 2, cmd.length - 1);
        if(cmd.length > 5) Printer.printErr(new TooManyArgumentException(cmd[0]));

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
        try {
            storage.add(cmd[2], new DeviceBundle(new Switch(), SWITCH.getValue()));
            Printer.println("Switch added!");
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
        try {
            storage.add(cmd[2], new DeviceBundle(new PowerSource(), POWER.getValue()));
            Printer.println("PowerSource added!");
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
        try {
            storage.add(cmd[2], new DeviceBundle(new Inverter(), INVERTER.getValue()));
            Printer.println("Inverter added!");
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
        try {
            storage.add(cmd[2], new DeviceBundle(new AndGate(Integer.parseInt(cmd[3])), ANDGATE.getValue()));
            Printer.println("AndGate added!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        } catch (NumberFormatException err) {
            Printer.printErr("Pin amount must be a number!");
        } catch (IndexOutOfBoundsException err) {
            Printer.printErr("You must specify the number of input pins!");
        }
    }

    /**
     * Creates an OrGate.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void createOr(DeviceMap storage, String[] cmd) {
        try {
            storage.add(cmd[2], new DeviceBundle(new OrGate(Integer.parseInt(cmd[3])), ORGATE.getValue()));
            Printer.println("OrGate added!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        } catch (NumberFormatException err) {
            Printer.printErr("Pin amount must be a number!");
        } catch (IndexOutOfBoundsException err) {
            Printer.printErr("You must specify the number of input pins!");
        }
    }

    /**
     * Creates a NandGate.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void createNand(DeviceMap storage, String[] cmd) {
        try {
            storage.add(cmd[2], new DeviceBundle(new NandGate(Integer.parseInt(cmd[3])), NANDGATE.getValue()));
            Printer.println("NandGate added!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        } catch (NumberFormatException err) {
            Printer.printErr("Pin amount must be a number!");
        } catch (IndexOutOfBoundsException err) {
            Printer.printErr("You must specify the number of input pins!");
        }
    }

    /**
     * Creates a NorGate.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void createNor(DeviceMap storage, String[] cmd) {
        try {
            storage.add(cmd[2], new DeviceBundle(new NorGate(Integer.parseInt(cmd[3])), NORGATE.getValue()));
            Printer.println("NorGate added!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        } catch (NumberFormatException err) {
            Printer.printErr("Pin amount must be a number!");
        } catch (IndexOutOfBoundsException err) {
            Printer.printErr("You must specify the number of input pins!");
        }
    }

    /**
     * Creates a CircuitBox.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void createBox(DeviceMap storage, String[] cmd) {
        try {
            storage.add(cmd[2], new DeviceBundle(new CircuitBox(cmd[2], Integer.parseInt(cmd[3]), Integer.parseInt(cmd[4])), CIRCUITBOX.getValue()));
            Printer.println("CircuitBox added!");
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
        } catch (NumberFormatException err) {
            Printer.printErr("Pin amount must be a number!");
        } catch (IndexOutOfBoundsException err) {
            Printer.printErr("You must specify the number of input and output pins!");
        }
    }
}
