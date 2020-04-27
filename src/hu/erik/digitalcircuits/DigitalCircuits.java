package hu.erik.digitalcircuits;

import hu.erik.digitalcircuits.cli.*;
import hu.erik.digitalcircuits.devices.*;
import hu.erik.digitalcircuits.errors.NoMorePinException;
import hu.erik.digitalcircuits.utils.Printer;

public class DigitalCircuits {
    public static void kotelezoFeladat() {
        PowerSource elem = new PowerSource();
        elem.on();

        Junction csomopont = new Junction(5);

        Switch A = new Switch();
        Switch B = new Switch();
        Switch C = new Switch();
        Switch D = new Switch();
        Switch E = new Switch();

        NandGate NandKapu = new NandGate(5);

        Inverter inverter1 = new Inverter();
        Inverter inverter2 = new Inverter();
        Inverter inverter3 = new Inverter();

        try {
            elem.connect(csomopont);
        }  catch (NoMorePinException err) {
            Printer.printErr(err);
        }

        try {
            csomopont.connectAll(A, B, C, D, E);
        } catch (NoMorePinException err) {
            Printer.printErr(err);
        }

        try {
            A.connect(inverter1).connect(NandKapu);
            B.connect(inverter2).connect(NandKapu);
            C.connect(NandKapu);
            D.connect(inverter3).connect(NandKapu);
            E.connect(NandKapu);
        } catch (NoMorePinException err) {
            Printer.printErr(err);
        }

        C.on();
        E.on();

        System.out.println("5 bementei kombinációra a függvény kimenete: " + NandKapu.outputPins()[0].getSignal());
        C.off();
        A.on();
        System.out.println("Nem 5-ös bemeneti kombinációra a függvény kimenete: " + NandKapu.outputPins()[0].getSignal());

    }

    /**
     * Application entry point.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        //kotelezoFeladat();
        CliController cliController = new CliController();
        cliController.addCommands(
                new ConnectCmd(), new CreateCmd(), new DeleteCmd(),
                new DeviceCmd(), new DeviceTypesCmd(), new DisconnectCmd(),
                new HelpCmd(), new ListCmd(), new ShowCmd()
        );
        cliController.listen();
    }
}
