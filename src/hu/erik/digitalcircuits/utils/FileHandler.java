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
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Circuit not found!");
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
            System.out.println("Circuit: [" + box.getName() + "] has been saved successfully!");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
