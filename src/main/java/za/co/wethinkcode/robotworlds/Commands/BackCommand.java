package za.co.wethinkcode.robotworlds.Commands;

import org.json.JSONException;
import za.co.wethinkcode.robotworlds.Command;
import za.co.wethinkcode.robotworlds.Json.ResponseHandler;
import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.world.WorldEnum;
import za.co.wethinkcode.robotworlds.Commands.TurnCommand;

public class BackCommand extends Command {
    ResponseHandler response = new ResponseHandler();
    @Override
    public boolean execute(Robot target) throws JSONException {
        int nrSteps = Integer.parseInt(getArgument());
        if (target.getWorld().updatePosition(-nrSteps).equals(WorldEnum.SUCCESS)) {
            response.setResult("OK");
            response.setMessage("DONE");
            response.setDirection(target.getWorld().getCurrentDirection().toString());
            target.setStatus(response.movementResponse(target.world.getPosition().toString(), argument));
        }else if(target.getWorld().updatePosition(-nrSteps).equals(WorldEnum.FAILED_OBSTRUCTED)){
            response.setResult("ERROR");
            response.setMessage("OBSTRUCTED");
            response.setDirection(target.getWorld().getCurrentDirection().toString());
            target.setStatus(response.movementResponse(target.world.getPosition().toString(), argument));
        } else {
            response.setResult("ERROR");
            response.setMessage("OUT OF SAFE ZONE");
            response.setDirection(target.getWorld().getCurrentDirection().toString());
            target.setStatus(response.movementResponse(target.world.getPosition().toString(), argument));
        }
        return true;
    }

    public BackCommand(String argument) {
        super("back", argument);
    }

    public void setArgument(String string) {
    }
}
