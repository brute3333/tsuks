package za.co.wethinkcode.robotworlds.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.Robot;


public interface IWorld {
    enum Direction {
        NORTH, EAST, SOUTH, WEST;

        public Direction left() {
            switch (this) {
                case NORTH:
                    return WEST;
                case EAST:
                    return NORTH;
                case SOUTH:
                    return EAST;
                case WEST:
                    return SOUTH;
                default:
                    throw new IllegalArgumentException("Invalid direction: " + this);
            }
        }

    public Direction right() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                throw new IllegalArgumentException("Invalid direction: " + this);
        }
    }
}

 
    enum ShotResult{
        HIT,MISS;
    }

    public Robot getRobot(String username);

    public void removeRobot(String name);

    List<String> getRobotList();
  
    public HashMap<String,Object> getState(String username);

    public void addRobot(String make, String name);

    public int getNumRobots();

    public HashMap<String,Object> getLaunchData(String username);

    enum UpdateResponse {
        SUCCESS, //position was updated successfully
        FAILED_OUTSIDE_WORLD, //robot will go outside world limits if allowed, so it failed to update the position
        FAILED_OBSTRUCTED, 
        //robot obstructed by at least one obstacle, thus cannot proceed.
    }

    Position CENTRE = new Position(0,0);

    UpdateResponse updatePosition(int nrSteps,String username);

    void updateDirection(boolean turnRight,String username);


    boolean isNewPositionAllowed(Position position,Robot robot);

    List<Obstacle> getObstacles();

    public void setStatus(String string,String username);

    public int getWorldVerticalSize();
    public int getWorldHorizontalSize();
    public String robotShot(String userName);
    public ArrayList<Object> getObjects(String userName);
    public int getWorldReload();
    public int getWorldRepair();
    public HashMap<String, Object> getClientsWrldObjs(String clientUsername);
   
}

