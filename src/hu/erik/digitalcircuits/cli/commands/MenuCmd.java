package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.utils.Printer;

public class MenuCmd extends Command {
    public MenuCmd(String name) {
        super(name);
    }

    @Override
    public void action(DeviceMap storage, String[] cmd) {
        String[] commands = {
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

        String[] descriptions = {
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

        Printer.printTitle("Digital Circuits");

        for (int i = 0; i < commands.length; i++) {
            Printer.println(commands[i]);
            System.out.println(descriptions[i]);
        }

        Printer.printSeparatorLine("-");
    }
}
