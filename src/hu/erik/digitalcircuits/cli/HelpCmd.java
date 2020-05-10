package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.errors.InvalidArgumentException;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;
import hu.erik.digitalcircuits.errors.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.FileHandler;
import hu.erik.digitalcircuits.utils.Printer;

import java.io.IOException;

import static hu.erik.digitalcircuits.cli.DeviceType.*;

/**
 * Class to handle commands prefixed with "help".
 */
public class HelpCmd extends Command {
    /**
     * Stores the help page for PowerSource devices in a single String.
     */
    private String powerHelp;

    /**
     * Stores the help page for Switch devices in a single String.
     */
    private String switchHelp;

    /**
     * Stores the help page for Gate and Inverter devices in a single String.
     */
    private String gateHelp;

    /**
     * Stores the help page for Junction devices in a single String.
     */
    private String junctionHelp;

    /**
     * Stores the help page for CircuitBox devices in a single String.
     */
    private String circuitBoxHelp;

    /**
     * Constructor to setup the command's name, format and description.
     */
    public HelpCmd() {
        super(
                "help",
                "help <devicetype>",
                "Shows help for the specified device type. You can view here the unique methods for a device."
        );
        try {
            powerHelp = FileHandler.readHelpPage("power_help");
            switchHelp = FileHandler.readHelpPage("switch_help");
            junctionHelp = FileHandler.readHelpPage("junction_help");
            circuitBoxHelp = FileHandler.readHelpPage("box_help");
            gateHelp = FileHandler.readHelpPage("gate_help");
        } catch (IOException err) {
            Printer.printErr("Some of the help pages can't be loaded.");
        }
    }

    /**
     * Writes the required help page (based on DeviceType) to the console.<br>
     *
     * Command format:<br>
     * help {@literal <}device type{@literal >}
     *
     * @param storage                   cli data structure
     * @param cmd                       command, split by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 1.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException, InvalidArgumentException {
        if(cmd.length < 2) throw new NotEnoughArgsException(cmd[0], 1, cmd.length - 1);
        if(!DeviceType.contains(cmd[1].toLowerCase())) throw new InvalidArgumentException(cmd[0], cmd[1]);
        if(cmd.length > 2) Printer.printErr(new TooManyArgumentException(cmd[0]));

        Printer.printSeparatorLine("-");
        if(cmd[1].toLowerCase().contains("gate") || cmd[1].equalsIgnoreCase(INVERTER.getValue())) {
            System.out.println(gateHelp);
        } else if(cmd[1].equalsIgnoreCase(POWER.getValue())) {
            System.out.println(powerHelp);
        } else if(cmd[1].equalsIgnoreCase(SWITCH.getValue())) {
            System.out.println(switchHelp);
        } else if(cmd[1].equalsIgnoreCase(JUNCTION.getValue())) {
            System.out.println(junctionHelp);
        } else if(cmd[1].equalsIgnoreCase(CIRCUITBOX.getValue())) {
            System.out.println(circuitBoxHelp);
        }
        Printer.printSeparatorLine("-");

    }
}
