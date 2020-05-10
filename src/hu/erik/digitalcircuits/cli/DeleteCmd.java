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
     * @param cmd                       command, split by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 1.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        if(cmd.length < 2) throw new NotEnoughArgsException(cmd[0], 1, 0);
        if(cmd.length > 2) Printer.printErr(new TooManyArgumentException(cmd[0]));


        try {
            DeviceBundle device = storage.get(cmd[1]);
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
            // UPDATE: Since user can't delete devices in boxeditor mode, and user can't create
            //         boxes in normal mode, this code is no longer necessary. For future changes, I left this here.
            // p.setAvailability(true);
            // p.setConnectionCable(null);
            // p.setSignal(false);
            // p.getParentDevice().calcOutput();
            // p.getParentDevice().sendOutput();
        }
    }
}
