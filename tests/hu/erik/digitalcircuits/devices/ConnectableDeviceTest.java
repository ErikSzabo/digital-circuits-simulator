package hu.erik.digitalcircuits.devices;

import hu.erik.digitalcircuits.errors.NoMorePinException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the automatic connect and disconnect methods inside
 * the ConnectableDevice class.
 */
class ConnectableDeviceTest {
    Device d1, d2;

    /**
     * Set up the default devices before every test method.
     * For simplicity, one of the device will be an AndGate and the
     * other one an Inverter.
     */
    @BeforeEach
    void setUp() {
        d1 = new AndGate(2);
        d2 = new Inverter();
    }

    /**
     * Tests the automatic connect method.
     * After connect, the pins should have connection cables.
     * Pin values should update automatically.
     */
    @Test
    void testConnect() {
        d1.getAllInputPins()[0].setValue(true);
        d1.getAllInputPins()[1].setValue(true);
        // now the and gates output should be true
        d1.calcOutput();
        // Connect andgate and inverter
        assertThrows(NoMorePinException.class, () -> {
            d1.connect(d2);
        });
        // now andgate output and inverter input should have connection cable
        assertNotNull(d1.getAllOutputPins()[0].getConnectionCable());
        assertNotNull(d2.getAllInputPins()[0].getConnectionCable());
        // inverter input should be true, and output should be false
        // because of dynamic transfer connection
        assertTrue(d2.getAllInputPins()[0].getValue());
        assertFalse(d2.getAllOutputPins()[0].getValue());
    }

    /**
     * Tests disconnect method.
     * After disconnect, there shouldn't be any connection cable.
     * Pin values should update automatically.
     */
    @Test
    void testDisconnect() {
        d1.getAllInputPins()[0].setValue(true);
        d1.getAllInputPins()[1].setValue(true);
        // now the and gates output should be true
        d1.calcOutput();
        // Connect andgate and inverter
        assertThrows(NoMorePinException.class, () -> {
            d1.connect(d2);
        });
        // Disconnect them
        d1.disconnect(d2);
        // now andgate output and inverter input shouldn't have connection cable
        assertNull(d1.getAllOutputPins()[0].getConnectionCable());
        assertNull(d2.getAllInputPins()[0].getConnectionCable());
        // inverter input should be false, and output should be true
        // because of dynamic transfer connection
        assertFalse(d2.getAllInputPins()[0].getValue());
        assertTrue(d2.getAllOutputPins()[0].getValue());
    }
}