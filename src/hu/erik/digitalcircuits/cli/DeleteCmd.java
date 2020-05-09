package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.devices.Pin;
import hu.erik.digitalcircuits.errors.DeviceNotExistsException;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;
import hu.erik.digitalcircuits.errors.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.Printer;

/**
 * Class to handle command prefixed with "delete".
 */
public class DeleteCmd extends Command {

    /**
     * Constructor to setup the command's name, format and description.
     */
    public DeleteCmd() {
        super(
                "delete",
                "delete <name>",
                "Delete a device with the given name."
        );
    }

    /**
     * Deletes a device with the given name, and also disconnects the device from
     * every other connected devices.<br>
     *
     * Command format:<br>
     * delete {@literal <}name{@literal >}
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 1.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        if(cmd.length < 2) throw new NotEnoughArgsException(cmd[0], 1, 0);
        if(cmd.length > 2) Printer.printErr(new TooManyArgumentException(cmd[0]));


        try {
            DeviceBundle device = storage.get(cmd[1]);
            if(device.getType().equals(DeviceType.CIRCUITBOX)) {
                Printer.println("You are trying to delete a CircuitBox." +
                        "\nAll of the devices that are bounded to the box will lose all of their" +
                        " connections! (Inner connections will remain the same.)" +
                        "\nFor example, if you have a Switch -> Andgate -> Orgate -> Nandgate circuit" +
                        " where the Andgate inputs and Orgate outputs are bounded to the box. " +
                        "\nWell, then your Orgate loses its connection to the Nandgate, and your Andgate lose its connection to the Switch." +
                        "\nAndgate and Orgate connection will remain the same." +
                        "\nThis thing isn't an issue if you just loaded your fresh circuit from yesterday.");
            }
            connectionReset(device.getDevice().inputPins(), "input");
            connectionReset(device.getDevice().outputPins(), "output");
            storage.remove(cmd[1]);
            Printer.println("Device has been deleted!");
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
        }
    }

    /**
     * Resets the pin connections and recalculates the circuit signals
     * if the type of the pins is output.
     *
     * @param pins  pins which will be reset
     * @param type  type of the pins
     */
    private void connectionReset(Pin[] pins, String type) {
        for(Pin p : pins) {
            if(!p.isFree()) {
                Pin otherPin = p.getConnectionCable().getOtherPin(p);
                otherPin.setConnectionCable(null);
                otherPin.setAvailability(true);
                if(type.equals("output")) {
                    otherPin.setSignal(false);
                    otherPin.getParentDevice().calcOutput();
                    otherPin.getParentDevice().sendOutput();
                }
            }
            // This is necessary because of CircuitBox reference bindings
            p.setAvailability(true);
            p.setConnectionCable(null);
            p.setSignal(false);
            p.getParentDevice().calcOutput();
            p.getParentDevice().sendOutput();
        }
    }
}
