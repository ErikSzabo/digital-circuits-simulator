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
        System.out.println("\tShows the menu.");
        Printer.println("create <type> <name> [inputnum] [outputnum]");
        System.out.println("\tCreates a device with the given parameters.");
        Printer.println("delete <name>");
        System.out.println("\tDelete a device with the given name.");
        Printer.println("list [type]");
        System.out.println("\tLists created devices.");
        Printer.println("connect <name> to <name>");
        System.out.println("\tConnects the first device first free output pin to the second device first free input pin.");
        Printer.println("disconnect <name> from <name>");
        System.out.println("\tDisconnects the first device output pin from the second device input pin.");
        Printer.println("show <input | output> <name> <index>");
        System.out.println("\tShow the current value for the given pin on the given device.");
        Printer.println("devicetypes");
        System.out.println("\tLists all available device type.");
        Printer.println("help <type>");
        System.out.println("\tShows help for the specified device type.");
        System.out.println("\tYou can view here the unique methods for a device.");
        Printer.println("exit");
        Printer.printSeparatorLine("-");
    }
}
