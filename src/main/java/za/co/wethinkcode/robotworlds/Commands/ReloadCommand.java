package za.co.wethinkcode.robotworlds.Commands;

import org.json.JSONException;
import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.Command;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super("reload");
    }

    @Override
    public boolean execute(Robot target) throws JSONException {
        target.reload();
        target.setStatus("RELOAD");
        return true;
    }

    @Override
    public JSONObject getResponse() throws JSONException {
        JSONObject responseJson = new JSONObject();
        responseJson.put("result", "OK");

        JSONObject dataJson = new JSONObject();
        dataJson.put("message", "Done");

        JSONObject stateJson = new JSONObject();
        stateJson.put("status", "RELOAD");
        // Add other state information if needed

        responseJson.put("data", dataJson);
        responseJson.put("state", stateJson);

        return responseJson;
    }
}
