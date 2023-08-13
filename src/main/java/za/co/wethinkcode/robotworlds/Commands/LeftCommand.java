package za.co.wethinkcode.robotworlds.Commands;

import org.json.JSONException;
import za.co.wethinkcode.robotworlds.Command;
import za.co.wethinkcode.robotworlds.Json.ResponseHandler;
import za.co.wethinkcode.robotworlds.Robot;

public class LeftCommand extends Command {
    private ResponseHandler response;

    public LeftCommand() {
        super("left");
        response = new ResponseHandler();
    }

    @Override
    public boolean execute(Robot target) throws JSONException {
        target.getWorld().updateDirection(false);
        response.setDirection(target.getWorld().getCurrentDirection().toString());
        target.setStatus(response.movementResponse(target.getWorld().getPosition().toString(), argument));
        return true;
    }
}
