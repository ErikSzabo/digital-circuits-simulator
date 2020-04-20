package hu.erik.digitalcircuits.utils;

import hu.erik.digitalcircuits.devices.CircuitBox;
import hu.erik.digitalcircuits.devices.Pin;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

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
        ArrayList<Pin> pins = new ArrayList<>();
        pins.addAll(Arrays.asList(box.getAllInputPins()));
        pins.addAll(Arrays.asList(box.getAllOutputPins()));
        try {
            for(Pin p : pins) {
                if(!p.isFree()) {
                    Printer.printErr(new Exception("You can only save a box if its input and output pins are not connected to anything!"));
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
