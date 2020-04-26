package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.cli.commands.*;
import hu.erik.digitalcircuits.errors.InvalidArgumentException;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;
import hu.erik.digitalcircuits.utils.Printer;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Class to handle CLI events and commands.
 */
public class CliController {
    /**
     * Data structure for the controller.
     * User created device will be stored here.
     */
    private DeviceMap devices;
    /**
     * Stores every command what is available for users.
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
     * Adds commands to the data structure. Cli will only use these commands.
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
    }

    /**
     * Creates a nice looking menu from the commands and prints it to the console.
     */
    private void showMenu() {
        Printer.printTitle("Digital Circuits v1.0");
        for(String name : commands.keySet()) {
            Printer.println(commands.get(name).getFormat());
            System.out.println("\t" + commands.get(name).getBriefDescription());
        }
        Printer.printSeparatorLine("-");
    }

}
