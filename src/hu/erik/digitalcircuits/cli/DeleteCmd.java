package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.devices.Device;
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
     * Deletes a device with the given name.<br>
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
            Device d = storage.get(cmd[1]).getDevice();
            connectionReset(d.inputPins(), "input");
            connectionReset(d.outputPins(), "output");
            storage.remove(cmd[1]);
            Printer.println("Device has been deleted!");
        } catch (DeviceNotExistsException err) {
            Printer.printErr(err);
        }
    }

    /**
     * Resets the pin connections and recalculates the circuit values
     * if the type of the give pins is output.
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
        }
    }
}
