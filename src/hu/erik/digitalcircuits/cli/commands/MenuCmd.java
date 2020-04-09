package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.utils.Printer;

public class MenuCmd extends Command {
    public MenuCmd(String name) {
        super(name);
    }

    @Override
    public void action(DeviceMap storage, String[] cmd) {
        Printer.printTitle("Digital Circuits");
        Printer.println("menu");
        System.out.println("Shows the menu.");
        Printer.println("create <type> <name> [inputnum] [outputnum]");
        System.out.println("Creates a device with the given parameters.");
        Printer.println("delete <name>");
        System.out.println("Delete a device with the given name.");
        Printer.println("list [type]");
        System.out.println("lists created devices.");
        Printer.println("connect <name> to <name>");
        System.out.println("Connects the first device first output pin to the second device first free input pin.");
        Printer.println("show <input | output> <name> <index>");
        System.out.println("Show the current value for the given pin on the given device.");
        Printer.println("help <type>");
        System.out.println("Shows help for the specified device type.");
        System.out.println("You can view here the unique methods for a device.");
        Printer.println("exit");
    }
}
