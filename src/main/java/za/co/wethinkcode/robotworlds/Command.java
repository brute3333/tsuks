package za.co.wethinkcode.robotworlds;


import org.json.JSONException;

import za.co.wethinkcode.robotworlds.Commands.*;

public abstract class Command {
    private final String name;
    protected String argument;



    public abstract boolean execute(Robot target) throws JSONException;

    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument = " ";
    }

    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    public String getName() {                                                                           //<2>
        return name;
    }

    public String getArgument() {
        return this.argument;
    }

    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        switch (args[0]) {
            case "invalid":
                return new CommandHandler();
            case "off":
                return new QuitCommand();
            case "forward":
                return new ForwardCommand(args[1]);
            case "back":
                return new BackCommand(args[1]);
            case "launch":
                return new LaunchCommand(args[0], args[1]);
            case "state":
                return new StateCommand();
            case "dump":
                return new DumpCommand();
            case "turn":
                if (args.length == 2 && args[1].equals("left")) {
                    return new LeftCommand();
                } else if (args.length == 2 && args[1].equals("right")) {
                    return new RightCommand();
                }
            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }


    public String getCommand() {
        return getName();
    }

    public void setArgument(String argument) {
        this.argument = argument.trim();
    }

}
