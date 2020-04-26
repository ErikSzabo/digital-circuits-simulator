package hu.erik.digitalcircuits.utils;

/**
 * Util class to print nice looking messages to the console.
 */
public final class Printer {
    /**
     * Width of the console in characters.
     */
    private static final int CONSOLEWIDTH = 120;

    /**
     * Private constructor to prevent instance creation.
     */
    private Printer() {}

    /**
     * Prints the given text to the console with "{@literal >>}" prefix.
     *
     * @param text text which will be printed to the console
     */
    public static void println(String text) {
        System.out.println(">> " + text);
    }

    /**
     * Prints a title to the console.
     *
     * @param title text which will be printed as a title
     */
    public static void printTitle(String title) {
        int remainingWidth = CONSOLEWIDTH - title.length() - 2;
        String side = lineBuilder("-", remainingWidth/2);
        System.out.println(side + " " + title + " " + side);
    }

    /**
     * Prints the given error's message with a "{@literal >>}" prefix.
     *
     * @param e exception which will be printed
     */
    public static void printErr(Exception e) {
        System.err.println(">> " + e.getMessage());
    }

    /**
     * Prints the given error's message with a "{@literal >>}" prefix.
     *
     * @param msg error message which will be printed
     */
    public static void printErr(String msg) {
        System.err.println(">> " + msg);
    }

    /**
     * Prints a simple separator line by repeating the given string material.
     *
     * @param material material of the line
     */
    public static void printSeparatorLine(String material) {
        System.out.println(lineBuilder(material, CONSOLEWIDTH));
    }

    /**
     * Builds a line from the given string and returns it.
     *
     * @param material  the string which going to be repeated
     * @param length    how many time will this string repeats
     * @return          the built line as a string
     */
    private static String lineBuilder(String material, int length) {
        return material.repeat(Math.max(0, length));
    }
}
