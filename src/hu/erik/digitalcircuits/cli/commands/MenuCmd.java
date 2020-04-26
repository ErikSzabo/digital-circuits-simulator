package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.utils.Printer;

/**
 * Class to handle commands prefixed with "menu".
 */
public class MenuCmd extends Command {
    /**
     * List of the available Cli commands.
     */
    String[] commands;
    /**
     * List of brief descriptions for available Cli command.
     */
    String[] descriptions;

    /**
     * Constructor to setup the command's name.
     * Initializes commands and their descriptions.
     *
     * @param name Name of the command.
     */
    public MenuCmd(String name) {
        super(name);
        commands = new String[] {
                "menu",
                "create <type> <name> [inputnum] [outputnum]",
                "delete <name>",
                "list [type]",
                "connect <name> to <name>",
                "disconnect <name> from <name>",
                "show <input | output> <name> <index>",
                "devicetypes",
                "help <type>",
                "exit"
        };
        descriptions = new String[] {
                "\tShows the menu.",
                "\tCreates a device with the given parameters.",
                "\tDelete a device with the given name.",
                "\tLists created devices.",
                "\tConnects the first device first free output pin to the second device first free input pin.",
                "\tDisconnects the first device output pin from the second device input pin.",
                "\tShow the current value for the given pin on the given device.",
                "\tLists all available device type.",
                "\tShows help for the specified device type.\n\tYou can view here the unique methods for a device.",
                "\tCloses the program."
        };
    }

    /**
     * Prints the menu to the console. It shows the available commands
     * and a brief description for all of them.
     *
     * Command format:
     * menu
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) {

        Printer.printTitle("Digital Circuits");

        for (int i = 0; i < commands.length; i++) {
            Printer.println(commands[i]);
            System.out.println(descriptions[i]);
        }

        Printer.printSeparatorLine("-");
    }
}
