package za.co.wethinkcode.robotworlds.Commands;

import za.co.wethinkcode.robotworlds.Robot;

public class TurnCommand {

    private String argument;

    public boolean execute(Robot target) {
        if (argument.equalsIgnoreCase("left")){
            target.setDirection(target.getDirection().left());
            return true;
        }else if (argument.equalsIgnoreCase("right")){
            target.setDirection(target.getDirection().right());
            return true;
        }else {
            return false;      
        }

    }

    public void setArgument(String argument) {
        this.argument = argument;
    }
    
}
