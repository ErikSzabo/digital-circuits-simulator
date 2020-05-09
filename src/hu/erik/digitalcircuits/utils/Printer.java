package hu.erik.digitalcircuits.utils;

/**
 * Util class to print nice looking messages to the console.
 */
public final class Printer {
    /**
     * Width of the console in characters.
     * This may vary in... most of the cases.
     * However on windows 10, this is the default console
     * width in characters.
     */
    private static final int CONSOLEWIDTH = 120;

    /**
     * Private constructor to prevent instance creation.
     */
    private Printer() {}

    /**
     * Prints text to the console with "{@literal >>}" prefix.
     *
     * @param text text which will be printed to the console
     */
    public static void println(String text) {
        System.out.println(">> " + text);
    }

    /**
     * Prints a title to the console. Title text will be
     * surrounded by lines, so the title will span across the console.
     *
     * @param title text which will be printed as a title
     */
    public static void printTitle(String title) {
        int remainingWidth = CONSOLEWIDTH - title.length() - 2;
        String side = lineBuilder("-", remainingWidth/2);
        System.out.println(side + " " + title + " " + side);
    }

    /**
     * Prints the given error's message with a "{@literal >>}" prefix to the standard error.
     *
     * @param e exception which will be printed
     */
    public static void printErr(Exception e) {
        System.err.println(">> " + e.getMessage());
    }

    /**
     * Prints the given message with a "{@literal >>}" prefix to the standard error.
     *
     * @param msg error message which will be printed
     */
    public static void printErr(String msg) {
        System.err.println(">> " + msg);
    }

    /**
     * Prints a simple separator line by repeating the given string material.
     * This line will span across the console by repeating the String CONSOLEWIDTH times.
     * In order to work properly, as the argument, a single character String should be passed.
     *
     * @param material material of the line
     */
    public static void printSeparatorLine(String material) {
        System.out.println(lineBuilder(material, CONSOLEWIDTH));
    }

    /**
     * Builds a line from the given string (repeated by x times) and returns it.
     *
     * @param material  the string which going to be repeated
     * @param length    how many times will this string repeats
     * @return          the built line as a string
     */
    private static String lineBuilder(String material, int length) {
        return material.repeat(Math.max(0, length));
    }
}
