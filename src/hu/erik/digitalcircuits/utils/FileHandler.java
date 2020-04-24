package hu.erik.digitalcircuits.utils;

import hu.erik.digitalcircuits.devices.CircuitBox;
import hu.erik.digitalcircuits.devices.Pin;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to save and load circuits.
 */
public final class FileHandler {
    /**
     * Private default constructor to prevent instance creation.
     */
    private FileHandler() {}

    /**
     * Loads a circuit with the given name or null if there isn't any
     * circuit with the given name.
     *
     * @param circuitName   name of the circuit which will be loaded
     * @return              loaded circuit or null
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
            Printer.printErr("Error while loading circuit!");
            return null;
        }
    }

    /**
     * Save a given circuit.
     *
     * @param box circuitbox which will be saved
     */
    public static void saveCircuit(CircuitBox box) {
        ArrayList<Pin> pins = new ArrayList<>();
        pins.addAll(Arrays.asList(box.getAllInputPins()));
        pins.addAll(Arrays.asList(box.getAllOutputPins()));
        try {
            for(Pin p : pins) {
                if(!p.isFree()) {
                    Printer.printErr("You can only save a box if its input and output pins are not connected to anything!");
                    return;
                }
            }
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(box.getName() + ".ser"));
            outputStream.writeObject(box);
            outputStream.close();
            Printer.println("Saved successfully!");
        } catch (IOException err) {
            Printer.printErr(err);
        }
    }
}
