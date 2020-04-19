package hu.erik.digitalcircuits.utils;

/**
 * Util class to print nice looking messages to the console.
 */
public class Printer {
    private static int CONSOLEWIDTH = 120;

    /**
     * Prints the given text to the console with ">>" prefix.
     *
     * @param text Text you want to print to the console
     */
    public static void println(String text) {
        System.out.println(">> " + text);
    }

    /**
     * Prints a title to the console.
     *
     * @param title Text you want to print as a title
     */
    public static void printTitle(String title) {
        int remainingWidth = CONSOLEWIDTH - title.length() - 2;
        String side = lineBuilder("-", remainingWidth/2);
        System.out.println(side + " " + title + " " + side);
    }

    /**
     * Prints the given error's message with a ">>" prefix.
     *
     * @param e Exception that you want to print
     */
    public static void printErr(Exception e) {
        System.err.println(">> " + e.getMessage());
    }

    /**
     * Prints a simple separator line by duplicating the string that you gave it.
     *
     * @param material Material of the line
     */
    public static void printSeparatorLine(String material) {
        System.out.println(lineBuilder(material, CONSOLEWIDTH));
    }

    /**
     * Build a string line from the given string.
     *
     * @param material  The string which going to be repeated
     * @param length    How many time will this string repeats
     * @return          The built line as a string
     */
    private static String lineBuilder(String material, int length) {
        return material.repeat(Math.max(0, length));
    }
}
