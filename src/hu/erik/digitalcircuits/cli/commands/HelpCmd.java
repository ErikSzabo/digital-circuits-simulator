package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.cli.DeviceType;
import hu.erik.digitalcircuits.errors.InvalidArgumentException;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;
import hu.erik.digitalcircuits.errors.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.Printer;

import static hu.erik.digitalcircuits.cli.DeviceType.*;

/**
 * Class to handle commands prefixed with "help".
 */
public class HelpCmd extends Command {

    /**
     * Constructor to setup the command's name.
     *
     * @param name Name of the command.
     */
    public HelpCmd(String name) {
        super(name);
    }

    /**
     * Writes the required help page to the console.
     *
     * Command format:
     * help {@literal <}device type{@literal >}
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 1.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException, InvalidArgumentException {
        if(cmd.length < 2) throw new NotEnoughArgsException(cmd[0], 1, cmd.length - 1);
        if(!DeviceType.contains(cmd[1].toLowerCase())) throw new InvalidArgumentException(cmd[0], cmd[1]);
        if(cmd.length > 2) Printer.printErr(new TooManyArgumentException(cmd[0]));

        if(cmd[1].toLowerCase().contains("gate") || cmd[1].equalsIgnoreCase(INVERTER.getValue())) {
            writeGatesHelpPage();
        } else if(cmd[1].equalsIgnoreCase(POWER.getValue())) {
            writePowerHelpPage();
        } else if(cmd[1].equalsIgnoreCase(SWITCH.getValue())) {
            writeSwitchHelpPage();
        } else if(cmd[1].equalsIgnoreCase(JUNCTION.getValue())) {
            writeJunctionHelpPage();
        } else if(cmd[1].equalsIgnoreCase(CIRCUITBOX.getValue())) {
            writeCircuitBoxHelpPage();
        }


    }

    /**
     * Write the power source help page to the console.
     * It contains information about creation, and unique methods.
     */
    private void writePowerHelpPage() {
        Printer.printSeparatorLine("-");
        System.out.println("PowerSource\n" +
                "\n" +
                "Used for giving eletricity to switches, or to generate constant 0 or 1 signal to a device.\n" +
                "Important thing, that a Switch will never work without a connected PowerSource.\n" +
                "\n" +
                "To create: \"create powersource <name>\"\n" +
                "\n" +
                "Uniqe functions:\n" +
                "on - turns on the electricity\n" +
                "off - turns off the electricity\n" +
                "\n" +
                "You can use these uniqe functions by typing \"powersource <your powersource name> <on or off>\"");
        Printer.printSeparatorLine("-");
    }

    /**
     * Write the switch help page to the console.
     * It contains information about creation, and unique methods.
     */
    private void writeSwitchHelpPage() {
        Printer.printSeparatorLine("-");
        System.out.println("Switch\n" +
                "\n" +
                "Used to simulate circuit variables. You can set your variables in your circuits with switches.\n" +
                "Switch won't work unless it's connected to a PowerSource.\n" +
                "\n" +
                "To create: \"create switch <name>\"\n" +
                "\n" +
                "Uniqe functions:\n" +
                "on - generete 1 on the output\n" +
                "off - generate 0 on the output\n" +
                "\n" +
                "You can use these uniqe functions by typing \"switch <your switch name> <on or off>\"");
        Printer.printSeparatorLine("-");
    }

    /**
     * Write the junction help page to the console.
     * It contains information about creation, and unique methods.
     */
    private void writeJunctionHelpPage() {
        Printer.printSeparatorLine("-");
        System.out.println("Junction\n" +
                "\n" +
                "Junctions are usally used to duplicate signals. For example, you can connect your PowerSource to a\n" +
                "Junction, and then you can connect the junction to all of your switches.\n" +
                "\n" +
                "To create: \"create junction <name> <output pin number>\"\n" +
                "\n" +
                "Junction devices have access to a special connectall function. With this, you can connect multiple devices\n" +
                "to your Junction at the same time\n" +
                "\n" +
                "To use this uniqe function, type: \"junction <your junction name> connectall <you device names separated by spaces>\"");
        Printer.printSeparatorLine("-");
    }

    /**
     * Write the gates and inverter help page to the console.
     * It contains information about creation, and unique methods.
     */
    private void writeGatesHelpPage() {
        Printer.printSeparatorLine("-");
        System.out.println("Gates\n" +
                "\n" +
                "A Gate or an Inverter is responsible for the circuit logic.\n" +
                "\n" +
                "To create a Gate: \"create <gatetype or inverter> <name> [input pin number if it is a gate] ");
        Printer.printSeparatorLine("-");
    }

    /**
     * Write the circuit box help page to the console.
     * It contains information about creation, and unique methods.
     */
    private void writeCircuitBoxHelpPage() {
        Printer.printSeparatorLine("-");
        System.out.println("CircuitBox\n" +
                "\n" +
                "With this tool, you can put your whole circuit to a box. And the reason to that, well, you can save and load\n" +
                "your boxes. So next time if you want to use the same circuit, the only thing you need is to load your box.\n" +
                "Don't bind switch pins to input pins! You won't be able to interact with the switch after loading.(on off methods)\n" +
                "\n" +
                "To create: \"create circuitbox <name> <box input pins> <box output pins>\"\n" +
                "\n" +
                "- Bind your circuit pins to the box:\n" +
                "     - To bind an input pin, type: \n" +
                "          \"circuitbox <box name> bindinputpin <target device name> <target pin index> <box pin index>\"\n" +
                "     - To bind an output pin, type:\n" +
                "          \"circuitbox <box name> bindoutputpin <target device name> <target pin index> <box pin index>\"\n" +
                "\n" +
                "- Save and load\n" +
                "      - To save, type: \"circuitbox <box name> save\"\n" +
                "      - To load, type: \"circuitbox <boxname> load\"\n" +
                "     After loading, you can connect your box as usual.");
        Printer.printSeparatorLine("-");
    }

}
