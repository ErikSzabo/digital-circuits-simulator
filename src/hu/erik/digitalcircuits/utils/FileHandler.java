package hu.erik.digitalcircuits.utils;

import hu.erik.digitalcircuits.devices.CircuitBox;

import java.io.*;

public class FileHandler {

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
