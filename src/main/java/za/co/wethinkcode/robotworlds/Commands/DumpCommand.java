package za.co.wethinkcode.robotworlds.Commands;

import org.json.JSONException;
import za.co.wethinkcode.robotworlds.Command;
import za.co.wethinkcode.robotworlds.Json.ResponseHandler;
import za.co.wethinkcode.robotworlds.Robot;

public class DumpCommand extends Command {
    ResponseHandler response = new ResponseHandler();

    public DumpCommand(){
        super("dump");
    }

    @Override
    public boolean execute(Robot target) throws JSONException {
        target.setStatus(response.dumpCommand(
                target.world.getPosition().toString()));
        return true;
    }
}
