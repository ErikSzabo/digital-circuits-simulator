package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.devices.CircuitBox;
import hu.erik.digitalcircuits.errors.*;
import hu.erik.digitalcircuits.utils.Printer;

import java.util.HashMap;
import java.util.function.BiConsumer;

import static hu.erik.digitalcircuits.cli.DeviceType.CIRCUITBOX;

/**
 * Class to handle commands prefixed with "box".
 */
public class BoxCmd extends Command {
    /**
     * Action methods that Box command can perform based on the arguments.
     */
    private HashMap<String, BiConsumer<DeviceMap, String[]>> actions;

    /**
     * Constructor to setup the command's name, format, description.
     */
    public BoxCmd() {
        super(
                "box",
                "box <name> <inputnum> <outputnum>\n\tbox <name> <bindinput | bindoutput> <target name> <target pin index> <box pin index>",
                "Creates a box with the given parameters or bind its pins. Only works in Box Editor Mode"
        );
        this.actions = new HashMap<>();
        actions.put("create", this::create);
        actions.put("bindinput", this::bindInputPin);
        actions.put("bindoutput", this::bindOutputPin);
    }

    /**
     * Creates a box with the given name and type and pin numbers.<br>
     *
     * Command format:<br>
     * box {@literal <}name{@literal >} {@literal <}create{@literal >} {@literal <}input number{@literal >} {@literal <}output number{@literal >}<br>
     * box {@literal <}name{@literal >} {@literal <}bindinput{@literal >} {@literal <}target name{@literal >} {@literal <}target index{@literal >} {@literal <}box index{@literal >}<br>
     * box {@literal <}name{@literal >} {@literal <}bindoutput{@literal >} {@literal <}target name{@literal >} {@literal <}target index{@literal >} {@literal <}box index{@literal >}
     *
     * @param storage                   cli data structure
     * @param cmd                       command, split by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 5.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        if(cmd.length < 5) throw new NotEnoughArgsException(cmd[0], 4, cmd.length - 1);
        try {
            actions.get(cmd[2]).accept(storage, cmd);
        } catch (NullPointerException err) {
            Printer.printErr("Invalid arguments! Try: help " + CIRCUITBOX);
        }
    }

    /**
     * Creates and adds a CircuitBox to the Cli data structure.
     *
     * @param storage   cli data structure
     * @param cmd       command, split by spaces
     */
    private void create(DeviceMap storage, String[] cmd) {
        if(cmd.length > 5) Printer.printErr(new TooManyArgumentException(cmd[0] + " create"));
        try {
            storage.add(cmd[1], new DeviceBundle(
                    new CircuitBox(
                            cmd[1], Integer.parseInt(cmd[3]),
                            Integer.parseInt(cmd[4])
                    ),
                    CIRCUITBOX)
            );
            Printer.println("CircuitBox, " + cmd[1] + " added!");
        } catch (NumberFormatException err) {
            Printer.printErr("Invalid arguments! Try: " + getFormat());
        } catch (RedundantKeyException err) {
            Printer.printErr(err);
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
    private void bindInputPin(DeviceMap storage, String[] cmd) {
        // box <name> <bindinput | bindoutput> <target name> <target pin index> <box pin index>
        if(cmd.length < 6) {
            Printer.printErr(new NotEnoughArgsException(cmd[0] + " <name> bindinput", 5, cmd.length - 1));
            return;
        } else if(cmd.length > 6) Printer.printErr(new TooManyArgumentException(cmd[0] + " bindinput"));

        DeviceBundle box, target;
        try {
            box = storage.get(cmd[1]);
            target = storage.get(cmd[3]);
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
            return;
        }

        if(!box.getType().equals(CIRCUITBOX)) {
            Printer.printErr(cmd[1] + " is not a "+ CIRCUITBOX +"!");
            return;
        }

        try {
            ((CircuitBox) box.getDevice()).bindInputPin(target.getDevice(), Integer.parseInt(cmd[4]), Integer.parseInt(cmd[5]));
            Printer.println("Pins now bounded!");
        } catch (PinNotExistsException | BoundException err) {
            Printer.printErr(err);
        } catch (NumberFormatException err) {
            Printer.printErr("Invalid arguments! Try: box <name> bindinput <target pint index> <box pin index>");
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
    private void bindOutputPin(DeviceMap storage, String[] cmd) {
        // box <name> <bindinput | bindoutput> <target name> <target pin index> <box pin index>
        if(cmd.length < 6) {
            Printer.printErr(new NotEnoughArgsException(cmd[0] + " <name> bindoutput", 5, cmd.length - 1));
            return;
        } else if(cmd.length > 6) Printer.printErr(new TooManyArgumentException(cmd[0] + " bindoutput"));

        DeviceBundle box, target;
        try {
            box = storage.get(cmd[1]);
            target = storage.get(cmd[3]);
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
            return;
        }

        if(!box.getType().equals(CIRCUITBOX)) {
            Printer.printErr(cmd[1] + " is not a "+ CIRCUITBOX +"!");
            return;
        }

        try {
            ((CircuitBox) box.getDevice()).bindOutputPin(target.getDevice(), Integer.parseInt(cmd[4]), Integer.parseInt(cmd[5]));
            Printer.println("Pins now bounded!");
        } catch (PinNotExistsException | BoundException err) {
            Printer.printErr(err);
        } catch (NumberFormatException err) {
            Printer.printErr("Invalid arguments! Try: box <name> bindoutput <target pint index> <box pin index>");
        }
    }
}
