package za.co.wethinkcode.robotworlds.Commands;

import org.json.JSONException;
import za.co.wethinkcode.robotworlds.Command;
import za.co.wethinkcode.robotworlds.Json.ResponseHandler;
import za.co.wethinkcode.robotworlds.Robot;

public class CommandHandler extends Command {
    private Robot target;
    private ResponseHandler response;
    private String command;

    public CommandHandler() {
        super("invalid");
        response = new ResponseHandler();
    }

    public CommandHandler(Robot target) {
        super("invalid");
        this.response = new ResponseHandler();
        this.target = target; // Assign the Robot instance
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public boolean commandHandle(String command, Robot target) throws JSONException {
        if (command.equals("forward")) {
            ForwardCommand forwardCommand = new ForwardCommand("");
            forwardCommand.setArgument("");
            return forwardCommand.execute(target);
        } else if (command.equals("back")) {
            BackCommand backCommand = new BackCommand("");
            backCommand.setArgument("");
            return backCommand.execute(target);
        } else if (command.equals("turn")) {
            TurnCommand turnCommand = new TurnCommand();
            turnCommand.setArgument("");
            return turnCommand.execute(target);
        } else if (command.equals("launch")) {
            LaunchCommand launchCommand = new LaunchCommand("", "");
            launchCommand.setArgument(" ");
            return launchCommand.execute(target);
        } else if (command.equals("state")) {
            StateCommand stateCommand = new StateCommand();
            return stateCommand.execute(target);
        } else {
            target.setStatus(response.invalidCommand());
            return true;
        }
    }

    @Override
    public boolean execute(Robot target) throws JSONException {
        return commandHandle(command, target);
    }
}
