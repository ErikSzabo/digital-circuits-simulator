package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.devices.Device;
import hu.erik.digitalcircuits.errors.*;
import hu.erik.digitalcircuits.utils.Printer;

/**
 * Class to handle commands prefixed with "show".
 */
public class ShowCmd extends Command {

    /**
     * Constructor to setup the command's name.
     */
    public ShowCmd() {
        super(
                "show",
                "show <input | output> <name> <index>",
                "Show the current value for the given pin on the given device."
        );
    }

    /**
     * Shows information about a pin on a device at the given index.<br>
     *
     * Command format:<br>
     * show {@literal <}output | input{@literal >} {@literal <}name{@literal >} {@literal <}pin index{@literal >}
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 3.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {

        if(cmd.length < 4) throw new NotEnoughArgsException(cmd[0], 3, cmd.length - 1);
        if(cmd.length > 4) Printer.printErr(new TooManyArgumentException(cmd[0]));

        Device device;
        int index;

        try {
            device = storage.get(cmd[2]).getDevice();
            index = Integer.parseInt(cmd[3]);
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
            return;
        } catch (NumberFormatException err) {
            Printer.printErr("Pin indexes must be numbers! (counting starts at 0)");
            return;
        }

        if(cmd[1].equalsIgnoreCase("input")) {
            showInput(device, cmd[2], index);
        } else if(cmd[1].equalsIgnoreCase("output")) {
            showOutput(device, cmd[2], index);
        }
    }

    /**
     * Shows the device input value at the given index.
     *
     * @param device device that has the pin
     * @param name   name of the device
     * @param index  index of the pin
     */
    private void showInput(Device device, String name, int index) {
        try {
            Printer.println(name + " inputpin(" + index +"): " + device.inputPins()[index].getSignal());
        } catch (IndexOutOfBoundsException err) {
            Printer.printErr(new PinNotExistsException(device, index));
        }
    }

    /**
     * Shows the device output value at the given index.
     *
     * @param device device that has the pin
     * @param name   name of the device
     * @param index  index of the pin
     */
    private void showOutput(Device device, String name, int index) {
        try {
            Printer.println(name + " outputpin(" + index +"): " + device.outputPins()[index].getSignal());
        } catch (IndexOutOfBoundsException err) {
            Printer.printErr(new PinNotExistsException(device, index));
        }
    }

}
