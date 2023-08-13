package za.co.wethinkcode.robotworlds.Commands;

import org.json.JSONException;
import za.co.wethinkcode.robotworlds.Command;
import za.co.wethinkcode.robotworlds.Json.ResponseHandler;
import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.world.WorldEnum;

public class ForwardCommand extends Command {
    private ResponseHandler response;

    public ForwardCommand(String argument) {
        super("forward", argument);
        response = new ResponseHandler();
    }

    @Override
    public boolean execute(Robot target) throws JSONException {
        int nrSteps = Integer.parseInt(getArgument());
        WorldEnum updateResult = target.getWorld().updatePosition(nrSteps);

        if (updateResult.equals(WorldEnum.SUCCESS)) {
            response.setResult("OK");
            response.setMessage("DONE");
        } else if (updateResult.equals(WorldEnum.FAILED_OBSTRUCTED)) {
            response.setResult("ERROR");
            response.setMessage("OBSTRUCTED");
        } else {
            response.setResult("ERROR");
            response.setMessage("OUT OF SAFE ZONE");
        }

        response.setDirection(target.getWorld().getCurrentDirection().toString());
        target.setStatus(response.movementResponse(target.getWorld().getPosition().toString(), argument));
        return true;
    }

    public void setArgument(String string) {
    }
}
