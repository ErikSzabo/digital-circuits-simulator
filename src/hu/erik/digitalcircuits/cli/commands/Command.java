package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.errors.InvalidArgumentException;
import hu.erik.digitalcircuits.errors.NotEnoughArgsException;

/**
 * Abstract class to create Commands.
 */
public abstract class Command {
    /**
     * Name of the command.
     */
    private String name;
    /**
     * How the command should be used. Format first word must be the name.
     */
    private String format;
    /**
     * One/two line description about the command.
     */
    private String briefDescription;

    /**
     * Constructor to setup the command's name.
     *
     * @param name Name of the command.
     */
    public Command(String name, String format, String briefDescription) {
        this.name = name;
        this.format = format;
        this.briefDescription = briefDescription;
    }

    /**
     * @return the name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * @return One/two line description about the command.
     */
    public String getBriefDescription() {
        return briefDescription;
    }

    /**
     * @return How the command should be used.
     */
    public String getFormat() {
        return format;
    }

    /**
     * Handles the command functionality.
     *
     * @param storage                   cli data structure
     * @param cmd                       command, splitted by spaces
     * @throws NotEnoughArgsException   If the provided arguments aren't enough to preform an action.
     * @throws InvalidArgumentException If there are any invalid argument in the command.
     */
    public abstract void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException, InvalidArgumentException;

}
