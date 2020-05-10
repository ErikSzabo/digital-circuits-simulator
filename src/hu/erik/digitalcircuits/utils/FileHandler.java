package hu.erik.digitalcircuits.utils;

import hu.erik.digitalcircuits.devices.CircuitBox;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class to handle file interactions.<br>
 * Can load and save CircuitBoxes.<br>
 * Can load command help pages.
 */
public final class FileHandler {
    /**
     * Private default constructor to prevent instance creation.
     */
    private FileHandler() {}

    /**
     * Loads a circuit from file if there is any .ser file with the given name.
     *
     * @param circuitName               name of the circuit which will be loaded
     * @return                          loaded circuit
     * @throws IOException              If the load fails.
     * @throws ClassNotFoundException   If the class doesn't exists in the classpath.
     */
    public static CircuitBox loadCircuit(String circuitName) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(circuitName + ".ser"));
        CircuitBox box = (CircuitBox) inputStream.readObject();
        inputStream.close();
        return box;
    }

    /**
     * Saves a circuit to a file.
     *
     * @param box           CircuitBox which will be saved
     * @throws IOException  If the save fails.
     */
    public static void saveCircuit(CircuitBox box) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(box.getName() + ".ser"));
        outputStream.writeObject(box);
        outputStream.close();
    }

    /**
     * Reads a help page from a txt file into a String and returns it.
     *
     * @param filename      name of the file that contains the help page
     * @return              the help page in a single String
     * @throws IOException  If the read fails.
     */
    public static String readHelpPage(String filename) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filename + ".txt"));
        return new String(encoded, Charset.defaultCharset());
    }
}
