package hu.erik.digitalcircuits.cli;

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
     * One/two sentence description about the command.
     */
    private String briefDescription;

    /**
     * Constructor to setup the command's name, format and description.
     *
     * @param name              name of the command
     * @param format            format of the command
     * @param briefDescription  command description in one/two sentence
     */
    public Command(String name, String format, String briefDescription) {
        this.name = name;
        this.format = format;
        this.briefDescription = briefDescription;
    }

    /**
     * Returns the name of the command.
     *
     * @return the name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * Returns command description.
     *
     * @return One/two line description about the command.
     */
    public String getBriefDescription() {
        return briefDescription;
    }

    /**
     * Returns command format.
     *
     * @return How the command should be used.
     */
    public String getFormat() {
        return format;
    }

    /**
     * Handles the command functionality.
     *
     * @param storage                   cli data structure
     * @param cmd                       command, split by spaces
     * @throws NotEnoughArgsException   If the provided arguments aren't enough to preform an action.
     * @throws InvalidArgumentException If there are any invalid argument in the command.
     */
    public abstract void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException, InvalidArgumentException;

}
