package hu.erik.digitalcircuits.cli;

import hu.erik.digitalcircuits.errors.NotEnoughArgsException;
import hu.erik.digitalcircuits.errors.TooManyArgumentException;
import hu.erik.digitalcircuits.utils.FileHandler;
import hu.erik.digitalcircuits.utils.Printer;

import java.io.IOException;
import java.util.HashMap;

/**
 * Class to handle commands prefixed with "help".
 */
public class HelpCmd extends Command {
    /**
     * Help pages that Help command can use based on a device type.
     * Based on device type, it has O(1) complexity reach.
     * Stores device type String as a key, and help page String as value.
     */
    private HashMap<String, String> helpPages;

    /**
     * Constructor to setup the command's name, format, description and its possible actions.
     */
    public HelpCmd() {
        super(
                "help",
                "help <devicetype>",
                "Shows help for the specified device type. You can view here the unique methods for a device."
        );
        this.helpPages = new HashMap<>();
        for(DeviceType type : DeviceType.values()) {
            try {
                helpPages.put(type.getValue(), FileHandler.readHelpPage(type.getValue()));
            } catch (IOException e) {
                Printer.printErr(type + " help page can't be loaded!");
            }
        }
    }

    /**
     * Writes the required help page (based on DeviceType) to the console.<br>
     *
     * Command format:<br>
     * help {@literal <}device type{@literal >}
     *
     * @param storage                   cli data structure
     * @param cmd                       command, split by spaces
     * @throws NotEnoughArgsException   If the number of arguments are less then 1.
     */
    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        if(cmd.length < 2) throw new NotEnoughArgsException(cmd[0], 1, cmd.length - 1);
        if(cmd.length > 2) Printer.printErr(new TooManyArgumentException(cmd[0]));

        String page = helpPages.get(cmd[1]);
        if(page == null) {
            Printer.printErr("This help page is not exists! Try: " + getFormat());
        } else {
            Printer.printSeparatorLine("-");
            Printer.println(helpPages.get(cmd[1]));
            Printer.printSeparatorLine("-");
        }

    }
}
