package za.co.wethinkcode.robot;

import org.json.JSONException;
import za.co.wethinkcode.robotworlds.Command;
import za.co.wethinkcode.robotworlds.Robot;



public class QuitCommand extends Command{
    public QuitCommand() {
        super("quit");
    }

    @Override
    public boolean execute(Robot target) {
        target.setStatus("quiting");
        return false;
    }
}
