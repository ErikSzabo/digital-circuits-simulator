package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.errors.InvalidArgumentException;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;
import hu.erik.digitalcircuits.errors.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.Printer;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Class to handle CLI events and commands.
 */
public class CliController {
    /**
     * Data structure for the controller.
     * User created devices will be stored here.
     */
    private DeviceMap devices;
    /**
     * Stores every command that is available for users.
     */
    private HashMap<String, Command> commands;

    /**
     * Default constructor to initialize the store, and the commands.
     */
    public CliController() {
        devices = new DeviceMap();
        commands = new HashMap<>();
    }

    /**
     * Adds commands to the data structure. CliController will only use these commands.
     * Make sure to add the commands before calling listen method!
     *
     * @param commands command which will be added to the cli
     */
    public void addCommands(Command... commands) {
        for(Command cmd : commands) {
            this.commands.put(cmd.getName(), cmd);
        }
    }

    /**
     * Listens for commands in an infinite loop.
     */
    public void listen() {
        Scanner sc = new Scanner(System.in);
        showMenu();

        while (true) {
            String cmd = sc.nextLine();
            String[] splitCMD = cmd.split(" ");

            if(splitCMD[0].equalsIgnoreCase("exit")) {
                Printer.println("Bye, have a nice day! :)");
                break;
            } else if(splitCMD[0].equalsIgnoreCase("menu")) {
                if(splitCMD.length > 1) Printer.printErr(new TooManyArgumentException("menu"));
                showMenu();
                continue;
            } else if(cmd.equals("\n") || cmd.equals("")) {
                continue;
            }

            try {
                commands.get(splitCMD[0]).action(devices, splitCMD);
            } catch (NotEnoughArgsException | InvalidArgumentException err) {
                Printer.printErr(err);
            } catch (NullPointerException err) {
                Printer.printErr("There isn't any command with this name: " + splitCMD[0] + "!");
                Printer.printErr("Too see the available commands, use the menu command!");
            }
        }

        sc.close();
    }

    /**
     * Creates a nice looking menu from the commands and prints it to the console.
     */
    private void showMenu() {
        Printer.printTitle("Digital Circuits v2.0");
        Printer.println("menu");
        System.out.println("\tShows this menu.");
        for(String name : commands.keySet()) {
            Printer.println(commands.get(name).getFormat());
            System.out.println("\t" + commands.get(name).getBriefDescription());
        }
        Printer.println("exit");
        System.out.println("\tCloses the application.");
        Printer.printSeparatorLine("-");
    }

}
