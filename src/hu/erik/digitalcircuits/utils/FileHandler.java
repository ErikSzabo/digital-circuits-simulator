package hu.erik.digitalcircuits.utils;

import hu.erik.digitalcircuits.devices.CircuitBox;

import java.io.*;

public class FileHandler {

    public static CircuitBox loadCircuit(String circuitName) {
        try {
            FileInputStream fileIn = new FileInputStream(circuitName + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            CircuitBox b = (CircuitBox) in.readObject();
            in.close();
            fileIn.close();
            return b;
        } catch (IOException i) {
            Printer.printErr(i);
            return null;
        } catch (ClassNotFoundException c) {
            Printer.printErr(new Exception("Error while loading class!"));
            c.printStackTrace();
            return null;
        }
    }

    public static void saveCircuit(CircuitBox box) {
        try {
            FileOutputStream fileOut = new FileOutputStream(box.getName() + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(box);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
