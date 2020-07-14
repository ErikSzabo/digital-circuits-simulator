# Digital Circuits

Created because of a school project. You can create, save and load circuits.
Also you can, do simulation with them.

Requirements
- java 11 beacuse of 2 string methods (I think strip and repeat) otherwise it works fine with 1.8

# How to use?

- menu
	- Shows this menu.
- devicetypes
	- Lists all available device type.
- disconnect <name> from <name>
	- Disconnects the first device output pins from the second device input pins.
- help <devicetype>
	- Shows help for the specified device type. You can view here the unique methods for a device.
- show <input | output> <name> <index>
	- Show the current value for the given pin on the given device.
- create <type> <name> [inputnum] [outputnum]
	- Creates a device with the given parameters.
- list [type]
	- Lists all available device type.
- device <name> <uniqe method> <args...>
	- Access device specific functions.
- delete <name>
	- Delete a device with the given name.
- connect <name> to <name>
	- Connects the first device first free output pin to the second device first free input pin.
- box <name> create <inputnum> <outputnum>
	- box <name> <bindinput | bindoutput> <target name> <target pin index> <box pin index>
	Creates a box with the given parameters or bind its pins. Only works in Box Editor Mode
- boxeditor
	- Open or close a circuit box editor session.
-  exit
    - Closes the application.