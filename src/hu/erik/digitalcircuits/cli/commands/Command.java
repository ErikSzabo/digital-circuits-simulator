package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;

/**
 * Abstract class to create Commands.
 */
public abstract class Command {
    private String name;

    /**
     * Constructor to set up the command's name.
     *
     * @param name Name of the command.
     */
    public Command(String name) {
        this.name = name;
    }

    /**
     * @return Returns the name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * Method which is going to handle the command functionality.
     *
     * @param storage                   Cli data structure
     * @param cmd                       Command splitted by spaces
     * @throws NotEnoughArgsException   When the provided arguments aren't enough to preform an action
     */
    public abstract void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException;

}
