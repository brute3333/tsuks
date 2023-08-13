package za.co.wethinkcode.robotworlds.Commands;

import org.json.JSONException;
import za.co.wethinkcode.robotworlds.Command;
import za.co.wethinkcode.robotworlds.Json.ResponseHandler;
import za.co.wethinkcode.robotworlds.Robot;

public class StateCommand extends Command {
    private ResponseHandler response;

    public StateCommand() {
        super("state");
        response = new ResponseHandler();
    }

    @Override
    public boolean execute(Robot target) throws JSONException {
        response.setDirection(target.getWorld().getCurrentDirection().toString());
        target.setStatus(response.setState(target.getWorld().getPosition().toString()));
        return true;
    }
}
