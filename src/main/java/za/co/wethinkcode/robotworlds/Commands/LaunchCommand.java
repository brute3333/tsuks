package za.co.wethinkcode.robotworlds.Commands;

import org.json.JSONException;
import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Json.ResponseHandler;
import za.co.wethinkcode.robotworlds.make.Hal;
import za.co.wethinkcode.robotworlds.Command;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.Robot;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;

public class LaunchCommand extends Command {

    private ResponseHandler responseHandler;

    public LaunchCommand(String kind, String name) {
        super("launch", kind + " " + name);
        this.responseHandler = new ResponseHandler();
    }

    
    @Override
    public boolean execute(Robot target) throws JSONException {
        String command = getArgument();
        String[] args = command.split(" ");

        if (args.length < 3) {
            // The command is missing the required arguments
            String response = responseHandler.createErrorResponse("Invalid launch command. Please provide robot type and name.");
            target.setStatus(response.toString());
            return false;
        }

        String robotType = args[1];
        String robotName = args[2];

        AbstractWorld world = target.getWorld();

        if (world.isRobotNameTaken(robotName)) {
            String response = responseHandler.createErrorResponse("Robot name is already taken");
            target.setStatus(response.toString());
            return false;
        }

        Position position = target.getPosition(); // Use the existing position of the robot.

        if (robotType.equalsIgnoreCase("hal")) {
            target = new Hal(robotName, position);
        }

        target.setShield(5);
        target.setShots(10);

        if (world.placeRobot(target)) {
            JSONObject state = new JSONObject();
            state.put("position", target.getPosition().toArray());
            state.put("direction", "NORTH");
            state.put("shields", target.getShield());
            state.put("shots", target.getShots());
            state.put("status", "NORMAL");

            JSONObject output = responseHandler.createSuccessResponse(state);
            target.setStatus(output.toString());
            return true;
        } else {
            String response = responseHandler.createErrorResponse("Failed to place robot in the world");
            target.setStatus(response.toString());
            return false;
        }
    }

    public void setArgument(String argument) {
        super.setArgument(argument);
    }

    public String getArgument() {
        return super.getArgument();
    }
}
