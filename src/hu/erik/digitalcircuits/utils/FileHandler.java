package hu.erik.digitalcircuits.utils;

import hu.erik.digitalcircuits.devices.Cable;
import hu.erik.digitalcircuits.devices.CircuitBox;
import hu.erik.digitalcircuits.devices.Pin;

import java.io.*;
import java.util.ArrayList;

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
     * @param circuitName               name of the circuit which will be loaded
     * @return                          loaded circuit or null
     * @throws IOException              If the load fails.
     * @throws ClassNotFoundException   If the class doesn't exists in the classpath
     */
    public static CircuitBox loadCircuit(String circuitName) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(circuitName + ".ser"));
        CircuitBox box = (CircuitBox) inputStream.readObject();
        inputStream.close();
        return box;
    }

    /**
     * Save a given circuit.
     *
     * @param box circuitbox which will be saved
     */
    public static void saveCircuit(CircuitBox box) throws IOException {

        // Reset the whole circuit to a default state
        ArrayList<Cable> inputCables = resetPins(box.inputPins(), "input");
        ArrayList<Cable> outputCables = resetPins(box.outputPins(), "output");
        circuitRunThrough(box);

        // Save the circuit
        saveCircuitToFile(box);

        // Restore the circuit
        restorePins(box.inputPins(), inputCables, "input");
        restorePins(box.outputPins(), outputCables, "output");
        circuitRunThrough(box);
    }

    /**
     * Resets the given pins based their type, then returns the
     * connection cables that they had, in order to have the chance of
     * restoration.
     *
     * @param pins  pins to be reset
     * @param type  type of the pins like input or output
     * @return      list of the pin's connection cables
     */
    private static ArrayList<Cable> resetPins(Pin[] pins, String type) {
        ArrayList<Cable> cables = new ArrayList<>();
        for (Pin p : pins) {
            cables.add(p.getConnectionCable());
            p.setConnectionCable(null);
            p.setAvailability(true);
            if(type.equals("input")) p.setValue(false);
        }
        return cables;
    }

    /**
     * Restores the given pins to their original state.
     * If the type is set to input, pin will get it's value from
     * the other end of the cable.
     *
     * @param pins      pins to be reset
     * @param cables    cables which will be used in restoration
     * @param type      type of the pins like input or output
     */
    private static void restorePins(Pin[] pins, ArrayList<Cable> cables, String type) {
        for (int i = 0; i < cables.size(); i++) {
            if (cables.get(i) != null) {
                pins[i].setConnectionCable(cables.get(i));
                pins[i].setAvailability(false);
                if(type.equals("input")) pins[i].setValue(cables.get(i).getOtherPin(pins[i]).getValue());
            }
        }
    }

    /**
     * Runs through the whole circuit box to recalculate pin values.
     *
     * @param box circuit box to run through
     */
    private static void circuitRunThrough(CircuitBox box) {
        for (Pin p : box.inputPins()) {
            p.getParentDevice().calcOutput();
            p.getParentDevice().sendOutput();
        }
    }

    /**
     * Saves the given to a serialized file. File name will be the same as the box name.
     *
     * @param box           circuit box to save
     * @throws IOException  If the save fails.
     */
    private static void saveCircuitToFile(CircuitBox box) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(box.getName() + ".ser"));
        outputStream.writeObject(box);
        outputStream.close();
    }
}
