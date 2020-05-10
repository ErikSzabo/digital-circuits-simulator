package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.devices.CircuitBox;
import hu.erik.digitalcircuits.errors.InvalidArgumentException;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;
import hu.erik.digitalcircuits.errors.RedundantKeyException;
import hu.erik.digitalcircuits.errors.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.FileHandler;
import hu.erik.digitalcircuits.utils.Printer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Class to handle CLI events and commands.
 * Features two user mode, normal and boxeditor.
 * In normal mode CircuitBoxes can't be created.
 * Box editor mode used for creating boxes. After exiting this mode,
 * all of the boxes will be saved and added to the normal mode. Anything else
 * will be deleted. User can switch modes at any time.
 * In box editor mode, delete command can't be use to ensure that
 * users don't mess up their circuit.
 */
public class CliController {
    /**
     * Data structure for the controller.
     * User created devices will be stored here.
     */
    private DeviceMap devices;
    /**
     * Data structure for the box editor mode.
     * User created devices will be stored here in editor mode.
     * After the box is done, this will be erased.
     */
    private DeviceMap boxEditorDevices;
    /**
     * Stores every command that is available for users.
     */
    private HashMap<String, Command> commands;
    /**
     * Stores every command that is available for users in the box editor mode.
     */
    private HashMap<String, Command> boxEditorCommands;
    /**
     * Determines whether the user in box editor mode or not.
     */
    private boolean inBoxEditorMode;

    /**
     * Default constructor to initialize the store, and the commands.
     */
    public CliController() {
        devices = new DeviceMap();
        boxEditorDevices = new DeviceMap();
        commands = new HashMap<>();
        boxEditorCommands = new HashMap<>();
        inBoxEditorMode = false;
        boxEditorCommands.put("box", new BoxCmd());
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
            this.boxEditorCommands.put(cmd.getName(), cmd);
        }
    }

    /**
     * Listens for commands in an infinite loop.
     */
    public void listen() {
        boxEditorCommands.remove("delete");
        Scanner sc = new Scanner(System.in);
        showMenu();

        while (true) {
            String cmd = sc.nextLine();
            String[] splitCMD = cmd.split(" ");

            if(splitCMD[0].equalsIgnoreCase("exit")) {
                Printer.println("Bye, have a nice day! :)");
                break;
            } else if(splitCMD[0].equalsIgnoreCase("menu")) {
                if (splitCMD.length > 1) Printer.printErr(new TooManyArgumentException("menu"));
                showMenu();
                continue;
            } else if(splitCMD[0].equalsIgnoreCase("boxeditor")) {
                handleEditorMode(splitCMD);
                continue;
            } else if(cmd.equals("\n") || cmd.equals("")) {
                continue;
            }

            try {
                if(inBoxEditorMode) {
                    boxEditorCommands.get(splitCMD[0]).action(boxEditorDevices, splitCMD);
                } else {
                    commands.get(splitCMD[0]).action(devices, splitCMD);
                }
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
        Printer.println(boxEditorCommands.get("box").getFormat());
        System.out.println("\t" + boxEditorCommands.get("box").getBriefDescription());
        Printer.println("boxeditor");
        System.out.println("\tOpen or close a circuit box editor session.");
        Printer.println("exit");
        System.out.println("\tCloses the application.");
        Printer.printSeparatorLine("-");
    }

    /**
     * Handles the switching between box editor mode and normal mode.
     *
     * @param cmd command, split by spaces
     */
    private void handleEditorMode(String[] cmd) {
        if (cmd.length > 1) Printer.printErr(new TooManyArgumentException("boxeditor"));
        inBoxEditorMode = !inBoxEditorMode;
        if(inBoxEditorMode) {
            Printer.println("Now you are in box editor mode. After you run this command again, your boxes will be saved.");
        } else {
            Map<String, DeviceBundle> devices = boxEditorDevices.getMap();
            for(String name : devices.keySet()) {
                if(devices.get(name).getType().equals(DeviceType.CIRCUITBOX)) {
                    try {
                        CircuitBox box = (CircuitBox) devices.get(name).getDevice();
                        box.resetToDefaultState();
                        FileHandler.saveCircuit(box);
                        this.devices.add(name, devices.get(name));
                        Printer.println("[BoxEditorMode] " + name + " >> Saved and added to your current session!");
                    } catch (IOException  e) {
                        Printer.printErr("[BoxEditorMode] " + name + " >> Save failed!");
                    } catch (RedundantKeyException err) {
                        Printer.printErr("[BoxEditorMode] " + name + " >> Can't be added to the current session because a device already has this name!");
                    }
                }
            }
            boxEditorDevices = new DeviceMap();
            Printer.println("Now you are in normal mode!");
        }
    }
}
