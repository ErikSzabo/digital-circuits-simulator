package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.cli.commands.*;
import hu.erik.digitalcircuits.errors.clierror.NotEnoughArgsException;
import hu.erik.digitalcircuits.utils.Printer;

import java.util.HashMap;
import java.util.Scanner;

public class CliController {
    private DeviceMap devices;
    private HashMap<String, Command> commands;

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
    }

    public void listen() {
        Scanner sc = new Scanner(System.in);

        String[] initCMD = {"menu"};
        try {
            commands.get("menu").action(devices, initCMD);
        } catch (NotEnoughArgsException err) {
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
                commands.get(splittedCMD[0]).action(devices, splittedCMD);
            } catch (NotEnoughArgsException err) {
                Printer.printErr(err);
            } catch (NullPointerException err) {
                Printer.printErr(new Exception("There isn't any command with this name: " + splittedCMD[0] + "!"));
                Printer.printErr(new Exception("Too see the available commands, use the menu command!"));
            }

        }
    }

}
