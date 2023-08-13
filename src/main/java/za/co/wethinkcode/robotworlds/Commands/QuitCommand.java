package za.co.wethinkcode.robotworlds.Commands;

import org.json.JSONException;
import za.co.wethinkcode.robotworlds.Command;
import za.co.wethinkcode.robotworlds.Robot;

public class QuitCommand extends Command {
    private void printGameOver(){
        System.out.println(
                "                 ██████╗  █████╗ ███╗   ███╗███████╗               \n" +
                "                ██╔════╝ ██╔══██╗████╗ ████║██╔════╝               \n" +
                "                ██║  ███╗███████║██╔████╔██║█████╗                 \n" +
                "                ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝                 \n" +
                "                ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗               \n" +
                "                 ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝               \n" +
                "                          ██████╗ ██╗   ██╗███████╗██████╗         \n" +
                "                         ██╔═══██╗██║   ██║██╔════╝██╔══██╗        \n" +
                "                         ██║   ██║██║   ██║█████╗  ██████╔╝        \n" +
                "                         ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗        \n" +
                "                         ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║███████╗\n" +
                "                          ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝╚══════╝\n" +
                "                                                                   ");
    }
    public QuitCommand(){
        super("quit");
    }
    @Override
    public boolean execute(Robot target) throws JSONException {
        printGameOver();
        target.setStatus("Programme ended!");
        return false;
    }
}
