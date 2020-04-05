package hu.erik.digitalcircuits.cli.commands;

import hu.erik.digitalcircuits.cli.DeviceMap;
import hu.erik.digitalcircuits.cli.DeviceType;
import hu.erik.digitalcircuits.errors.clierror.InvalidArgumentException;
import hu.erik.digitalcircuits.errors.clierror.NotEnoughArgsException;
import hu.erik.digitalcircuits.utils.Printer;

public class HelpCmd extends Command {
    public HelpCmd(String name) {
        super(name);
    }

    @Override
    public void action(DeviceMap storage, String[] cmd) throws NotEnoughArgsException {
        // FORMAT
        // help <type>

        if(cmd.length < 2) throw new NotEnoughArgsException(cmd[0], 2, cmd.length - 1);

        if(!DeviceType.ALL.contains(cmd[1].toLowerCase())) {
            Printer.printErr(new InvalidArgumentException(cmd[0], cmd[1]));
            return;
        }

        if(cmd[1].toLowerCase().contains("gate") || cmd[1].equalsIgnoreCase(DeviceType.INVERTER)) {
            writeGatesHelpPage();
        } else if(cmd[1].equalsIgnoreCase(DeviceType.POWER)) {
            writePowerHelpPage();
        } else if(cmd[1].equalsIgnoreCase(DeviceType.SWITCH)) {
            writeSwitchHelpPage();
        } else if(cmd[1].equalsIgnoreCase(DeviceType.JUNCTION)) {
            writeJunctionHelpPage();
        } else if(cmd[1].equalsIgnoreCase(DeviceType.CIRCUITBOX)) {
            writeCircuitBoxHelpPage();
        }


    }

    private void writePowerHelpPage() {
        System.out.println("PowerSource\n" +
                "\n" +
                "Used for giving eletricity to switches, or to generate constant 0 or 1 signal to a device.\n" +
                "Important thing, that a Switch will never work without a connected PowerSource.\n" +
                "\n" +
                "To create: \"create powersource <name>\"\n" +
                "\n" +
                "Uniqe functions:\n" +
                "on - turns on the electricity\n" +
                "off - turns off the electricity\n" +
                "\n" +
                "You can use these uniqe functions by typing \"device <your powersource name> <on or off>\"");
    }

    private void writeSwitchHelpPage() {
        System.out.println("Switch\n" +
                "\n" +
                "Used to simulate circuit variables. You can set your variables in your circuits with switches.\n" +
                "Switch won't work unless it's connected to a PowerSource.\n" +
                "\n" +
                "To create: \"create switch <name>\"\n" +
                "\n" +
                "Uniqe functions:\n" +
                "on - generete 1 on the output\n" +
                "off - generate 0 on the output\n" +
                "\n" +
                "You can use these uniqe functions by typing \"device <your switch name> <on or off>\"");
    }

    private void writeJunctionHelpPage() {
        System.out.println("Junction\n" +
                "\n" +
                "Junctions are usally used to duplicate signals. For example, you can connect your PowerSource to a\n" +
                "Junction, and then you can connect the junction to all of your switches.\n" +
                "\n" +
                "To create: \"create junction <name> <output pin number>\"\n" +
                "\n" +
                "Junction devices gets access to a special connectall function. With this, you can connect multiple devices\n" +
                "to your Junction at the same time\n" +
                "\n" +
                "To use this uniqe function, type: \"device <your junction name> connectall <you device names>\"");
    }

    private void writeGatesHelpPage() {
        System.out.println("Gates\n" +
                "\n" +
                "A Gate or Inverter is responsible for the circuit logic.\n" +
                "For more information about gates, visit Google :) \n" +
                "\n" +
                "To create a Gate: \"create <gatetype or inverter> <name> [input pin number if it is a gate] ");
    }

    private void writeCircuitBoxHelpPage() {
        System.out.println("CircuitBox\n" +
                "\n" +
                "With this tool, you can put your whole circuit to box. And the reason to that, well, you can save and load\n" +
                "your boxes. So next time if you want to use the same circuit, the only thing you need is to load your box.\n" +
                "\n" +
                "To create: \"create circuitbox <name> <box input pins> <box output pins>\"\n" +
                "\n" +
                "- Bind your circuit pins to the box:\n" +
                "     - To bind an input pin, type: \n" +
                "          \"device <box name> bindinputpin <target device name> <target pin index> <box pin index>\"\n" +
                "     - To bind an output pin, type:\n" +
                "          \"device <box name> bindoutputpin <target device name> <target pin index> <box pin index>\"\n" +
                "     In order to bind pins, the target device pins can't be connected to anything.\n" +
                "\n" +
                "- Save and load\n" +
                "      - To save, type: \"device <box name> save\"\n" +
                "      - To load, type: \"device <boxname> load\"\n" +
                "     After loading, you can connect your box as usual.");
    }

}
