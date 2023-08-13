package za.co.wethinkcode.robotworlds.make;

import za.co.wethinkcode.robotworlds.Position.*;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.Robot;

import za.co.wethinkcode.robotworlds.world.*;

public class Sniper extends Robot {
    
    private int MAXIMUM_SHIELD;
    private int MAXIMUM_SHOTS;
    private int SHOT_MAXIMUM_DISTANCE;
    private int currentShield;
    private int currentShots;

    public Sniper(String name, Position position) {
        super(name, position);
        this.MAXIMUM_SHIELD = 5;
        this.MAXIMUM_SHOTS = 10;
        this.SHOT_MAXIMUM_DISTANCE = 2;
        this.currentShield = this.MAXIMUM_SHIELD;
        this.currentShots = this.MAXIMUM_SHOTS;
    }
    
}
