package za.co.wethinkcode.robotworlds.Commands;

import org.json.JSONException;
import za.co.wethinkcode.robotworlds.Command;
import za.co.wethinkcode.robotworlds.Json.ResponseHandler;
import za.co.wethinkcode.robotworlds.Robot;

public class RightCommand extends Command {
    private ResponseHandler response;

    public RightCommand() {
        super("right");
        response = new ResponseHandler();
    }

    @Override
    public boolean execute(Robot target) throws JSONException {
        target.getWorld().updateDirection(true);
        response.setDirection(target.getWorld().getCurrentDirection().toString());
        target.setStatus(response.movementResponse(target.getWorld().getPosition().toString(), argument));
        return true;
    }
}
