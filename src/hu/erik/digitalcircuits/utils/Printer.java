package hu.erik.digitalcircuits.utils;

public class Printer {
    private static int CONSOLEWIDTH = 120;

    public static void println(String text) {
        System.out.println(">> " + text);
    }

    public static void printTitle(String title) {
        int remainingWidth = CONSOLEWIDTH - title.length() - 2;
        String side = lineBuilder("-", remainingWidth/2);
        System.out.println(side + " " + title + " " + side);
    }

    public static void printErr(Exception e) {
        System.err.println(">> " + e.getMessage());
    }

    public static void printSeparatorLine(String material) {
        System.out.println(lineBuilder(material, CONSOLEWIDTH));
    }

    private static String lineBuilder(String material, int length) {
        return material.repeat(Math.max(0, length));
    }
}
