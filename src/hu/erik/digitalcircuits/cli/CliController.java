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

        commands.put("connect", new ConnectCmd("connect"));
        commands.put("create", new CreateCmd("create"));
        commands.put("delete", new DeleteCmd("delete"));
        commands.put("device", new DeviceCmd("device"));
        commands.put("devicetypes", new DeviceTypesCmd("devicetypes"));
        commands.put("help", new HelpCmd("help"));
        commands.put("list", new ListCmd("list"));
        commands.put("menu", new MenuCmd("menu"));
        commands.put("show", new ShowCmd("show"));
        commands.put("disconnect", new DisconnectCmd("disconnect"));
    }

    /**
     * Listens for commands in an infinite loop.
     */
    public void listen() {
        Scanner sc = new Scanner(System.in);

        String[] initCMD = {"menu"};
        try {
            commands.get("menu").action(devices, initCMD);
        } catch (NotEnoughArgsException | InvalidArgumentException err) {
            // :)
        }

        while (true) {
            String cmd = sc.nextLine();

            if(cmd.contains("exit")) {
                Printer.println("Bye, have a nice day! :)");
                break;
            } else if(cmd.equals("\n") || cmd.equals("")) {
                continue;
            }

            String[] splittedCMD = cmd.split(" ");

            try {
                if(DeviceType.contains(splittedCMD[0].toLowerCase())) {
                    commands.get("device").action(devices, splittedCMD);
                } else {
                    commands.get(splittedCMD[0]).action(devices, splittedCMD);
                }
            } catch (NotEnoughArgsException | InvalidArgumentException err) {
                Printer.printErr(err);
            } catch (NullPointerException err) {
                Printer.printErr("There isn't any command with this name: " + splittedCMD[0] + "!");
                Printer.printErr("Too see the available commands, use the menu command!");
            }

        }
    }

}
