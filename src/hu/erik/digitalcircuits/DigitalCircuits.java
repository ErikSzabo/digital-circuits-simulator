package hu.erik.digitalcircuits;

import hu.erik.digitalcircuits.devices.*;
import hu.erik.digitalcircuits.errors.PinAlreadyInUseException;
import hu.erik.digitalcircuits.errors.PinNotExistsException;
import hu.erik.digitalcircuits.utils.FileHandler;

public class DigitalCircuits {
    public static void kotelezoFeladat() {
        // FONTOS:
        // Csak a kapcsolókkal módsítsuk a bemenetet!
        // Itt az egyszerű connectet használtam ami mindent megcsinál helyettem, de van egy
        // "bonyolultabb", ahol a kapcsolodó pin-t is ki lehet választani.
        // Egy pines eszközök esetén ennek sok értelme nincs. Ha nem a 0-ás pint választjuk kivétel fog dobódni.


        // Szükségünk van egy áramforrásra, hogy a kapcsolók működjenek
        // alap állapotában nem biztosít áramellátást, majd be kell kapcsolni
        PowerSource elem = new PowerSource();

        // Kell majd egy csomópont is hogy az áramforrást több kapcsolóra is rá tudjuk kötni
        Junction csomopont = new Junction(5);

        // Ezek lesznek a kombinációs hálózat változói. Meg amúgy a kapcsolók
        // A képvéseli a legnagyobb helyiértéket
        // Alap állapotában le van kapcsolva, tehát 0
        Switch A = new Switch();
        Switch B = new Switch();
        Switch C = new Switch();
        Switch D = new Switch();
        Switch E = new Switch();

        // Az adott feladatot legegyszerűbben egy Vagy kapuval lehetne megoldani.
        // Úgyhogy ezért most 1 db NAND kapuval fogjuk megcsinálni
        // Maga a feladat: 5 változós kombinációs hálózat, ami az 5 bemenetre 0-át ad egyébként 1-et
        // Itt az őt az input pinek számát adja meg. Lehet külön megcsinálni ezeket a pineket, de sok fölösleges írás lenne
        NandGate NandKapu = new NandGate(5);

        // Szükségünk lesz 3 inverterre is
        Inverter inverter1 = new Inverter();
        Inverter inverter2 = new Inverter();
        Inverter inverter3 = new Inverter();

        // Most hogy minden kapcsolónk, csomópontunk áramunk, kapunk megvan,
        // Kössük össze és állítsuk be.
        // Fontos hogy elöszőr összekötünk mindent, és csak utána szórakozúnk a kapcsolókkal!
        // Elem hozzákötése a csomóponthoz
        elem.connect(csomopont);

        // csomopont kimeneteit összekötjük a kapcsolók bemeneteivel
        csomopont.connectAll(A, B, C, D, E);

        // Összekötjük a kapcsolók kimeneteit a NAND kapunkkal
        A.connect(inverter1);
        inverter1.connect(NandKapu);
        B.connect(inverter2);
        inverter2.connect(NandKapu);
        C.connect(NandKapu);
        D.connect(inverter3);
        inverter3.connect(NandKapu);
        E.connect(NandKapu);


        // Kapcsoljuk be az áramot
        elem.on();

        // Most hogy mindent összekötüttünk, állítsuk be a kapcsolókat az 5-ös számkombinációra
        // Mivel a kapcsolók alapból 0 jelet továbbítanak, elég csak a két megfelelő kapcsolót 1-be rakni
        C.on();
        E.on();
        try {
            // Most az elvárt kimenet 0 mivel ezt kérte a feladat
            // Nézzük meg tényleg 0-ban van-e a Nand kapu output pin-je.
            System.out.println("5 bementei kombinációra a függvény kimenete: " + NandKapu.getOutputPin(0).getValue());

            // Most nézzük meg mi lesz ha átállítjuk a kapcsolakat
            // Billentsük le C kapcsolót és mondjuk A-t fel
            C.off();
            A.on();
            System.out.println("Nem 5-ös bemeneti kombinációra a függvény kimenete: " + NandKapu.getOutputPin(0).getValue());

            // További tesztek
            System.out.println("----------------------- Tesztek helyes működésre -----------------------");
            B.on();
            E.on();
            System.out.println("Nem 5-ös bemeneti kombinációra a függvény kimenete: " + NandKapu.getOutputPin(0).getValue());
            C.on();
            A.off();
            System.out.println("Nem 5-ös bemeneti kombinációra a függvény kimenete: " + NandKapu.getOutputPin(0).getValue());
        } catch (PinNotExistsException err) {
            System.err.println(err.getMessage());
        }

    }

    public static void szavazogep() {

        // Feladat leírás:

        // Van 4 emberünk. Ebből egy ember (A) a polgármester.
        // A függvény akkor adjon 1-es értéket ha legalább 3an szavaztak igennel!
        // Vagy ha 2-en szavaztak igennel, de ebből az egyikük a polgármester (A).
        // F(A, B, C, D) = AB + AC + AD + BCD
        // Most higgyük el egyelőre hogy ez helyes felírás, majd később úgyis kpróbáljuk.

        // áramforrás
        PowerSource aram = new PowerSource();

        // csomópont
        // (Nem akarunk minden kapcsolónak külön áramforrást ezért vezetjük át ezen az összes kapcsolóba később)
        Junction csomopont = new Junction(4);

        // változók, avagy kapcsolók (kapcsolók nem működnek aktív áramforrás nélkül)
        Switch A = new Switch();
        Switch B = new Switch();
        Switch C = new Switch();
        Switch D = new Switch();

        // Ehhez a függvényhez kéne 4 db ÉS kapu és 1 db VAGY kapu
        // Vagy 5 db NAND kapu, de a változatosság kedvéért hagyjuk most a NAND kaput.
        AndGate andAB = new AndGate(2);
        AndGate andAC = new AndGate(2);
        AndGate andAD = new AndGate(2);
        AndGate andBCD = new AndGate(3);

        // Itt már látszik hogy Ezeket a változókat egyszerre több helyre is be kéne kötni.
        // Eljött hát az idő felvenni mindnek egy-egy csomópontot.
        Junction csomopontA = new Junction(3);
        Junction csomopontB = new Junction(2);
        Junction csomopontC = new Junction(2);
        Junction csomopontD = new Junction(2);

        // Akkor kéne még egy VAGY kapu ami összeköti az ÉS kapukat.
        OrGate OR = new OrGate(4);

        // Csináljunk neki egy dobozt, hogy megmutassuk, ilyet is lehet.
        CircuitBox box = new CircuitBox("szavazogep", 4, 1);

        // Rakjuk is össze
        // áram rákapcsolása a csomópontra, majd kapcsolók csomópontra kötése
        aram.connect(csomopont);
        csomopont.connectAll(A, B, C, D);
        csomopontA.connectAll(andAB, andAC, andAD);
        csomopontB.connectAll(andAB, andBCD);
        csomopontC.connectAll(andAC, andBCD);
        csomopontD.connectAll(andAD, andBCD);
        andAB.connect(OR);
        andAC.connect(OR);
        andAD.connect(OR);
        andBCD.connect(OR);

        // na most akkor minden csomopontot bindoljunk rá a boxra
        // tehat a csomópont minden input pinjét (1 db) szeretnén sorba bindolni
        box.bindInputPin(csomopontA, 0, 0);
        box.bindInputPin(csomopontB, 0, 1);
        box.bindInputPin(csomopontC, 0, 2);
        box.bindInputPin(csomopontD, 0, 3);
        box.bindOutputPin(OR, 0, 0);

        // Mentsük el ezt az áramkört hogy legközelebb csak beolvasni kelljen, ne újra megírni.
        FileHandler.saveCircuit(box);
        CircuitBox ujBox = FileHandler.loadCircuit("szavazogep");

        // Akkor most kéne a kapcsolókat a helyes sorrendben rákötni a dobozra
        A.connect(ujBox);
        B.connect(ujBox);
        C.connect(ujBox);
        D.connect(ujBox);

        // Kapcsoljuk fel az áramot
        aram.on();

        try {
            // Hamis (0)
            A.on();
            System.out.println("Ha csak a polgármester szavazott igennel akkor a kimenetnek false-nak kéne lennie");
            System.out.println("Kimenet: " + ujBox.getOutputPin(0).getValue());

            // Igaz (1)
            // Most épp a polgármester szavazója már igenre van állítva, adjunk neki még egy társat
            B.on();
            System.out.println("Mostmár B is igennel szavazott így a kimenetnek true-nak kéne lennie");
            System.out.println("Kimenet: " + ujBox.getOutputPin(0).getValue());

            // Hamis (0)
            A.off();
            C.on();
            System.out.println("Most 2 ember szavazott igennel de a polgármester nincs köztük. Kimenetnek false-nak kéne lennie.");
            System.out.println("Kimenet: " + ujBox.getOutputPin(0).getValue());
        } catch (PinNotExistsException err) {
            System.err.println(err.getMessage());
        }


//         NOTE: ez a dobozolás azért volt jó mert igy egy blacboxba be tudunk rakni egy mar elkészített
//         áramkört. Ezt menthetjük is fájlba. utána be lehet olvasni és csak hasznalni kell. lehet több input/outputpinje.
    }

    public static void main(String[] args) throws PinAlreadyInUseException, PinNotExistsException {
        kotelezoFeladat();
        System.out.println("-----------------------------------------------------");
        szavazogep();
    }
}
