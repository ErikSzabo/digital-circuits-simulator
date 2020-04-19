package hu.erik.digitalcircuits.utils;

import hu.erik.digitalcircuits.devices.CircuitBox;

import java.io.*;

/**
 * Class to save and load circuits.
 */
public class FileHandler {

    /**
     * Load a circuit with the given name.
     *
     * @param circuitName Name of the circuit you want to load
     * @return Loaded circuit or null
     */
    public static CircuitBox loadCircuit(String circuitName) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(circuitName + ".ser"));
            CircuitBox box = (CircuitBox) inputStream.readObject();
            inputStream.close();
            return box;
        } catch (IOException err) {
            Printer.printErr(err);
            return null;
        } catch (ClassNotFoundException err) {
            Printer.printErr(new Exception("Error while loading circuit!"));
            return null;
        }
    }

    /**
     * Save a given circuit.
     *
     * @param box CircuitBox you want to save
     */
    public static void saveCircuit(CircuitBox box) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(box.getName() + ".ser"));
            outputStream.writeObject(box);
            outputStream.close();
        } catch (IOException err) {
            Printer.printErr(err);
        }
    }
}
