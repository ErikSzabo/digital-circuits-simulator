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
        Printer.println("create <type> <name> [inputnum] [outputnum]");
        Printer.println("delete <name>");
        Printer.println("list [type]");
        Printer.println("connect <name> to <name>");
        Printer.println("show <input | output> <name> <index>");
        Printer.println("device <name> <uniqe method> [method arguments]");
        Printer.println("help <type>");
        Printer.println("exit");
    }
}
